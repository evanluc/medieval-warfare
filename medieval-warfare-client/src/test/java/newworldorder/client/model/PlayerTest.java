package newworldorder.client.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	
	private Player p1;
	private Player p2;
	private Player p3;
	private List<Player> players;
	
	@Before
	public void setUp(){
		p1 = new Player("WalterWhite", "MethKing50", 0, 0, null);
		p2 = new Player("GobBot", "Kek", 2, 0, null);
		p3 = new Player("LucilleBot", "vodka,neat", 1, 5, null);
		players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
	}

	@Test
	public void testSetUpPlayers() {
		Player.setUpPlayers(players);
		assertTrue(p1.getColour() != null);
		assertTrue(p2.getColour() != null);
		assertTrue(p3.getColour() != null);
		assertTrue(p3.getColour() != p1.getColour());
		assertTrue(p3.getColour() != p2.getColour());
		assertTrue(p2.getColour() != p1.getColour());
	}

	@Test
	public void testGetUsername() {
		assertTrue(p1.getUsername().equals("WalterWhite"));
	}

	@Test
	public void testGetPassword() {
		assertTrue(p3.getPassword().equals("vodka,neat"));
	}

	@Test
	public void testGetSetStatus() {
		p2.setStatus(PlayerStatus.OFFLINE);
		assertTrue(p2.getStatus() == PlayerStatus.OFFLINE);
		p2.setStatus(PlayerStatus.ONLINE);
		assertTrue(p2.getStatus() == PlayerStatus.ONLINE);
	}

	@Test
	public void testGetSetWins() {
		assertTrue(p1.getWins() == 0);
		p1.incrementWinCount();
		assertTrue(p1.getWins() == 1);
	}

	@Test
	public void testGetSetLosses() {
		assertTrue(p1.getLosses() == 0);
		p1.incrementLossCount();
		assertTrue(p1.getLosses() == 1);
	}

	@Test
	public void testGetSetColour() {
		p2.setColour(ColourType.BLUE);
		assertTrue(p2.getColour() == ColourType.BLUE);
	}

}
