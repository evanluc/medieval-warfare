package newworldorder.server.matchmaking;

import java.util.Set;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.matchmaking.IMatchController;

public class MatchController implements IMatchController {
	
	private static MatchController instance;
	
	private MatchQueue twoPlayerQueue;
	private MatchQueue threePlayerQueue;
	private MatchQueue fourPlayerQueue;
	
	private MatchController() {
		twoPlayerQueue = new MatchQueue(2);
		threePlayerQueue = new MatchQueue(3);
		fourPlayerQueue = new MatchQueue(4);
	}
	
	public static MatchController getInstance() {
		if (instance == null) {
			instance = new MatchController();
		}
		
		return instance;
	}
	
	@Override
	synchronized public void addToQueue(GameRequest gameRequest) {
		String username = gameRequest.getUsername();
		int numPlayers = gameRequest.getNumPlayers();
		MatchQueue queue = getQueue(numPlayers);
		queue.insertPlayer(username);
		
		if (queue.hasGame()) {
			Set<String> players = queue.popGame();
			// TODO: create fanout exchange named by first player, command each player
			// to subscribe to the exchange.
		}
		
	}
	
	private MatchQueue getQueue(int numPlayers) {
		switch (numPlayers) {
			case 2:
				return twoPlayerQueue;
			case 3:
				return threePlayerQueue;
			case 4:
				return fourPlayerQueue;
			default:
				throw new IllegalArgumentException();
		}
	}
}
