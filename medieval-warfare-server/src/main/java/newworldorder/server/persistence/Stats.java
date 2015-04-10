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
	
	public void win() {
		wins++;
	}
	
	public void loss() {
		losses++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + losses;
		result = prime * result + wins;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		if (losses != other.losses)
			return false;
		if (wins != other.wins)
			return false;
		return true;
	}
}
