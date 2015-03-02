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
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.AbstractCommand;
import newworldorder.common.network.message.LoginCommand;
import newworldorder.common.network.util.Serialization;

public class RoutingProducerTest {
	private IRoutingProducer producer;
	private String exchange = "test-exchange-1";
	private String routingKey = "routing-key-1";
	@Mock MessageHandler handler;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		producer = ActorFactory.createRoutingProducer("localhost", exchange);
	}

	@After
	public void tearDown() {
		producer = null;
		handler = null;
	}

	@Test
	public void testSubscribedConsumerHandlesMessage() throws Exception {
		ActorFactory.createRoutingConsumer("localhost", exchange, routingKey, handler);
		byte[] message = "message".getBytes();

		producer.sendMessage(message, routingKey);

		then(handler).should().handle(message);
	}

	@Test
	public void testSubscribedConsumerHandlesCommand() throws Exception {
		ActorFactory.createRoutingConsumer("localhost", exchange, routingKey, handler);
		AbstractCommand command = new LoginCommand("sender", "password");
		byte[] message = Serialization.command2bytes(command);

		producer.sendCommand(command, routingKey);

		then(handler).should().handle(message);
	}

	@Test
	public void testSubscribedConsumerWithDifferentKeyDoesNotHandleMessage() throws Exception {
		ActorFactory.createRoutingConsumer("localhost", exchange, "routing-key-2", handler);
		AbstractCommand command = new LoginCommand("sender", "password");

		producer.sendCommand(command, routingKey);
		producer.sendMessage("message".getBytes(), routingKey);

		then(handler).should(never()).handle(any());
	}
}
