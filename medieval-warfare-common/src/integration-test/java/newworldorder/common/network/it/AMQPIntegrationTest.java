package newworldorder.common.network.it;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.common.network.command.CommandExecutor;
import newworldorder.common.network.command.LoginCommand;

public class AMQPIntegrationTest {
	private RabbitTemplate template;
	private SimpleMessageListenerContainer container;
	private CachingConnectionFactory factory;
	private RabbitAdmin admin;
	@Mock private CommandExecutor executor;

	private final String host = "104.236.30.10";
	private final String username = "newworldorder";
	private final String password = "warfare";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		factory = new CachingConnectionFactory(host);
		factory.setUsername(username);
		factory.setPassword(password);
		admin = new RabbitAdmin(factory);
		template = new RabbitTemplate(factory);
		container = new SimpleMessageListenerContainer(factory);
		MessageListenerAdapter adapter = new MessageListenerAdapter(executor);
		adapter.setDefaultListenerMethod("execute");
		container.setMessageListener(adapter);
	}

	@After
	public void tearDown() {
		container.stop();
		template = null;
		container = null;
		executor = null;
		admin = null;
	}

	@Test
	public void testDirectSend() throws InterruptedException {
		Queue queue = new Queue("test-queue-1");
		admin.declareQueue(queue);
		admin.declareBinding(BindingBuilder.bind(queue).to(new DirectExchange("")).withQueueName());
		container.setQueues(queue);
		container.start();
		LoginCommand command = new LoginCommand("username", "password");
		template.convertAndSend(queue.getName(), command);

		Thread.sleep(1000);

		then(executor).should().execute(command);
	}

	@Test
	public void testDirectSendWithRoutingKey() throws InterruptedException {
		Queue queue = new Queue("test-queue-1");
		DirectExchange exchange = new DirectExchange("test-exchange-1");
		String routingKey = "test-routing-key-1";
		admin.declareQueue(queue);
		admin.declareExchange(exchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
		container.setQueues(queue);
		container.start();
		LoginCommand command = new LoginCommand("username", "password");

		template.convertAndSend(exchange.getName(), routingKey, command);

		Thread.sleep(1000);

		then(executor).should().execute(command);
	}

	@Test
	public void testDirectSendWithUnmatchedRoutingKey() throws InterruptedException {
		Queue queue = new Queue("test-queue-1");
		DirectExchange exchange = new DirectExchange("test-exchange-1");
		String routingKey = "test-routing-key-1";
		admin.declareQueue(queue);
		admin.declareExchange(exchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
		container.setQueues(queue);
		container.start();
		LoginCommand command = new LoginCommand("username", "password");

		template.convertAndSend(exchange.getName(), "test-routing-key-2", command);

		Thread.sleep(1000);

		then(executor).should(never()).execute(command);
	}

	@Test
	public void testFanoutExchange() throws InterruptedException {
		FanoutExchange exchange = new FanoutExchange("test-exchange-2");
		admin.declareExchange(exchange);
		List<CommandExecutor> consumers = new ArrayList<>();
		LoginCommand command = new LoginCommand("username", "password");

		for (int i = 0; i < 4; i++) {
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
			CommandExecutor exec = Mockito.mock(CommandExecutor.class);
			MessageListenerAdapter adapter = new MessageListenerAdapter(exec);
			adapter.setDefaultListenerMethod("execute");
			container.setMessageListener(adapter);
			Queue queue = admin.declareQueue();
			admin.declareBinding(BindingBuilder.bind(queue).to(exchange));
			container.setQueues(queue);
			consumers.add(exec);
			container.start();
		}

		template.convertAndSend(exchange.getName(), "", command);

		Thread.sleep(1000);

		for (CommandExecutor e : consumers) {
			then(e).should().execute(command);
		}
	}
}
