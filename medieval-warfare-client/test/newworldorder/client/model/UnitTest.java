package newworldorder.client.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import newworldorder.client.shared.UnitType;

import org.junit.Test;
import org.junit.Before;

public class UnitTest
{
	private Unit u;
	private Map aMap;
	private Village aVillage;
	private Player p1, p2;
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
		u = new Unit(UnitType.PEASANT, aVillage, aMap.getTile(1, 1));
    }
	@Test
	public void testGetUnitType(){
		assertTrue(u.getUnitType() == UnitType.PEASANT);
	}
    @Test
    public void testUnitLevel()
    {
        assertTrue(Unit.unitLevel(u.getUnitType()) == 1);
    }
    @Test
    public void testUnitCost(){
    	assertTrue(Unit.unitCost(u.getUnitType()) == 10);
    }
    @Test
    public void testSetUnitCost(){
    	u.setUnitType(UnitType.KNIGHT);
    	UnitType ut = u.getUnitType();
    	assertTrue(ut == UnitType.KNIGHT);
    }
    
    @Test
    public void testGetCurrentAction(){
    	assertTrue(u.getCurrentAction() == ActionType.READYFORORDERS);
    }
    @Test
    public void testSetCurrentAction(){
    	u.setCurrentAction(ActionType.CLEARINGTOMBSTONE);
    	assertTrue(u.getCurrentAction() == ActionType.CLEARINGTOMBSTONE);
    }
    @Test
    public void testGetImmobileUntilRound(){
    	assertTrue(u.getImmobileUntilRound() == -1);
    }
    @Test
    public void testSetImmobileUntilRound(){
    	u.setImmobileUntilRound(10);
    	assertTrue(u.getImmobileUntilRound() == 10);
    }
    @Test
    public void testGetTile(){
    	assertTrue(u.getTile() == aMap.getTile(1, 1));
    }
    @Test
    public void testSetTile(){
    	u.setTile(aMap.getTile(0,1));
    	assertTrue(u.getTile() == aMap.getTile(0,1));
    }
    @Test
    public void testGetVillage(){
    	assertTrue(u.getVillage() == aVillage);
    }
    @Test
    public void testSetVillage(){
    	Set<Tile> newRegion = new HashSet<Tile>();
    	newRegion.add(aMap.getTile(5, 5));
    	Village testV = new Village(aMap.getTile(5,5), u.getControllingPlayer(), newRegion);
    	u.setVillage(testV);
    	assertTrue(u.getVillage() == testV);
    }
    @Test
    public void testGetControllingPlayer(){
    	assertTrue(u.getControllingPlayer() == p1);
    }
    @Test
    public void testGetUpkeep(){
    	assertTrue(u.getUpkeep() == 2);
    }
}
