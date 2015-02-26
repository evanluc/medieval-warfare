package newworldorder.common.matchmaking;

public class GameRequest {
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
