package newworldorder.common.network.it;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

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

public class DirectProducerTest {
	private MessageProducer producer;
	@Mock private MessageHandler handler;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		producer = ActorFactory.createDirectProducer("localhost", "test-queue-1");
	}

	@After
	public void tearDown() {
		producer = null;
		handler = null;
	}

	@Test
	public void testSubscribedConsumerHandlesMessage() throws Exception {
		ActorFactory.createDirectConsumer("localhost", "test-queue-1", handler);
		byte[] message = "message".getBytes();
		producer.sendMessage(message);
		then(handler).should().handle(message);
	}

	@Test
	public void testSubscribedConsumerHandlesCommand() throws Exception {
		ActorFactory.createDirectConsumer("localhost", "test-queue-1", handler);
		AbstractCommand command = new LoginCommand("sender", "password");
		producer.sendCommand(command);
		byte[] message = Serialization.command2bytes(command);
		then(handler).should().handle(message);
	}

	@Test
	public void testUnsubscribedConsumerDoesNotReceiveMessage() throws Exception {
		ActorFactory.createDirectConsumer("localhost", "some-other-queue", handler);
		byte[] message = "message".getBytes();
		producer.sendMessage(message);
		AbstractCommand command = new LoginCommand("sender", "password");
		producer.sendCommand(command);
		then(handler).should(never()).handle(any());
	}
}
