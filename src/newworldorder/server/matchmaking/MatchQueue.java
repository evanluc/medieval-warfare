package newworldorder.server.matchmaking;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import newworldorder.server.model.Player;

public class MatchQueue {
	
	private int numPlayers;
	private ConcurrentLinkedQueue<Player> playerQueue = new ConcurrentLinkedQueue<>();
	
	public MatchQueue(int numPlayers) {
		super();
		this.numPlayers = numPlayers;
	}

	public boolean hasGame() {
		return playerQueue.size() >= numPlayers;
	}
	
	public Set<Player> popGame() {
		HashSet<Player> players = new HashSet<>();
		
		for (int i = 0; i < numPlayers; i++) {
			players.add(playerQueue.remove());
		}
		
		return players;
	}
	
	public void insertPlayer(Player p) {
		playerQueue.add(p);
	}
}
