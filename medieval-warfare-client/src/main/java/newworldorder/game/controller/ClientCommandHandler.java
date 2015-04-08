package newworldorder.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.AbstractCommand;
import newworldorder.common.network.command.CheckPartyCommand;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.network.command.ReplyCommandHandler;
import newworldorder.common.service.IClientServiceLocator;

@Component
public class ClientCommandHandler implements CommandHandler, ReplyCommandHandler {
	private final IClientServiceLocator locator;

	@Autowired
	public ClientCommandHandler(IClientServiceLocator locator) {
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

	@Override
	public Object handleAndReply(AbstractCommand command) {
		if (command instanceof CheckPartyCommand) {
			CheckPartyCommand chkCommand = (CheckPartyCommand) command;
			chkCommand.execute();
			return chkCommand.canJoin();
		}
		else return null;
	}
}
