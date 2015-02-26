package newworldorder.common.matchmaking;

import java.io.Serializable;

public class GameRequest implements Serializable {
	private String username;
	private int numPlayers;
	
	public GameRequest(String username, int numPlayers) {
		super();
		this.username = username;
		this.numPlayers = numPlayers;
	}

	public String getUsername() {
		return username;
	}

	public int getNumPlayers() {
		return numPlayers;
	}
}
