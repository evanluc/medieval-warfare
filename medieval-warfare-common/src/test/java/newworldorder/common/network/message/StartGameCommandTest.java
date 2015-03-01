package newworldorder.common.network.message;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.service.IClientServiceLocator;
import newworldorder.common.service.IGameLauncher;

public class StartGameCommandTest extends CommandTestBase {
	@Mock IClientServiceLocator locator;
	@Mock IGameLauncher launcher;
	private GameInfo gameInfo;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(locator.getGameLauncher()).thenReturn(launcher);
		gameInfo = getGameInfo();
		StartGameCommand command = new StartGameCommand("server", gameInfo);
		command.setServiceLocator(locator);
		this.command = command;
		sender = "server";
	}

	@Override
	@After
	public void tearDown() {
		locator = null;
		launcher = null;
		gameInfo = null;
		super.tearDown();
	}

	@Test
	public void testExecute() {
		command.execute();
		then(launcher).should().launchGame(gameInfo);
	}

	@Test
	public void testEquals() {
		StartGameCommand other = new StartGameCommand("server", getGameInfo());
		assertEquals(command, other);
	}

	private GameInfo getGameInfo() {
		List<String> players = new ArrayList<>();
		players.add("player1");
		players.add("player2");
		players.add("player3");
		return new GameInfo(players, "test-exchange-1");
	}
}
