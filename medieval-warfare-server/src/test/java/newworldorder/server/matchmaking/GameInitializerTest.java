package newworldorder.server.matchmaking;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.message.StartGameCommand;

import org.junit.Before;
import org.junit.Test;

public class GameInitializerTest {
	private IRoutingProducer producer;
	private GameInitializer gameInitializer;
	
	@Before
	public void setup() {
		producer = mock(IRoutingProducer.class);
		gameInitializer = new GameInitializer(producer);
	}
	
	@Test
	public void testInitializeGame() throws Exception {
		List<String> players = new ArrayList<>();
		players.add("player1");
		players.add("player2");
		players.add("player3");
		
		String exchangeName = String.valueOf(players.hashCode());
		GameInfo info = new GameInfo(players, exchangeName);
		StartGameCommand command = new StartGameCommand("server", info);
		
		gameInitializer.initializeGame(players);
		
		for (String p : players) {
			verify(producer).sendCommand(command, p);
		}
	}
}
