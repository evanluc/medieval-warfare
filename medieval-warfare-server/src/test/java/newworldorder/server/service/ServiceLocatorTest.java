package newworldorder.server.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.service.IMatchController;
import newworldorder.server.matchmaking.GameInitializer;

import org.junit.Test;
import org.mockito.Mockito;

public class ServiceLocatorTest {
	private ServiceLocator locator;

	@Test
	public void testLocateMatchController() throws IOException {
		IRoutingProducer mockedProducer = Mockito.mock(IRoutingProducer.class);
		IMatchController controller = new MatchController(new GameInitializer(mockedProducer));
		locator = new ServiceLocator(controller);

		assertEquals(controller, locator.getMatchController());
	}
}
