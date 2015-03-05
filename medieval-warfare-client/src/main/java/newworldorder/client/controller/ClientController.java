package newworldorder.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.command.LoginCommand;

@Component
public class ClientController implements IController {
	private AmqpAdapter adapter;
	
	@Value("${rabbitmq.publishTo}")
	private String commandExchange;
	
	@Value("${rabbitmq.routingKey}")
	private String routingKey;
	
	@Autowired
	public ClientController(AmqpAdapter adapter) {
		super();
		this.adapter = adapter;
	}

	@Override
	public void login(String username, String password) {
		LoginCommand loginCommand = new LoginCommand(username, password);
		adapter.send(loginCommand, commandExchange, routingKey);
	}

}
