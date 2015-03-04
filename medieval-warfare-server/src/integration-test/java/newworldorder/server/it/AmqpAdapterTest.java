package newworldorder.server.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.then;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.command.Command;
import newworldorder.common.network.command.CommandExecutor;
import newworldorder.common.network.command.LoginCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AmqpAdapterTest {
	@Autowired AmqpTemplate template;
	@Autowired AmqpAdmin admin;
	@Autowired Queue queue;
	@Autowired DirectExchange defaultExchange;
	@Autowired DirectExchange directExchange;
	@Autowired FanoutExchange fanoutExchange;
	@Autowired AmqpAdapter adapter;
	@Autowired SimpleMessageListenerContainer container;
	@Mock CommandExecutor listener;
	private LoginCommand expected;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		expected = new LoginCommand("username", "password");
	}
	
	@After
	public void tearDown() {
		admin.deleteExchange(directExchange.getName());
		admin.deleteExchange(fanoutExchange.getName());
		admin.deleteQueue(queue.getName());
		admin = null;
		adapter = null;
		template = null;
		container = null;
	}
	
	@Test
	public void testSendToDefaultExchange() {
		admin.declareQueue(queue);
		admin.declareBinding(BindingBuilder.bind(queue).to(defaultExchange).withQueueName());
		
		adapter.send(expected, queue.getName());
		LoginCommand actual = (LoginCommand) adapter.receive(queue.getName());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSendToDirectExchangeNoKey() {
		Queue queue = admin.declareQueue();
		admin.declareExchange(directExchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(directExchange).withQueueName());
		adapter.send(expected, directExchange.getName(), queue.getName());
		LoginCommand actual = (LoginCommand) adapter.receive(queue.getName());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSendToDirectExchangeWithKey() {
		Queue queue = admin.declareQueue();
		admin.declareExchange(directExchange);
		String key = "test-key";
		admin.declareExchange(directExchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(key));
		adapter.send(expected, directExchange.getName(), key);
		LoginCommand actual = (LoginCommand) adapter.receive(queue.getName());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSendToFanoutExchange() {
		admin.declareExchange(fanoutExchange);
		admin.declareQueue(queue);
		Queue queue2 = admin.declareQueue();
		admin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
		admin.declareBinding(BindingBuilder.bind(queue2).to(fanoutExchange));
		
		adapter.send(expected, fanoutExchange.getName(), "");
		Command actual1 = adapter.receive(queue.getName());
		assertEquals(expected, actual1);
		Command actual2 = adapter.receive(queue2.getName());
		assertEquals(expected, actual2);
	}
	
	@Test
	public void testUnboundQueueGetsNull() {
		Queue queue = admin.declareQueue();
		admin.declareQueue(this.queue);
		admin.declareExchange(directExchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(directExchange).withQueueName());
		adapter.send(expected, directExchange.getName(), queue.getName());
		LoginCommand actual = (LoginCommand) adapter.receive(this.queue.getName());
		assertNull(actual);
	}
	
	@Test
	public void testBoundQueueWithWrongKeyGetsNull() {
		Queue queue = admin.declareQueue();
		admin.declareExchange(directExchange);
		String key = "test-key";
		admin.declareExchange(directExchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with("some-other-key"));
		adapter.send(expected, directExchange.getName(), key);
		LoginCommand actual = (LoginCommand) adapter.receive(queue.getName());
		assertNull(actual);
	}
	
	@Test
	public void testAsyncConsumer() throws InterruptedException {
		Queue queue = admin.declareQueue();
		admin.declareExchange(directExchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(directExchange).withQueueName());
		
		container.addQueues(queue);
		container.setMessageListener(new MessageListenerAdapter(listener, "execute"));
		container.start();
		
		adapter.send(expected, directExchange.getName(), queue.getName());
		Thread.sleep(1000);
		
		then(listener).should().execute(expected);
		
		container.stop();
	}
}
