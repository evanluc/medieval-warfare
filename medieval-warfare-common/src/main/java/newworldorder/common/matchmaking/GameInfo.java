package newworldorder.common.matchmaking;

import java.util.List;

public class GameInfo {
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
