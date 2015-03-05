package newworldorder.common.network;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import newworldorder.common.network.command.Command;

public class AmqpAdapter {
	private final AmqpTemplate template;
	
	@Autowired
	public AmqpAdapter(AmqpTemplate template) {
		super();
		this.template = template;
	}

	public void send(Command command, String routingKey) {
		template.convertAndSend(routingKey, command);
	}
	
	public void send(Command command, String exchange, String routingKey) {
		template.convertAndSend(exchange, routingKey, command);
	}

	public Command receive(String queueName) {
		return (Command) template.receiveAndConvert(queueName);
	}
}
