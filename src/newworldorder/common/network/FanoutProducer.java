package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;

class FanoutProducer implements MessageProducer {

	private Channel channel;
	private String exchangeName;

	FanoutProducer(Channel channel, String exchangeName) {
		this.channel = channel;
		this.exchangeName = exchangeName;
	}

	@Override
	public void sendMessage(String message) throws IOException {
		channel.basicPublish(exchangeName, "", null, message.getBytes());
	}
}
