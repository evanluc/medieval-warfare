package newworldorder.common.network.command;

import newworldorder.common.model.IStats;

public class GetPlayerStatsCommand extends RemoteCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8674003828629957204L;
	private int[] stats;
	
	public GetPlayerStatsCommand(String sender) {
		super(sender);
	}

	@Override
	public void execute() {
		IStats tStats = this.getServiceLocator().getStatsForPlayer(this.getSender());
		stats = new int[] {tStats.getWins(), tStats.getLosses()};
	}
	
	public int[] getStats() {
		return stats;
	}
}
