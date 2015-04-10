package newworldorder.server.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerStatsticsTest {
	private PlayerStatistics stats;
	
	@Before
	public void setup() {
		stats = new PlayerStatistics();
	}
	
	@After
	public void tearDown() {
		stats = null;
	}
	
	@Test
	public void testGetStatsForNewUser() {
		Stats expected = new Stats(0, 0);
		Stats actual = stats.getUserStats("newplayer");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testWin() {
		Stats expected = new Stats(1, 0);
		stats.win("test-user-1");
		Stats actual = stats.getUserStats("test-user-1");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLoss() {
		Stats expected = new Stats(0, 1);
		stats.loss("test-user-1");
		Stats actual = stats.getUserStats("test-user-1");
		assertEquals(expected, actual);
	}

}
