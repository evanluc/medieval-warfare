package newworldorder.common.network.command;

import java.util.List;

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
		// TODO Auto-generated method stub

	}

}
