package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;

class DirectProducer implements MessageProducer {

	private Channel channel;
	private String queueName;

	DirectProducer(Channel channel, String queueName) {
		this.channel = channel;
		this.queueName = queueName;
	}
	
	@Override
	public void sendMessage(String message) throws IOException {
		channel.basicPublish("", queueName, null, message.getBytes());
	}
}
