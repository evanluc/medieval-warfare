package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class DirectProducer {

	private String queueName;
	private Channel channel;

	public DirectProducer(String host, String queueName) throws IOException {
		this.queueName = queueName;
		channel = ChannelFactory.createDirectChannel(host, queueName);
	}

	public void sendMessage(String message) throws IOException {
		channel.basicPublish("", queueName, null, message.getBytes());
	}
}
