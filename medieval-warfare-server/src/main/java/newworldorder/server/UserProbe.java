package newworldorder.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.ProbeCommand;
import newworldorder.server.service.OnlineUsers;

@Component
public class UserProbe implements Runnable {
	private final Logger logger = LoggerFactory.getLogger(UserProbe.class);
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
		logger.info("Probing players...");
		ProbeCommand c = new ProbeCommand("server");
		
		for (String user : users.getAll()) {
			logger.info("Probing " + user);
			Boolean status = (Boolean) template.convertSendAndReceive(exchange, user, c);
			if (status == null || status == false) {
				logger.info("Offline.");
				users.remove(user);
			}
			else {
				logger.info("Online.");
			}
		}

	}

}
