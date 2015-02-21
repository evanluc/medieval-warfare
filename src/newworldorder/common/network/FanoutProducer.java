package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class FanoutProducer {
	
	private String exchangeName;
	private Channel channel;
	
	public FanoutProducer(String host, String exchangeName) throws IOException {
		this.exchangeName = exchangeName;
		channel = ChannelFactory.createFanoutChannel(host, exchangeName);
	}
	
	public void sendMessage(String message) throws IOException {
		channel.basicPublish(exchangeName, "", null, message.getBytes());
	}
}
