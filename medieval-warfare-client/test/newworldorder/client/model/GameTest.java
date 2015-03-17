package newworldorder.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import newworldorder.game.model.Game;
import newworldorder.game.model.Map;
import newworldorder.game.model.Player;
import newworldorder.game.model.Tile;

public class GameTest {
	private Map map;
	private Player p1, p2;
	private Game game;
	private List<Player> playerList;
	private List<Tile> tileList;

	@Before
	public void setUp() throws Exception {
		p1 = new Player("Yung", "Lean", 0, 0, null);
		p2 = new Player("2", "Chainz", 0, 0, null);
		playerList = new ArrayList<Player>();
		playerList.add(p1);
		playerList.add(p2);
		map = new Map(10, 10);
		game = new Game(playerList, map);
		p1.setCurrentGame(game);
		p2.setCurrentGame(game);
		tileList = new ArrayList<Tile>();
		tileList.add(map.getTile(0, 0));
		tileList.add(map.getTile(1, 0));
		tileList.add(map.getTile(0, 1));
		tileList.add(map.getTile(1, 1));
	}

	@Test
	public void testGetSetRoundCount() {
		assertEquals(game.getRoundCount(), 0);
		game.incrementRoundCount();
		assertEquals(game.getRoundCount(), 1);
	}

	@Test
	public void testGetSetHasWon() {
		assertFalse(game.hasWon());
		game.setHasWon(true);
		assertTrue(game.hasWon());
	}

	@Test
	public void testGetPlayers() {
		for (Player p : game.getPlayers()) {
			assertTrue(playerList.contains(p));
		}
	}

	@Test
	public void testGetSetTurnOf() {
		game.setTurnOf(p1);
		assertEquals(game.getCurrentTurnPlayer(), p1);
		game.setTurnOf(p2);
	}

	@Test
	public void testGetMap() {
		assertEquals(game.getMap(), map);
	}

	@Test
	public void testGetAllChat() {
		// TODO: test when implemented
		// fail("Not yet implemented");
	}

	@Test
	public void testGetChatSentBy() {
		// TODO: test when implemented
		// fail("Not yet implemented");
	}

}
