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

import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.MessageProducer;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.AbstractCommand;
import newworldorder.common.network.message.LoginCommand;
import newworldorder.common.network.util.RabbitUtils;
import newworldorder.common.network.util.Serialization;

public class DirectProducerTest {
	private MessageProducer producer;
	private MessageConsumer consumer;
	private String queue = "test-queue-1";
	private String hostname = "104.236.30.10";
	@Mock private MessageHandler handler;

	private final int wait = 2000;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		producer = ActorFactory.createDirectProducer(hostname, queue);
	}

	@After
	public void tearDown() throws IOException {
		producer.releaseConnection();
		consumer.stopConsuming();
		consumer.releaseConnection();
		producer = null;
		handler = null;
		consumer = null;
		RabbitUtils.purgeQueue(hostname, queue);
	}

	@Test
	public void testSubscribedConsumerHandlesMessage() throws Exception {
		consumer = ActorFactory.createDirectConsumer(hostname, queue, handler, false);
		consumer.startConsuming();
		byte[] message = "message".getBytes();
		producer.sendMessage(message);

		Thread.sleep(wait);

		then(handler).should().handle(message);
	}

	@Test
	public void testSubscribedConsumerHandlesCommand() throws Exception {
		consumer = ActorFactory.createDirectConsumer(hostname, queue, handler, false);
		consumer.startConsuming();
		AbstractCommand command = new LoginCommand("sender", "password");
		producer.sendCommand(command);
		byte[] message = Serialization.command2bytes(command);

		Thread.sleep(wait);
		then(handler).should().handle(message);
	}

	@Test
	public void testUnsubscribedConsumerDoesNotReceiveMessage() throws Exception {
		consumer = ActorFactory.createDirectConsumer(hostname, "some-other-queue", handler, false);
		consumer.startConsuming();
		byte[] message = "message".getBytes();
		producer.sendMessage(message);
		AbstractCommand command = new LoginCommand("sender", "password");
		producer.sendCommand(command);

		Thread.sleep(wait);
		then(handler).should(never()).handle(any());
	}
}
