package newworldorder.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.AbstractCommand;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.service.IClientServiceLocator;

@Component
public class GameCommandHandler implements CommandHandler {
	private final IClientServiceLocator locator;

	@Autowired
	public GameCommandHandler(IClientServiceLocator locator) {
		this.locator = locator;
	}

	@Override
	public void handle(AbstractCommand command) {
		if (command instanceof ClientCommand) {
			ClientCommand clientCommand = (ClientCommand) command;
			clientCommand.setServiceLocator(locator);
			clientCommand.execute();
		}
	}
}
