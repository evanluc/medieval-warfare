package newworldorder.server.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PlayerStatistics {
	private Map<String, Stats> allStats;
	
	public PlayerStatistics() {
		allStats = new HashMap<>();
	}
	
	public Stats getUserStats(String username) {
		if (!allStats.containsKey(username)) {
			Stats newStats = new Stats(0, 0);
			allStats.put(username, newStats);
			return newStats;
		}
		else {
			return allStats.get(username);
		}
	}
	
	public void win(String username) {
		Stats s = getUserStats(username);
		s.win();
	}
	
	public void loss(String username) {
		Stats s = getUserStats(username);
		s.loss();
	}
}
