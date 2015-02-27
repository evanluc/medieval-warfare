package newworldorder.server.matchmaking;

import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.message.StartGameCommand;

public class GameInitializerTest {
	@Mock private IRoutingProducer producer;
	private GameInitializer gameInitializer;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
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
			then(producer).should().sendCommand(command, p);
		}
	}
	
	@After
	public void tearDown() {
		producer = null;
		gameInitializer = null;
	}
}
