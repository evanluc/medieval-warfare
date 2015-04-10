package newworldorder.server;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.ProbeCommand;
import newworldorder.server.service.OnlineUsers;

@Component
public class UserProbe implements Runnable {
	private OnlineUsers users;
	private AmqpTemplate template;
	
	@Value("${rabbitmq.publishTo}")
	private String exchange;
	
	@Autowired
	public UserProbe(OnlineUsers users, AmqpTemplate template) {
		this.users = users;
		this.template = template;
	}

	@Override
	public void run() {
		ProbeCommand c = new ProbeCommand("server");
		
		for (String user : users.getAll()) {
			Boolean status = (Boolean) template.convertSendAndReceive(exchange, user, c);
			if (status == null || status == false) {
				users.remove(user);
			}
		}

	}

}
