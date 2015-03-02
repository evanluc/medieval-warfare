package newworldorder.server.matchmaking;

import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;

import newworldorder.common.network.command.StartGameCommand;

import newworldorder.common.matchmaking.GameInfo;

public class GameInitializerTest {
	@Mock private AmqpTemplate amqpTemplate;
	private GameInitializer gameInitializer;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		gameInitializer = new GameInitializer(amqpTemplate);
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
			then(amqpTemplate).should().convertAndSend(p, command);
		}
	}

	@After
	public void tearDown() {
		amqpTemplate = null;
		gameInitializer = null;
	}
}
