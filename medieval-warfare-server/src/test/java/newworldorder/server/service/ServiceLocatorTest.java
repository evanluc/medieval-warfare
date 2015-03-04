package newworldorder.server.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;

import newworldorder.common.service.IMatchController;
import newworldorder.server.matchmaking.GameInitializer;

public class ServiceLocatorTest {
	private ServiceLocator locator;

	@Test
	public void testLocateMatchController() throws IOException {
		IMatchController controller = new MatchController(new GameInitializer(Mockito.mock(AmqpAdapter.class)));
		locator = new ServiceLocator(controller);

		assertEquals(controller, locator.getMatchController());
	}
}
