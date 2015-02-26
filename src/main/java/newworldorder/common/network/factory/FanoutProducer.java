package newworldorder.common.network.factory;

import java.io.IOException;

import newworldorder.common.network.MessageProducer;

import com.rabbitmq.client.Channel;

class FanoutProducer extends AbstractMessageProducer implements MessageProducer {

	private Channel channel;
	private String exchangeName;

	FanoutProducer(Channel channel, String exchangeName) {
		this.channel = channel;
		this.exchangeName = exchangeName;
	}

	@Override
	public void sendMessage(byte[] message) throws IOException {
		channel.basicPublish(exchangeName, "", null, message);
	}
}
