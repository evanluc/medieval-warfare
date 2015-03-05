package newworldorder.client.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import newworldorder.client.model.Game;
import newworldorder.client.model.Map;
import newworldorder.client.model.Player;
import newworldorder.client.model.Region;
import newworldorder.client.model.StructureType;
import newworldorder.client.model.TerrainType;
import newworldorder.client.model.Tile;
import newworldorder.client.model.Unit;
import newworldorder.client.model.UnitType;
import newworldorder.client.model.Village;

import org.junit.Test;
import org.junit.Before;

public class VillageTest {
	private Unit u;
	private Map aMap;
	private Village aVillage;
	private Player p1, p2;
	private List<Tile> aReg;
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
		aReg = new ArrayList<Tile>();
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
    public void testGetVillageType() {
		assertTrue(aVillage.getVillageType() == VillageType.HOVEL);
    }
	@Test
    public void testSetVillageType() {
		aVillage.setVillageType(VillageType.TOWN);
		assertTrue(aVillage.getVillageType() == VillageType.TOWN);
    }
	@Test
    public void testGetGold() {
		assertTrue(aVillage.getGold() == 0);
    }
	@Test
    public void testGetWood() {
		assertTrue(aVillage.getWood() == 0);
    }
    @Test
    public void testGetControlledBy() {
    	assertTrue(aVillage.getControlledBy() == p1);
    }
    @Test
    public void testGetTile(){
    	assertTrue(aVillage.getTile() == aMap.getTile(0, 0));
    }
    @Test
    public void testGetSupportedUnits() {
    	List<Unit> units = aVillage.getSupportedUnits();
    	assertTrue(units.contains(u));
    }
    @Test
    public void testTransactGold() {
    	aVillage.transactGold(10);
    	assertTrue(aVillage.getGold() == 10);
    }
    @Test
    public void testTransactWood() {
    	aVillage.transactWood(10);
    	assertTrue(aVillage.getWood() == 10);
    }
    @Test
    public void testAddUnit() {
    	aVillage.removeUnit(u);
    	aVillage.addUnit(u);
    	assertTrue(aVillage.getSupportedUnits().contains(u));
    }
    @Test
    public void testRemoveUnit() {
    	aVillage.removeUnit(u);
    	assertFalse(aVillage.getSupportedUnits().contains(u));
    }
    @Test
    public void testGetTotalUpkeep() {
    	assertTrue(aVillage.getTotalUpkeep() == u.getUpkeep());
    }
    @Test
    public void testGetTotalIncome() {
    	assertTrue(aVillage.getTotalIncome() == 2);
    }
    @Test
    public void testkill() {
    	aVillage.kill();
    	assertFalse(p1.getVillages().contains(aVillage));
    }
    @Test
    public void testKillUnits() {
    	aVillage.killUnits();
    	assertTrue(aVillage.getSupportedUnits().isEmpty());
    }
}
