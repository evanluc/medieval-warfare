package newworldorder.common.network.factory;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;

class BlockingConsumer implements MessageConsumer {
	private Channel channel;
	private String queueName;
	private MessageHandler handler;

	BlockingConsumer(Channel channel, String queueName, MessageHandler handler) {
		super();
		this.channel = channel;
		this.queueName = queueName;
		this.handler = handler;
	}

	@Override
	public void startConsuming() throws IOException {
		QueueingConsumer consumer = new QueueingConsumer(channel);

		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = consumer.nextDelivery();
				handler.handle(delivery.getBody());
			} catch (ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {
				// TODO log...
				stopConsuming();
				releaseConnection();
			}
		}
	}

	@Override
	public void stopConsuming() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void releaseConnection() throws IOException {
		// TODO Auto-generated method stub

	}
}
