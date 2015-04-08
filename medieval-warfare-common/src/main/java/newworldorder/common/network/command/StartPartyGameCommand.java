package newworldorder.common.network.command;

import java.util.List;

import newworldorder.common.service.IGameInitializer;

public class StartPartyGameCommand extends RemoteCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3296541352318158264L;
	private List<String> players;
	
	public StartPartyGameCommand(String sender, List<String> players) {
		super(sender);
		this.players = players;
	}

	@Override
	public void execute() {
		IGameInitializer gameInitializer = this.getServiceLocator().getGameInitializer();
		gameInitializer.initializeGame(players);
	}
}
