package newworldorder.client.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import newworldorder.client.shared.TerrainType;
import newworldorder.client.shared.UnitType;

import org.junit.Test;
import org.junit.Before;

public class RegionTest
{
	private Unit u;
	private Map aMap;
	private Village aVillage;
	private Player p1, p2;
	private Region reg;
	@Before
	public void setUp() {
		p1 = new Player("Yung Lean");
		p2 = new Player("2 Chainz");
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(p1);
		playerList.add(p2);
		aMap = new Map(10,10);
		Game aGame = new Game(playerList, aMap);
		Set<Tile> aReg = new HashSet<Tile>();
		aReg.add(aMap.getTile(0, 0));
		aReg.add(aMap.getTile(1, 0));
		aReg.add(aMap.getTile(0, 1));
		aReg.add(aMap.getTile(1, 1));
		aVillage = new Village(aMap.getTile(0, 0), p1, aReg);
		reg = aVillage.getRegion();
		u = new Unit(UnitType.PEASANT, aVillage, aMap.getTile(1, 1));
    }
	@Test
    public void testRemoveTile() {
		reg.removeTile(aMap.getTile(1, 1));
		assertFalse(reg.getTiles().contains(aMap.getTile(1,1)));
		assertTrue(aMap.getTile(1, 1).getRegion() == null);
    }

	@Test
    public void testAddTile() {
		reg.addTile(aMap.getTile(4,4));
		assertTrue(reg.getTiles().contains(aMap.getTile(4, 4)));
		assertTrue(aMap.getTile(4, 4).getRegion() == reg);
    }

	@Test
    public void testGetTiles() {
		List<Tile> tiles = reg.getTiles();
		assertTrue(tiles.contains(aMap.getTile(0, 0)));
		assertTrue(tiles.contains(aMap.getTile(0, 1)));
		assertTrue(tiles.contains(aMap.getTile(1, 0)));
		assertTrue(tiles.contains(aMap.getTile(1, 1)));
    }

	@Test
    public void testGetVillage() {
		assertTrue(reg.getVillage() == aVillage);
    }

	@Test
    public void testSetVillage() {
    	Set<Tile> newRegion = new HashSet<Tile>();
    	newRegion.add(aMap.getTile(5, 5));
    	Village testV = new Village(aMap.getTile(5,5), u.getControllingPlayer(), newRegion); 
    	reg.setVillage(testV);
    	assertTrue(reg.getVillage() == testV);
    }

	@Test
    public void getControllingPlayer() {
		assertTrue(reg.getControllingPlayer() == p1);
    }

	@Test
    public void testGetTileCount() {
		aMap.getTile(0, 0).setTerrainType(TerrainType.MEADOW);
		assertTrue(reg.getTileCount(TerrainType.MEADOW) == 1);
    }

	@Test
    public void testCreateVillage() {
		reg.setVillage(null);
		reg.createVillage();
		assertFalse(reg.getVillage() == null);
    }
}
