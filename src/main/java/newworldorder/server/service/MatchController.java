package newworldorder.server.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.factory.ActorFactory;
import newworldorder.common.network.message.ClientCommand;
import newworldorder.common.network.message.StartGameCommand;
import newworldorder.common.service.IMatchController;
import newworldorder.server.matchmaking.MatchQueue;

public class MatchController implements IMatchController {
	
	private static MatchController instance;
	
	private MatchQueue twoPlayerQueue;
	private MatchQueue threePlayerQueue;
	private MatchQueue fourPlayerQueue;
	
	private IRoutingProducer producer;
	
	private MatchController() throws IOException {
		twoPlayerQueue = new MatchQueue(2);
		threePlayerQueue = new MatchQueue(3);
		fourPlayerQueue = new MatchQueue(4);
		producer = ActorFactory.createRoutingProducer("localhost", "notifyExchange");
	}
	
	public static MatchController getInstance() throws IOException {
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
			List<String> players = queue.popGame();
			String gameExchangeName = UUID.randomUUID().toString();
			
			GameInfo gameInfo = new GameInfo(players, gameExchangeName);
			ClientCommand command = new StartGameCommand("server", gameInfo);
			
			for (String player : players) {
				try {
					producer.sendCommand(command, player);
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
