package newworldorder.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.AbstractCommand;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.network.command.CreateAccountCommand;
import newworldorder.common.network.command.GetOnlinePlayersCommand;
import newworldorder.common.network.command.LoginCommand;
import newworldorder.common.network.command.RemoteCommand;
import newworldorder.common.network.command.ReplyCommandHandler;
import newworldorder.common.service.IServerServiceLocator;

@Component
public class RequestHandler implements CommandHandler, ReplyCommandHandler {
	private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final IServerServiceLocator locator;

	@Autowired
	public RequestHandler(IServerServiceLocator locator) {
		this.locator = locator;
	}

	@Override
	public void handle(AbstractCommand command) {
		logger.info("Received new command from [" + command.getSender() + "]: " + command.toString());
		
		if (command instanceof RemoteCommand) {
			RemoteCommand remoteCommand = (RemoteCommand) command;
			logger.info("Command: " + command.toString());
			remoteCommand.setServiceLocator(locator);
			remoteCommand.execute();
		}
		else {
			logger.error("Received command not a remote command. Ignoring.");
		}
	}

	@Override
	public Object handleAndReply(AbstractCommand command) {
		logger.info("Received new command from [" + command.getSender() + "]: " + command.toString());
		
		if (command instanceof LoginCommand) {
			LoginCommand loginCommand = (LoginCommand) command;
			loginCommand.setServiceLocator(locator);
			loginCommand.execute();
			return loginCommand.getResult();
		}
		else if (command instanceof CreateAccountCommand) {
			CreateAccountCommand accountCommand = (CreateAccountCommand) command;
			accountCommand.setServiceLocator(locator);
			accountCommand.execute();
			return accountCommand.getResult();
		}
		else if (command instanceof GetOnlinePlayersCommand) {
			GetOnlinePlayersCommand c = (GetOnlinePlayersCommand) command;
			c.setServiceLocator(locator);
			c.execute();
			return c.getOnlinePlayers();
		}
		else {
			return null;
		}
	}
}
