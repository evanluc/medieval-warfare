package newworldorder.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.AbstractCommand;
import newworldorder.common.network.command.CheckPartyCommand;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.network.command.ProbeCommand;
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
			System.out.println("Received command: " + command.toString());
			ClientCommand clientCommand = (ClientCommand) command;
			clientCommand.setServiceLocator(locator);
			clientCommand.execute();
		}
	}

	@Override
	public Object handleAndReply(AbstractCommand command) {
		if (command instanceof CheckPartyCommand) {
			System.out.println("Received command: " + command.toString());
			CheckPartyCommand checkCommand = (CheckPartyCommand) command;
			checkCommand.setServiceLocator(locator);
			checkCommand.execute();
			return checkCommand.canJoin();
		}
		else if (command instanceof ProbeCommand) {
			ProbeCommand c = (ProbeCommand) command;
			c.execute();
			return c.getStatus();
		}
		else {
			return null;
		}
	}
}
