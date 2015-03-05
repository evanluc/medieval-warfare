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
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.command.StartGameCommand;

public class GameInitializerTest {
	@Mock private AmqpAdapter amqpAdapter;
	private GameInitializer gameInitializer;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		gameInitializer = new GameInitializer(amqpAdapter);
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
			then(amqpAdapter).should().send(command, "notify-exchange", p);
		}
	}

	@After
	public void tearDown() {
		amqpAdapter = null;
		gameInitializer = null;
	}
}
