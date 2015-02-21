package newworldorder.common.network;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class FanoutConsumer {

	private Channel channel;
	private String queueName;
	private MessageHandler handler;

	public FanoutConsumer(String host, String exchangeName,
			MessageHandler handler) throws IOException {
		this.handler = handler;
		channel = ChannelFactory.createFanoutChannel(host, exchangeName);
		queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangeName, "");
	}

	public void consumeMessages() throws Exception {
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			handler.handle(message);
		}
	}
}
