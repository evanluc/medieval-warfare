package newworldorder.common.network.command;

import newworldorder.common.model.Stats;

public class GetPlayerStatsCommand extends RemoteCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8674003828629957204L;
	private transient Stats stats;
	
	public GetPlayerStatsCommand(String sender) {
		super(sender);
	}

	@Override
	public void execute() {
		stats = this.getServiceLocator().getStatsForPlayer(this.getSender());
	}
	
	public Stats getStats() {
		return stats;
	}
}
