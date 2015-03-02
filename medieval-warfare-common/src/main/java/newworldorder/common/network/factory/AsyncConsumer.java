package newworldorder.common.network.factory;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;

class AsyncConsumer implements MessageConsumer {
	private final Channel channel;
	private final String queueName;
	private final DefaultConsumer consumer;

	AsyncConsumer(Channel channel, String queueName, MessageHandler handler) {
		this.channel = channel;
		this.queueName = queueName;

		consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
				handler.handle(body);
			}
		};
	}

	@Override
	public void startConsuming() throws IOException {
		channel.basicConsume(queueName, true, consumer);
	}

	@Override
	public void stopConsuming() throws IOException {
		channel.basicCancel(consumer.getConsumerTag());
	}

	@Override
	public final void releaseConnection() throws IOException {
		Connection conn = channel.getConnection();
		channel.close();
		conn.close();
	}

	@Override
	public String getQueue() {
		return queueName;
	}
}
