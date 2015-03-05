package newworldorder.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.AbstractCommand;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.network.command.RemoteCommand;

import newworldorder.common.service.IServerServiceLocator;

@Component
public class RequestDispatcher implements CommandHandler {
	private final IServerServiceLocator locator;

	@Autowired
	public RequestDispatcher(IServerServiceLocator locator) {
		this.locator = locator;
	}

	@Override
	public void handle(AbstractCommand command) {
		if (command instanceof RemoteCommand) {
			RemoteCommand remoteCommand = (RemoteCommand) command;
			remoteCommand.setServiceLocator(locator);
			remoteCommand.execute();
		}
	}
}
