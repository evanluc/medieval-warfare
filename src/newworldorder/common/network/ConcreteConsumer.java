package newworldorder.common.network;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

class ConcreteConsumer implements MessageConsumer {

	private Channel channel;
	private String queueName;
	private MessageHandler handler;

	ConcreteConsumer(Channel channel, String queueName, MessageHandler handler) {
		this.channel = channel;
		this.queueName = queueName;
		this.handler = handler;
	}

	@Override
	public void consumeMessages() throws Exception {
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			byte[] message = delivery.getBody();
			handler.handle(message);
		}
	}
}
