package newworldorder.common.network.message;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.service.IMatchController;
import newworldorder.common.service.IServerServiceLocator;

public class JoinGameCommandTest extends CommandTestBase {
	@Mock private IServerServiceLocator locator;
	@Mock private IMatchController controller;
	private GameRequest gameRequest;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(locator.getMatchController()).thenReturn(controller);
		sender = "player1";
		gameRequest = new GameRequest(sender, 3);
		JoinGameCommand command = new JoinGameCommand(sender, gameRequest);
		command.setServiceLocator(locator);
		this.command = command;
	}

	@Override
	@After
	public void tearDown() {
		locator = null;
		controller = null;
		gameRequest = null;
		super.tearDown();
	}

	@Test
	public void testExecute() {
		command.execute();
		then(controller).should().addToQueue(gameRequest);
	}

	@Test
	public void testEquals() {
		JoinGameCommand other = new JoinGameCommand(sender, gameRequest);
		assertEquals(command, other);
	}
}
