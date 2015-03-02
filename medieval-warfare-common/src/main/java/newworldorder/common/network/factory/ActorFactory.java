package newworldorder.common.network.factory;

import java.io.IOException;

import com.rabbitmq.client.Channel;

import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.MessageConsumer;
import newworldorder.common.network.MessageHandler;
import newworldorder.common.network.MessageProducer;
import newworldorder.common.network.util.RabbitUtils;

public class ActorFactory {

	public static MessageConsumer createDirectConsumer(String host, String queueName, MessageHandler handler) throws IOException {
		Channel channel = RabbitUtils.createDirectChannel(host, queueName);
		return new AsyncConsumer(channel, queueName, handler);
	}

	public static MessageConsumer createDirectConsumer(String host, String exchangeName, String routingKey, MessageHandler handler)
			throws IOException {
		Channel channel = RabbitUtils.createRoutingChannel(host, exchangeName);
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangeName, routingKey);
		return new AsyncConsumer(channel, queueName, handler);
	}

	public static MessageConsumer createFanoutConsumer(String host, String exchangeName, MessageHandler handler) throws IOException {
		Channel channel = RabbitUtils.createFanoutChannel(host, exchangeName);
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangeName, "");
		return new AsyncConsumer(channel, queueName, handler);
	}

	public static MessageProducer createDirectProducer(String host, String queueName) throws IOException {
		Channel channel = RabbitUtils.createDirectChannel(host, queueName);
		return new DirectProducer(channel, queueName);
	}

	public static IRoutingProducer createRoutingProducer(String host, String exchangeName) throws IOException {
		Channel channel = RabbitUtils.createRoutingChannel(host, exchangeName);
		return new RoutingProducer(channel, exchangeName);
	}

	public static MessageProducer createFanoutProducer(String host, String exchangeName) throws IOException {
		Channel channel = RabbitUtils.createFanoutChannel(host, exchangeName);
		return new FanoutProducer(channel, exchangeName);
	}
}
