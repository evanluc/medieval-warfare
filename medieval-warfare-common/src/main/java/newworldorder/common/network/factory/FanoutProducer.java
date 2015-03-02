package newworldorder.common.network.factory;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

class FanoutProducer extends AbstractMessageProducer {

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

	@Override
	public void releaseConnection() throws IOException {
		Connection conn = channel.getConnection();
		channel.close();
		conn.close();
	}
}
