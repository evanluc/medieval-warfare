package newworldorder.common.network.factory;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

class DirectProducer extends AbstractMessageProducer {
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

	@Override
	public void releaseConnection() throws IOException {
		Connection conn = channel.getConnection();
		channel.close();
		conn.close();
	}
}
