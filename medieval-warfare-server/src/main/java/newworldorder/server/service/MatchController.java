package newworldorder.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.service.IMatchController;
import newworldorder.server.matchmaking.GameInitializer;
import newworldorder.server.matchmaking.MatchQueue;

@Component
public class MatchController implements IMatchController {
	private MatchQueue twoPlayerQueue;
	private MatchQueue threePlayerQueue;
	private MatchQueue fourPlayerQueue;
	private GameInitializer gameInitializer;
	
	@Autowired
	public MatchController(GameInitializer gameInitializer) {
		twoPlayerQueue = new MatchQueue(2);
		threePlayerQueue = new MatchQueue(3);
		fourPlayerQueue = new MatchQueue(4);
		this.gameInitializer = gameInitializer;
	}
	
	@Override
	synchronized public void addToQueue(GameRequest gameRequest) {
		String username = gameRequest.getUsername();
		int numPlayers = gameRequest.getNumPlayers();
		MatchQueue queue = getQueue(numPlayers);
		queue.insertPlayer(username);
		
		if (queue.hasGame()) {
			List<String> players = queue.popGame();
			gameInitializer.initializeGame(players);
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
