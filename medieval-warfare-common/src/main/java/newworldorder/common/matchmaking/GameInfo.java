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
}
