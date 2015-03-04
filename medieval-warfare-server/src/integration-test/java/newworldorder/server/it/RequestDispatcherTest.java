package newworldorder.server.it;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.common.network.command.CommandExecutor;
import newworldorder.common.network.command.LoginCommand;
import newworldorder.common.service.IMatchController;
import newworldorder.server.RequestDispatcher;
import newworldorder.server.matchmaking.GameInitializer;
import newworldorder.server.service.MatchController;
import newworldorder.server.service.ServiceLocator;

public class RequestDispatcherTest {
	private RabbitTemplate template;
	private SimpleMessageListenerContainer container;
	private CachingConnectionFactory factory;
	private RabbitAdmin admin;
	private CommandExecutor executor;

	private final String host = "104.236.30.10";
	private final String username = "newworldorder";
	private final String password = "warfare";
	private final Queue queue = new Queue("test-request-queue-1");

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		factory = new CachingConnectionFactory(host);
		factory.setUsername(username);
		factory.setPassword(password);
		admin = new RabbitAdmin(factory);
		template = new RabbitTemplate(factory);

		GameInitializer gameInit = new GameInitializer(template);
		IMatchController controller = new MatchController(gameInit);
		executor = new RequestDispatcher(new ServiceLocator(controller));

		container = new SimpleMessageListenerContainer(factory);
		MessageListenerAdapter adapter = new MessageListenerAdapter(executor);
		adapter.setDefaultListenerMethod("execute");
		container.setMessageListener(adapter);
	}

	@Test
	public void testRequestDispatcherRecievesMessages() throws InterruptedException {
		admin.declareQueue(queue);
		admin.declareBinding(BindingBuilder.bind(queue).to(new DirectExchange("")).withQueueName());
		container.setQueues(queue);
		container.start();

		LoginCommand command = new LoginCommand("username", "password");
		template.convertAndSend(queue.getName(), command);
	}
}
