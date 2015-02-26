package newworldorder.common.network.factory;

import java.io.IOException;

import newworldorder.common.network.MessageProducer;

import com.rabbitmq.client.Channel;

class DirectProducer extends AbstractMessageProducer implements MessageProducer {

	private Channel channel;
	private String queueName;

	DirectProducer(Channel channel, String queueName) {
		this.channel = channel;
		this.queueName = queueName;
	}
	
	@Override
	public void sendMessage(byte[] message) throws IOException {
		channel.basicPublish("", queueName, null, message);
	}
}
