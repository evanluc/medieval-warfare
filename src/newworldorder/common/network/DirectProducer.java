package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DirectProducer implements MessageProducer {
	
	private Channel channel;
	private String queueName;
	
	public DirectProducer(String host, String queueName) throws IOException {
		this.queueName = queueName;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);
	}

	@Override
	public void sendMessage(String message) throws IOException {
		channel.basicPublish("", queueName, null, message.getBytes());
	}
	
}
