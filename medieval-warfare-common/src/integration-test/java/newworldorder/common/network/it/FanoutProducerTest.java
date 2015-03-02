package newworldorder.common.network.it;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.MessageProducer;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.AbstractCommand;
import newworldorder.common.network.message.LoginCommand;
import newworldorder.common.network.util.Serialization;

public class FanoutProducerTest {
	private MessageProducer producer;
	@Mock private MessageHandler handler1, handler2, handler3;
	private String exchange = "test-exchange-1";

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		producer = ActorFactory.createFanoutProducer("localhost", exchange);
	}

	@After
	public void tearDown() {
		producer = null;
		handler1 = null;
		handler2 = null;
		handler3 = null;
	}

	@Test
	public void testMultipleSubscribedConsumersHandleMessage() throws Exception {
		ActorFactory.createFanoutConsumer("localhost", exchange, handler1);
		ActorFactory.createFanoutConsumer("localhost", exchange, handler2);
		ActorFactory.createFanoutConsumer("localhost", exchange, handler3);
		byte[] message = "message".getBytes();

		producer.sendMessage(message);

		then(handler1).should(times(1)).handle(message);
		then(handler2).should(times(1)).handle(message);
		then(handler3).should(times(1)).handle(message);
	}

	@Test
	public void testMultipleSubscribedConsumersHandleCommand() throws Exception {
		ActorFactory.createFanoutConsumer("localhost", exchange, handler1);
		ActorFactory.createFanoutConsumer("localhost", exchange, handler2);
		ActorFactory.createFanoutConsumer("localhost", exchange, handler3);
		AbstractCommand command = new LoginCommand("sender", "password");
		byte[] message = Serialization.command2bytes(command);

		producer.sendCommand(command);

		then(handler1).should(times(1)).handle(message);
		then(handler2).should(times(1)).handle(message);
		then(handler3).should(times(1)).handle(message);
	}

	@Test
	public void testUnsubscribedConsumerDoesNotHandleMessage() throws Exception {
		ActorFactory.createFanoutConsumer("localhost", "test-exchange-2", handler1);
		byte[] message = "message".getBytes();
		AbstractCommand command = new LoginCommand("sender", "password");

		producer.sendMessage(message);
		producer.sendCommand(command);

		then(handler1).should(never()).handle(any());
	}
}
