package newworldorder.server.persistence;

public class Stats implements newworldorder.common.model.Stats {
	private int wins, losses;
	
	public Stats(int wins, int losses) {
		this.wins = wins;
		this.losses = losses;
	}

	@Override
	public int getWins() {
		return wins;
	}
	
	@Override
	public int getLosses() {
		return losses;
	}
}
