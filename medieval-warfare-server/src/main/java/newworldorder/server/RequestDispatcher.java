package newworldorder.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.AbstractCommand;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.network.command.RemoteCommand;
import newworldorder.common.service.IServerServiceLocator;

@Component
public class RequestDispatcher implements CommandHandler {
	private final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
	private final IServerServiceLocator locator;

	@Autowired
	public RequestDispatcher(IServerServiceLocator locator) {
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
}
