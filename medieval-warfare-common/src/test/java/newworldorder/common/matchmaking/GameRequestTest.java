package newworldorder.common.matchmaking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameRequestTest {
	@Test
	public void gameRequestTest() {
		GameRequest request = new GameRequest("player1", 2);
		assertEquals("player1", request.getUsername());
		assertEquals(2, request.getNumPlayers());
	}
}
