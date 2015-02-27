package newworldorder.common.matchmaking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GameInfoTest {
	@Test
	public void testGameInfo() {
		List<String> players = new ArrayList<>();
		players.add("player1");
		players.add("player2");
		players.add("player3");
		String exchange = "test-exchange-1";
		
		GameInfo gameInfo = new GameInfo(players, exchange);
		assertEquals(players, gameInfo.getPlayers());
		assertEquals(exchange, gameInfo.getGameExchange());
		
		GameInfo other = new GameInfo(players, exchange);
		assertEquals(gameInfo, other);
	}
}
