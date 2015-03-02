package newworldorder.common.network.it;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.MessageProducer;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.AbstractCommand;
import newworldorder.common.network.message.LoginCommand;
import newworldorder.common.network.util.RabbitUtils;
import newworldorder.common.network.util.Serialization;

public class FanoutProducerTest {
	private MessageProducer producer;
	private List<MessageConsumer> consumers;
	private String hostname = "104.236.30.10";
	private String exchange = "test-exchange-1";
	@Mock private MessageHandler handler1, handler2, handler3;

	private final int wait = 2000;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		producer = ActorFactory.createFanoutProducer(hostname, exchange);
		consumers = new ArrayList<>();
	}

	@After
	public void tearDown() throws IOException {
		producer.releaseConnection();
		for (MessageConsumer c : consumers) {
			c.stopConsuming();
			c.releaseConnection();
			c = null;
		}
		consumers.clear();
		RabbitUtils.purgeExchange(hostname, exchange);
		RabbitUtils.deleteExchange(hostname, exchange);
		handler1 = null;
		handler2 = null;
		handler3 = null;
		producer = null;
		consumers = null;
	}

	@Test
	public void testMultipleSubscribedConsumersHandleMessage() throws Exception {
		consumers.add(ActorFactory.createFanoutConsumer(hostname, exchange, handler1, false));
		consumers.add(ActorFactory.createFanoutConsumer(hostname, exchange, handler2, false));
		consumers.add(ActorFactory.createFanoutConsumer(hostname, exchange, handler3, false));

		for (MessageConsumer c : consumers) {
			c.startConsuming();
		}

		byte[] message = "message".getBytes();

		producer.sendMessage(message);
		Thread.sleep(wait);

		then(handler1).should().handle(message);
		then(handler2).should().handle(message);
		then(handler3).should().handle(message);
	}

	@Test
	public void testMultipleSubscribedConsumersHandleCommand() throws Exception {
		consumers.add(ActorFactory.createFanoutConsumer(hostname, exchange, handler1, false));
		consumers.add(ActorFactory.createFanoutConsumer(hostname, exchange, handler2, false));
		consumers.add(ActorFactory.createFanoutConsumer(hostname, exchange, handler3, false));

		for (MessageConsumer c : consumers) {
			c.startConsuming();
		}

		AbstractCommand command = new LoginCommand("sender", "password");
		byte[] message = Serialization.command2bytes(command);

		producer.sendCommand(command);
		Thread.sleep(wait);

		then(handler1).should().handle(message);
		then(handler2).should().handle(message);
		then(handler3).should().handle(message);
	}

	@Test
	public void testUnsubscribedConsumerDoesNotHandleMessage() throws Exception {
		String otherExchange = "test-exchange-2";
		consumers.add(ActorFactory.createFanoutConsumer(hostname, otherExchange, handler1, false));

		for (MessageConsumer c : consumers) {
			c.startConsuming();
		}

		byte[] message = "message".getBytes();
		AbstractCommand command = new LoginCommand("sender", "password");

		producer.sendMessage(message);
		producer.sendCommand(command);
		Thread.sleep(wait);

		then(handler1).should(never()).handle(any());
		RabbitUtils.deleteExchange(hostname, otherExchange);
	}
}
