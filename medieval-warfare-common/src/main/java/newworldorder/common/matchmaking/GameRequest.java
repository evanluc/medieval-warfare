package newworldorder.common.matchmaking;

import java.io.Serializable;

public class GameRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2994315251237909617L;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numPlayers;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		GameRequest other = (GameRequest) obj;
		if (numPlayers != other.numPlayers)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
