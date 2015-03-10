package newworldorder.game.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;

import newworldorder.game.model.Game;
import newworldorder.game.model.Map;
import newworldorder.game.model.Player;
import newworldorder.game.model.Region;
import newworldorder.game.model.StructureType;
import newworldorder.game.model.TerrainType;
import newworldorder.game.model.Tile;
import newworldorder.game.model.Unit;
import newworldorder.game.model.UnitType;
import newworldorder.game.model.Village;

public class TileTest {
	private Unit u;
	private Map aMap;
	private Village aVillage;
	private Player p1, p2;
	private Set<Tile> aReg;
	@Before
	public void setUp() {
		p1 = new Player("Yung", "Lean", 0, 0, null);
		p2 = new Player("2", "Chainz", 0, 0, null);
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(p1);
		playerList.add(p2);
		aMap = new Map(10,10);
		Game aGame = new Game(playerList, aMap);
		p1.setCurrentGame(aGame);
		p2.setCurrentGame(aGame);
		aReg = new HashSet<Tile>();
		aReg.add(aMap.getTile(0, 0));
		aReg.add(aMap.getTile(1, 0));
		aReg.add(aMap.getTile(0, 1));
		aReg.add(aMap.getTile(1, 1));
		aVillage = new Village(aMap.getTile(0, 0), p1, aReg);
		aMap.getTile(0, 0).setVillage(aVillage);
		u = new Unit(UnitType.PEASANT, aVillage, aMap.getTile(1, 1));
		aMap.getTile(1, 1).setUnit(u);
		aMap.getTile(0, 1).setStructure(StructureType.TOMBSTONE);
		aMap.getTile(0, 1).setTerrainType(TerrainType.MEADOW);
    }
	@Test
	public void testCacheNeighbors(){
		//aMap.getTile(0, 0).cacheNeighbours(); called in setUp(), when newMap(10,10) is called
		List<Tile> adj = aMap.getTile(0,0).getNeighbours();
		assertTrue(adj.contains(aMap.getTile(0,1)));
		assertTrue(adj.contains(aMap.getTile(1,0)));
		assertTrue(adj.contains(aMap.getTile(1,1)));

		adj.remove(aMap.getTile(0, 1));
		adj.remove(aMap.getTile(1,0));
		adj.remove(aMap.getTile(1, 1));
		assertTrue(adj.isEmpty());
	}
	@Test
    public void testGetStructure() {
        assertTrue(aMap.getTile(0, 1).getStructure() == StructureType.TOMBSTONE);
    }
    @Test
    public void testSetStructure() {
        aMap.getTile(1 , 0).setStructure(StructureType.WATCHTOWER);
        assertTrue(aMap.getTile(1, 0).getStructure() == StructureType.WATCHTOWER);
    }

    @Test
    public void testGetVillage(){
    	assertTrue(aMap.getTile(0, 0).getVillage() == aVillage);
    }
    @Test
    public void testSetVillage(){
    	Set<Tile> newRegion = new HashSet<Tile>();
    	newRegion.add(aMap.getTile(3, 3));
    	Village newV = new Village(aMap.getTile(3, 3), p1, newRegion);
    	aMap.getTile(3,3).setVillage(newV);
    	assertTrue(aMap.getTile(3, 3).getVillage() == newV);
    }
    @Test
    public void testGetRegion() {
        assertTrue(aMap.getTile(1, 0).getRegion() == aMap.getTile(0, 1).getRegion());
    }
    @Test
    public void testSetRegion() {
    	Set<Tile> tileList = new HashSet<Tile>();
    	tileList.add(aMap.getTile(3, 3));
    	Region newRegion = new Region(tileList, p1);
    	aMap.getTile(3,3).setRegion(newRegion);
    	assertTrue(aMap.getTile(3, 3).getRegion() == newRegion);
    }
    @Test
    public void testGetUnit() {
        assertTrue(aMap.getTile(1, 1).getUnit() == u);
    }
    @Test
    public void testSetUnit() {
        Unit testU = new Unit(UnitType.KNIGHT, aVillage, aMap.getTile(0, 1));
        aMap.getTile(0, 1).setUnit(testU);
        assertTrue(aMap.getTile(0, 1).getUnit() == testU);
    }
    @Test
    public void testGetTerrainType() {
      	assertTrue(aMap.getTile(0,  1).getTerrainType() == TerrainType.MEADOW);
    }
    @Test
    public void testSetTerrainType() {
       aMap.getTile(4, 4).setTerrainType(TerrainType.SEA);
       assertTrue(aMap.getTile(4, 4).getTerrainType() == TerrainType.SEA);
    }
    @Test
    public void testGetNeighbours() {
    	List<Tile> nbrs = aMap.getTile(1, 1).getNeighbours();
    	assertTrue(nbrs.contains(aMap.getTile(0, 0)));
    	assertTrue(nbrs.contains(aMap.getTile(0, 1)));
    	assertTrue(nbrs.contains(aMap.getTile(1, 0)));
    	assertTrue(nbrs.contains(aMap.getTile(1, 2)));
    	assertTrue(nbrs.contains(aMap.getTile(2, 1)));
    	assertTrue(nbrs.contains(aMap.getTile(2, 0)));
    }
    @Test
    public void testKillUnit() {
    	assertTrue(aMap.getTile(1, 1).getUnit() == u);
    	aMap.getTile(1, 1).killUnit();
    	assertTrue(aMap.getTile(1 ,1).getUnit() == null);
    }
    @Test
    public void testGetControllingPlayer() {
       assertTrue(aMap.getTile(0, 1).getControllingPlayer() == p1);
       assertTrue(aMap.getTile(5, 5).getControllingPlayer() == null);
    }
    @Test
    public void testHashCode() {
        int hash = aMap.getTile(3, 3).hashCode();
        // 3 * 10 + 3 = 33
        assertTrue(hash == 33);
        hash = aMap.getTile(0, 0).hashCode();
        assertTrue(hash == 0);
    }
    @Test
    public void testEquals() {
        assertTrue(aMap.getTile(3, 3).equals(aMap.getTile(3, 3)));
    }

}
