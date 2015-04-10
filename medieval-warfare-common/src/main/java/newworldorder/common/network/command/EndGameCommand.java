package newworldorder.common.network.command;

import java.util.List;

import newworldorder.common.service.IServerServiceLocator;

public class EndGameCommand extends RemoteCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6210745370606635268L;
	private List<String> losers;
	private String winner;
	
	public EndGameCommand(String sender, List<String> losers, String winner) {
		super(sender);
		this.losers = losers;
		this.winner = winner;
	}

	@Override
	public void execute() {
		IServerServiceLocator locator = this.getServiceLocator();
		for (String player : losers) {
			locator.loss(player);
		}
		
		locator.win(winner);
	}

}
