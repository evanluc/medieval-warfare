package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class DirectConsumer {

	private Channel channel;
	private String queueName;
	private MessageHandler handler;

	public DirectConsumer(String host, String queueName, MessageHandler handler)
			throws IOException {
		this.queueName = queueName;
		this.handler = handler;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);
	}

	public final void consumeMessages() throws Exception {
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			handler.handle(message);
		}
	}

}
