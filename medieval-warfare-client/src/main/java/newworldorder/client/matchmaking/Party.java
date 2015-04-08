package newworldorder.client.matchmaking;

import java.util.List;

public class Party {
	private List<String> playersInParty;
	private String leader;
	private static Party instance = null;
	
	private Party() {
		super();
	}
	
	public static Party getInstance() {
		if (instance == null) {
			instance = new Party();
		}
		return instance;
	}
	public List<String> getPlayersInParty() {
		return playersInParty;
	}

	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
}
