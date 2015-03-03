package newworldorder.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {
	private Map aMap;
	private Village village1, village2;
	private Player p1, p2;
	private GameEngine gameEngine;

	@Before
	public void setUp() {
		p1 = new Player("Yung", "Lean", 0, 0, null);
		p2 = new Player("2", "Chainz", 0, 0, null);
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(p1);
		playerList.add(p2);
		aMap = new Map(10, 10);
		Game aGame = new Game(playerList, aMap);
		gameEngine = new GameEngine(aGame);
		p1.setCurrentGame(aGame);
		p2.setCurrentGame(aGame);
		aGame.setTurnOf(p1);
		List<Tile> reg1 = new ArrayList<Tile>();
		reg1.add(aMap.getTile(0, 0));
		reg1.add(aMap.getTile(1, 0));
		reg1.add(aMap.getTile(0, 1));
		village1 = new Village(aMap.getTile(0, 0), p1, reg1);

		List<Tile> reg2 = new ArrayList<Tile>();
		reg2.add(aMap.getTile(1, 1));
		reg2.add(aMap.getTile(2, 1));
		reg2.add(aMap.getTile(1, 2));
		reg2.add(aMap.getTile(2, 2));
		village2 = new Village(aMap.getTile(2, 2), p2, reg2);
	}

	@Test
	public void testBuildRoad() {
		Unit aUnit = new Unit(UnitType.PEASANT, village1, aMap.getTile(1, 0));
		assertFalse(aMap.getTile(1, 0).getStructure() == StructureType.ROAD);
		gameEngine.buildRoad(aUnit);
		assertTrue(aMap.getTile(1, 0).getStructure() == StructureType.ROAD);

		Unit aUnit2 = new Unit(UnitType.KNIGHT, village1, aMap.getTile(0, 1));
		assertFalse(aMap.getTile(0, 1).getStructure() == StructureType.ROAD);
		gameEngine.buildRoad(aUnit2);
		assertFalse(aMap.getTile(0, 1).getStructure() == StructureType.ROAD);
	}

	@Test
	public void testTakeoverTile() {
		// TODO fail("Not yet implemented");
	}

	@Test
	public void testNewGame() {
		// TODO fail("Not yet implemented");
	}

	@Test
	public void testBeginTurn() {
		// TODO fail("Not yet implemented");
	}

	@Test
	public void testUpgradeUnit() {
		Unit aUnit = new Unit(UnitType.PEASANT, village1, aMap.getTile(1, 0));
		assertEquals(aUnit.getVillage().getWood(), 0);
		gameEngine.upgradeUnit(aUnit, UnitType.KNIGHT);
		assertEquals(aUnit.getUnitType(), UnitType.PEASANT);
		aUnit.getVillage().transactGold(20);
		gameEngine.upgradeUnit(aUnit, UnitType.KNIGHT);
		assertEquals(aUnit.getUnitType(), UnitType.KNIGHT);
	}

	@Test
	public void testUpgradeVillage() {
		// TODO fail("Not yet implemented");
	}

	@Test
	public void testBuildTower() {
		// TODO fail("Not yet implemented");
	}

	@Test
	public void testGetGameState() {
		// TODO fail("Not yet implemented");
	}

	@Test
	public void testSetGameState() {
		// TODO fail("Not yet implemented");
	}

	@Test
	public void testMoveUnit_InvalidMove() {
		//Case where enemy unit is stronger
		Unit ally = new Unit(UnitType.INFANTRY, village1, aMap.getTile(1, 0));
		Unit enemy = new Unit(UnitType.KNIGHT, village2, aMap.getTile(1, 1));
		gameEngine.moveUnit(ally, aMap.getTile(1, 1));
		assertEquals(aMap.getTile(1,0).getUnit(), ally);
		assertEquals(aMap.getTile(1,1).getUnit(), enemy);
		//Case where dest tile is not adjacent.
		gameEngine.moveUnit(ally, aMap.getTile(2, 2));
		assertEquals(aMap.getTile(1,0).getUnit(), ally);
		//Case where enemy unit is weaker, but on a watchtower that is stronger than attacker
		ally.setUnitType(UnitType.INFANTRY);
		enemy.setUnitType(UnitType.PEASANT);
		enemy.getTile().setStructure(StructureType.WATCHTOWER);
		gameEngine.moveUnit(ally, enemy.getTile());
		assertEquals(aMap.getTile(1, 0).getUnit(), ally);
		//Case where there is just a watchtower
		enemy.kill();
		aMap.getTile(1, 1).setStructure(StructureType.WATCHTOWER);
		gameEngine.moveUnit(ally, aMap.getTile(1, 1));
		assertEquals(aMap.getTile(1, 0).getUnit(), ally);
		//Case where weak unit tries to take village
		aMap.getTile(1, 1).setRegion(aMap.getTile(1,0).getRegion());
		aMap.getTile(1, 1).setTerrainType(TerrainType.GRASS);
		gameEngine.moveUnit(ally, aMap.getTile(1,1));
		assertEquals(aMap.getTile(1, 0).getRegion(), aMap.getTile(1, 1).getRegion());
		assertEquals(aMap.getTile(1, 1).getUnit(), ally);
		gameEngine.moveUnit(ally, aMap.getTile(2,2));
		assertEquals(aMap.getTile(1,1).getUnit(), ally);
	}

	@Test
	public void testMoveUnit_FreeMove() {
		Unit ally = new Unit(UnitType.INFANTRY, village1, aMap.getTile(1, 0));
		Unit enemy = new Unit(UnitType.SOLDIER, village2, aMap.getTile(1, 1));
		aMap.getTile(0, 1).setTerrainType(TerrainType.GRASS);
		//Move onto grass in own territory
		gameEngine.moveUnit(ally, aMap.getTile(0,1));
		assertEquals(aMap.getTile(0,1).getUnit(), ally);
		//Move onto meadow as weak unit
		aMap.getTile(1, 0).setTerrainType(TerrainType.MEADOW);
		gameEngine.moveUnit(ally, aMap.getTile(1, 0));
		assertEquals(aMap.getTile(1, 0).getTerrainType(), TerrainType.MEADOW);
		//Kill enemy unit
		ally.setUnitType(UnitType.KNIGHT);
		gameEngine.moveUnit(ally, aMap.getTile(1, 1));
		assertEquals(aMap.getTile(1, 1).getUnit(), ally);
		assertEquals(aMap.getTile(1, 1).getRegion(), aMap.getTile(1, 0).getRegion());
		//Take over village
		gameEngine.moveUnit(ally, aMap.getTile(2,2));
		assertEquals(aMap.getTile(2,2).getUnit(), ally);
		//Walk into neutral territory
		aMap.getTile(0, 2).setTerrainType(TerrainType.GRASS);
		aMap.getTile(1,1).setStructure(null);
		aMap.getTile(1,1).setTerrainType(TerrainType.GRASS);
		gameEngine.moveUnit(ally, aMap.getTile(1, 1));
		gameEngine.moveUnit(ally, aMap.getTile(0, 2));
		assertEquals(aMap.getTile(0, 2).getUnit(), ally);
	}



	@Test
	public void testMoveUnit_NeutralTakeOver() {
		// fail("Not yet implemented");
	}

	@Test
	public void testMoveUnit_EnemyTakeover() {
		// fail("Not yet implemented");
	}

	@Test
	public void testMoveUnit_CombineUnits() {
		// fail("Not yet implemented");
	}

	@Test
	public void testMoveUnit_TrampleMeadow() {
		// fail("Not yet implemented");
	}
}
