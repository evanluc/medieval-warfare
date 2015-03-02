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

import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.AbstractCommand;
import newworldorder.common.network.message.LoginCommand;
import newworldorder.common.network.util.RabbitUtils;
import newworldorder.common.network.util.Serialization;

public class RoutingProducerTest {
	private IRoutingProducer producer;
	private MessageConsumer consumer;
	private String hostname = "104.236.30.10";
	private String exchange = "test-exchange-1";
	private String routingKey = "routing-key-1";
	@Mock MessageHandler handler;

	private final int wait = 1000;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		producer = ActorFactory.createRoutingProducer(hostname, exchange);
	}

	@After
	public void tearDown() throws IOException {
		producer.releaseConnection();
		consumer.stopConsuming();
		consumer.releaseConnection();
		producer = null;
		handler = null;
		consumer = null;
		RabbitUtils.deleteExchange(hostname, exchange);
	}

	@Test
	public void testSubscribedConsumerHandlesMessage() throws Exception {
		consumer = ActorFactory.createDirectConsumer(hostname, exchange, routingKey, handler);
		consumer.startConsuming();
		byte[] message = "message".getBytes();

		producer.sendMessage(message, routingKey);

		Thread.sleep(wait);

		then(handler).should().handle(message);
	}

	@Test
	public void testSubscribedConsumerHandlesCommand() throws Exception {
		consumer = ActorFactory.createDirectConsumer(hostname, exchange, routingKey, handler);
		consumer.startConsuming();
		AbstractCommand command = new LoginCommand("sender", "password");
		byte[] message = Serialization.command2bytes(command);

		producer.sendCommand(command, routingKey);

		Thread.sleep(wait);

		then(handler).should().handle(message);
	}

	@Test
	public void testSubscribedConsumerWithDifferentKeyDoesNotHandleMessage() throws Exception {
		consumer = ActorFactory.createDirectConsumer(hostname, exchange, "routing-key-2", handler);
		consumer.startConsuming();
		AbstractCommand command = new LoginCommand("sender", "password");

		producer.sendCommand(command, routingKey);
		producer.sendMessage("message".getBytes(), routingKey);

		Thread.sleep(wait);

		then(handler).should(never()).handle(any());
	}

	@Test
	public void testUnsubscribedConsumerDoesNotHandleMessage() throws Exception {
		String otherExchange = "test-exchange-2";
		consumer = ActorFactory.createDirectConsumer(hostname, otherExchange, routingKey, handler);
		consumer.startConsuming();
		AbstractCommand command = new LoginCommand("sender", "password");

		producer.sendCommand(command, routingKey);
		producer.sendMessage("message".getBytes(), routingKey);

		Thread.sleep(wait);

		then(handler).should(never()).handle(any());
		RabbitUtils.deleteExchange(hostname, otherExchange);
	}
}
