package newworldorder.common.network.util;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitUtils {

	public static void deleteQueue(String host, String queueName) throws IOException {
		Channel channel = setupChannel(host);
		channel.queueDelete(queueName);
		Connection conn = channel.getConnection();
		channel.close();
		conn.close();
	}

	public static void deleteExchange(String host, String exchangeName) throws IOException {
		Channel channel = setupChannel(host);
		channel.exchangeDelete(exchangeName);
		Connection conn = channel.getConnection();
		channel.close();
		conn.close();
	}

	public static Channel createDirectChannel(String host, String queueName) throws IOException {
		Channel channel = setupChannel(host);
		channel.queueDeclare(queueName, false, false, false, null);
		return channel;
	}

	public static Channel createRoutingChannel(String host, String exchangeName) throws IOException {
		Channel channel = setupChannel(host);
		channel.exchangeDeclare(exchangeName, "direct");
		return channel;
	}

	public static Channel createFanoutChannel(String host, String exchangeName) throws IOException {
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
