package newworldorder.common.network;

import org.springframework.amqp.core.AmqpTemplate;

public class AmqpAdapter {
	private final AmqpTemplate template;
	
	public AmqpAdapter(AmqpTemplate template) {
		super();
		this.template = template;
	}

	public void send(Object message, String routingKey) {
		template.convertAndSend(routingKey, message);
	}
	
	public void send(Object message, String exchange, String routingKey) {
		template.convertAndSend(exchange, routingKey, message);
	}

	public Object receive(String queueName) {
		return template.receiveAndConvert(queueName);
	}
}
