package newworldorder.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import newworldorder.client.shared.TerrainType;

import org.junit.Before;
import org.junit.Test;

public class ModelSerializerTest {

	private Map testMap;

	private Map aMap;
	List<Player> playerList;
	private Player p1, p2;
	private Game testGame;

	@Before
	public void setUp() throws Exception {
		testMap = new Map(3, 2);
		testMap.getTile(0, 0).setTerrainType(TerrainType.SEA);
		testMap.getTile(1, 0).setTerrainType(TerrainType.SEA);
		testMap.getTile(2, 0).setTerrainType(TerrainType.GRASS);
		testMap.getTile(0, 1).setTerrainType(TerrainType.GRASS);
		testMap.getTile(1, 1).setTerrainType(TerrainType.MEADOW);
		testMap.getTile(2, 1).setTerrainType(TerrainType.TREE);

		p1 = new Player("Yung Lean");
		p2 = new Player("2 Chainz");
		playerList = new ArrayList<Player>();
		playerList.add(p1);
		playerList.add(p2);
		aMap = new Map(10, 10);
		testGame = new Game(playerList, aMap);
		testGame.setTurnOf(p1);
		Set<Tile> reg1 = new HashSet<Tile>();
		reg1.add(aMap.getTile(0, 0));
		reg1.add(aMap.getTile(1, 0));
		reg1.add(aMap.getTile(0, 1));
		new Village(aMap.getTile(0, 0), p1, reg1);

		Set<Tile> reg2 = new HashSet<Tile>();
		reg2.add(aMap.getTile(1, 1));
		reg2.add(aMap.getTile(2, 1));
		reg2.add(aMap.getTile(1, 2));
		reg2.add(aMap.getTile(2, 2));
		new Village(aMap.getTile(2, 2), p2, reg2);
	}

	@Test
	public void testLoadMap() {
		Map loadMap = null;
		try {
			loadMap = ModelSerializer.loadMap("/testfiles/junit-test-map-load.mwm");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		for (Tile t : loadMap.getTiles()) {
			assertEquals(t.getTerrainType(), testMap.getTile(t.hashCode()).getTerrainType());
		}
	}

	@Test
	public void testSaveMap() {
		try {
			ModelSerializer.saveMap(testMap, "/testfiles/junit-test-map-save.mwm");
		}
		catch (Exception e) {
			fail("Unable to save, test failed");
		}
	}

	@Test
	public void testLoadGameState() {
		Game loadGame = null;
		try {
			loadGame = ModelSerializer.loadGameState("/testfiles/junit-test-game-load.mwm");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertEquals(loadGame.getPlayers().size(), testGame.getPlayers().size());
		assertEquals(loadGame.getRoundCount(), testGame.getRoundCount());
		assertEquals(loadGame.getMap().getHeight(), testGame.getMap().getHeight());
		assertEquals(loadGame.getMap().getWidth(), testGame.getMap().getWidth());

		for (Player p : loadGame.getPlayers()) {
			boolean exists = false;
			for (Player sp : testGame.getPlayers()) {
				if (p.getPlayerId() == sp.getPlayerId())
					exists = true;
			}
			assertTrue(exists);
		}

		for (int y = 0; y < loadGame.getMap().getHeight(); y++) {
			for (int x = 0; x < loadGame.getMap().getWidth(); x++) {
				Tile lt = loadGame.getMap().getTile(x, y);
				Tile tt = testGame.getMap().getTile(x, y);

				assertEquals(lt.getTerrainType(), tt.getTerrainType());
				if (lt.getVillage() != null) {
					assertEquals(lt.getVillage().getVillageType(), tt.getVillage().getVillageType());
					assertEquals(lt.getVillage().getControlledBy().getPlayerId(), tt.getVillage().getControlledBy().getPlayerId());
				}
			}
		}
	}

	@Test
	public void testSaveGameState() {
		try {
			ModelSerializer.saveGameState(testGame, "/testfiles/junit-test-game-save.mwm");
		}
		catch (Exception e) {
			fail("Unable to save, test failed");
		}
	}

}
