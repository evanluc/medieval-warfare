package newworldorder.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.service.IMatchController;
import newworldorder.server.matchmaking.GameInitializer;
import newworldorder.server.matchmaking.MatchQueue;

@Component
public class MatchController implements IMatchController {
	private final Logger logger = LoggerFactory.getLogger(MatchController.class);
	private final MatchQueue twoPlayerQueue;
	private final MatchQueue threePlayerQueue;
	private final MatchQueue fourPlayerQueue;
	private final GameInitializer gameInitializer;

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
		
		logger.info("Adding user '" + username + "' to queue for " + numPlayers + "-player game.");
		
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
