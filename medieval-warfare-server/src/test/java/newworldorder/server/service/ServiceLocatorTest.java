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
	
	public ServiceLocatorTest(ServiceLocator locator) {
		super();
		this.locator = locator;
	}
	
//	@Test
//	public void testLocateMatchController() throws IOException {
//		IMatchController expected = MatchController.getInstance();
//		IMatchController actual = locator.getMatchController();
//		assertEquals(expected, actual);
//	}
}
