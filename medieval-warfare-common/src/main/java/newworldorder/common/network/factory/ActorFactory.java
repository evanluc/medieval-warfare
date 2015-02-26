package newworldorder.common.network.factory;

import java.io.IOException;

import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.MessageProducer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ActorFactory {

	public static MessageConsumer createDirectConsumer(String host, String queueName, MessageHandler handler)
			throws IOException {
		Channel channel = createDirectChannel(host, queueName);
		return new ConcreteConsumer(channel, queueName, handler);
	}

	public static MessageConsumer createFanoutConsumer(String host, String exchangeName, MessageHandler handler)
			throws IOException {
		Channel channel = createFanoutChannel(host, exchangeName);
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangeName, "");
		return new ConcreteConsumer(channel, queueName, handler);
	}

	public static MessageProducer createDirectProducer(String host, String queueName) throws IOException {
		Channel channel = createDirectChannel(host, queueName);
		return new DirectProducer(channel, queueName);
	}

	public static IRoutingProducer createRoutingProducer(String host, String exchangeName) throws IOException {
		Channel channel = createRoutingChannel(host, exchangeName);
		return new RoutingProducer(channel, exchangeName);
	}

	public static MessageProducer createFanoutProducer(String host, String exchangeName) throws IOException {
		Channel channel = createFanoutChannel(host, exchangeName);
		return new FanoutProducer(channel, exchangeName);
	}

	private static Channel createDirectChannel(String host, String routingKey) throws IOException {
		Channel channel = setupChannel(host);
		channel.queueDeclare(routingKey, false, false, false, null);
		return channel;
	}

	private static Channel createRoutingChannel(String host, String exchangeName) throws IOException {
		Channel channel = setupChannel(host);
		channel.exchangeDeclare(exchangeName, "direct");
		return channel;
	}

	private static Channel createFanoutChannel(String host, String exchangeName) throws IOException {
		Channel channel = setupChannel(host);
		channel.exchangeDeclare(exchangeName, "fanout");
		return channel;
	}

	private static Channel setupChannel(String host) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername("newworldorder");
		factory.setPassword("warfare");
		Connection connection = factory.newConnection();
		return connection.createChannel();
	}
}
