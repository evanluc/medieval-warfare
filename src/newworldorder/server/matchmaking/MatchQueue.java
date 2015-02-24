package newworldorder.server.matchmaking;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MatchQueue {
	
	private int numPlayers;
	private ConcurrentLinkedQueue<String> playerQueue = new ConcurrentLinkedQueue<>();
	
	public MatchQueue(int numPlayers) {
		super();
		this.numPlayers = numPlayers;
	}

	public boolean hasGame() {
		return playerQueue.size() >= numPlayers;
	}
	
	public Set<String> popGame() {
		if (!hasGame()) throw new NoSuchElementException();
		
		HashSet<String> players = new HashSet<>();
		
		for (int i = 0; i < numPlayers; i++) {
			players.add(playerQueue.remove());
		}
		
		return players;
	}
	
	public void insertPlayer(String username) {
		playerQueue.add(username);
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public int size() {
		return playerQueue.size();
	}
}
