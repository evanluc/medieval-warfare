package newworldorder.server.matchmaking;

import java.util.List;
import java.util.UUID;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.message.ClientCommand;
import newworldorder.common.network.message.StartGameCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameInitializer {
	private IRoutingProducer producer;
	
	@Autowired
	public GameInitializer(IRoutingProducer producer) {
		this.producer = producer;
//		this.producer = ActorFactory.createRoutingProducer("localhost", "notifyExchange");
	}
	
	public void initializeGame(List<String> players) {
		System.out.println("Game popped.");
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
