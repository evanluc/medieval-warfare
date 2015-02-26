package newworldorder.common.matchmaking;

import java.io.Serializable;
import java.util.List;

public class GameInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6846664976644536673L;
	private List<String> players;
	private String gameExchange;
	
	public GameInfo(List<String> players, String gameExchange) {
		super();
		this.players = players;
		this.gameExchange = gameExchange;
	}

	public List<String> getPlayers() {
		return players;
	}

	public String getGameExchange() {
		return gameExchange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameExchange == null) ? 0 : gameExchange.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
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
		GameInfo other = (GameInfo) obj;
		if (gameExchange == null) {
			if (other.gameExchange != null)
				return false;
		} else if (!gameExchange.equals(other.gameExchange))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GameInfo [players=" + players + ", gameExchange=" + gameExchange + "]";
	}
}
