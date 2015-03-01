package newworldorder.client.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MapTest {
	private Map aMap;
	private Village aVillage;
	private Player p1;

	@Before
	public void setUp() {
		aMap = new Map(3, 3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				aMap.getTile(i, j).setTerrainType(TerrainType.MEADOW);
			}
		}
		aMap.getTile(1, 1).setTerrainType(TerrainType.TREE);
		aMap.getTile(2, 2).setStructure(StructureType.ROAD);
		List<Tile> aList = new ArrayList<Tile>();
		aList.add(aMap.getTile(1, 2));
		aList.add(aMap.getTile(2, 1));
		p1 = new Player("Yung", "Lean", 0, 0, null);
		aVillage = new Village(aMap.getTile(1, 2), p1, aList);
	}

	@Test
	public void testSetUpMap() {
		// TODO: implement after json parser works
		// fail();
	}

	@Test
	public void testGetWidth() {
		assertTrue(aMap.getWidth() == 3);
	}

	@Test
	public void testGetHeight() {
		assertTrue(aMap.getHeight() == 3);
	}

	@Test
	public void testGetTiles() {
		assertFalse(aMap.getTiles() == null);
	}

	@Test
	public void testGetTilesWithTerrain() {
		assertTrue(aMap.getTilesWithTerrain(TerrainType.MEADOW).size() == 8);
	}

	@Test
	public void testGetTilesWithStructure() {
		assertTrue(aMap.getTilesWithStructure(StructureType.ROAD).size() == 1);
	}

	@Test
	public void testGetVillages() {
		assertTrue(aMap.getVillages().contains(aVillage));
		assertTrue(aMap.getVillages().size() == 1);
	}

	@Test
	public void testReplaceTombstonesWithTrees() {
		aMap.getTile(2, 1).setStructure(StructureType.TOMBSTONE);
		aMap.replaceTombstonesWithTrees(p1);
		assertTrue(aMap.getTilesWithStructure(StructureType.TOMBSTONE).isEmpty());
	}

	@Test
	public void testGrowNewTrees() {
		int numTreesStart = aMap.getTilesWithTerrain(TerrainType.TREE).size();
		for (int i = 0; i < 100; i++) {
			aMap.growNewTrees();
		}
		int numTreesEnd = aMap.getTilesWithTerrain(TerrainType.TREE).size();
		assertTrue(numTreesEnd > numTreesStart);
	}
}
