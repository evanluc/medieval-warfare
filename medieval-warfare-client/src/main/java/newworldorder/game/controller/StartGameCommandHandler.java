package newworldorder.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.AbstractCommand;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.service.IClientServiceLocator;

@Component
public class StartGameCommandHandler implements CommandHandler {
	private final IClientServiceLocator locator;

	@Autowired
	public StartGameCommandHandler(IClientServiceLocator locator) {
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
