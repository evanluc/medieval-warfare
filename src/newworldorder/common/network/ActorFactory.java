package newworldorder.common.network;

import java.io.IOException;

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

	public static MessageProducer createFanoutProducer(String host, String exchangeName) throws IOException {
		Channel channel = createFanoutChannel(host, exchangeName);
		return new FanoutProducer(channel, exchangeName);
	}

	private static Channel createDirectChannel(String host, String routingKey) throws IOException {
		Channel channel = setupChannel(host);
		channel.queueDeclare(routingKey, false, false, false, null);
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
		Connection connection = factory.newConnection();
		return connection.createChannel();
	}
}
