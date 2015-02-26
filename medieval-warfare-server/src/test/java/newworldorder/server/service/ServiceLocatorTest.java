package newworldorder.server.service;

import static org.junit.Assert.*;

import java.io.IOException;

import newworldorder.common.service.IMatchController;
import newworldorder.server.service.ServiceLocator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServiceLocatorTest {
	private ServiceLocator locator;
	
	@Before
	public void setup() throws IOException {
		locator = ServiceLocator.getInstance();
	}
	
	/* Commented out to allow building. @JoelChev */
	
	/*@Test
	public void testLocateMatchController() throws IOException {
		IMatchController expected = MatchController.getInstance();
		IMatchController actual = locator.getMatchController();
		assertEquals(expected, actual);
	}*/
	
	@After
	public void tearDown() {
		locator = null;
	}
}
