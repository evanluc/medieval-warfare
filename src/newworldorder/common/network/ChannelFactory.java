package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChannelFactory {
	
	public static Channel createDirectChannel(String host, String routingKey) throws IOException {
		Channel channel = setupChannel(host);
		channel.queueDeclare(routingKey, false, false, false, null);
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
		Connection connection = factory.newConnection();
		return connection.createChannel();
	}
}
