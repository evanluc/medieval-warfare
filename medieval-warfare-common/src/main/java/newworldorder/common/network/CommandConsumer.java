package newworldorder.common.network;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class CommandConsumer {
	private final AmqpAdmin admin;
	private final SimpleMessageListenerContainer container;
	
	public CommandConsumer(AmqpAdmin admin, SimpleMessageListenerContainer container) {
		super();
		this.admin = admin;
		this.container = container;
	}
	
	public void startConsumingFromDirectExchange(String exchangeName, String routingKey) {
		Queue queue = admin.declareQueue();
		DirectExchange exchange = new DirectExchange(exchangeName);
		admin.declareExchange(exchange);
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
		container.addQueues(queue);
		
		container.start();
	}
}
