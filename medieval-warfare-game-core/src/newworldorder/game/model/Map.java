package newworldorder.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Map class clean and complete.
 * This will be deleted soon, please start using corresponding definition in newworldorder.client.model or newworldorder.client.shared
 */
@Deprecated
public class Map implements Serializable {

	private static final long serialVersionUID = 3445594680733206161L;
	private static final int INIT_VILLAGE_REGION_SIZE = 3;
	private static final int INIT_VILLAGE_GOLD_AMT = 7;
	private static final double INIT_VILLAGE_PERC_OF_AVAIL_TILE_FACTOR = 0.05;
	private static final double TREE_GROWTH_PROB = 0.05;
	private final int width;
	private final int height;
	private final int totalTiles;
	private final Hashtable<Integer, Tile> tiles;

	public Map(int pWidth, int pHeight) {
		width = pWidth;
		height = pHeight;
		totalTiles = pWidth * pHeight;
		tiles = new Hashtable<Integer, Tile>();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile t = new Tile(x, y, this);
				tiles.put(t.hashCode(), t);
			}
		}
		for (Tile t : tiles.values()) {
			t.cacheNeighbours();
		}
	}

	/**
	 * setUpMap will create an equal number of initial villages for each player
	 * on the given map instance. The number of villages it creates is determined by the
	 * INIT_VILLAGE_PERC_OF_AVAIL_TILE_FACTOR constant.
	 * @param players The players to initialize the map with.
	 * @param map The map to set up.
	 */
	public static void setUpMap(List<Player> players, Map map) {

		int numPlayers = players.size();
		List<Tile> remainingGrassTiles = map
				.getTilesWithTerrain(TerrainType.GRASS);

		int numVillagesPerPlayer = 1 + (int) Math
				.floor(INIT_VILLAGE_PERC_OF_AVAIL_TILE_FACTOR
						* (remainingGrassTiles.size() / numPlayers));

		for (Player p : players) {
			while (p.getVillages().size() < numVillagesPerPlayer) {
				// pick a random grass tile
				int randTileIndex = (int) Math.round(Math.random()
						* (remainingGrassTiles.size() - 1));
				Tile randTile = remainingGrassTiles.get(randTileIndex);
				List<Tile> randTileNeighbours = randTile.getNeighbours();

				// determine if tile borders a tile already controlled by
				// player,
				// don't want to create a big region at start
				boolean canPlaceRegion = true;
				for (Tile n : randTileNeighbours) {
					if (n.getControllingPlayer() == p) {
						canPlaceRegion = false;
					}
				}

				// if the tile passed the above check,
				// determine whether a region of size 3 can be made
				if (canPlaceRegion) {
					Set<Tile> newRegion = new HashSet<Tile>();
					newRegion.add(randTile);
					Tile curTile = randTileNeighbours.get(0);

					while (newRegion.size() < INIT_VILLAGE_REGION_SIZE) {
						// the tile must not be sea or already controlled by another player
						if (curTile.getTerrainType() != TerrainType.SEA && curTile.getRegion() == null) {
							for (Tile n : curTile.getNeighbours()) {
								if (n.getControllingPlayer() == p) {
									canPlaceRegion = false;
									break;
								}
							}
						} else {
							canPlaceRegion = false;
						}

						// we are done checking curTile, so remove it from the
						// list to consider
						randTileNeighbours.remove(curTile);

						if (canPlaceRegion) {
							// if curTile did not border the player, then add it
							// to the newRegion
							newRegion.add(curTile);
							if (newRegion.size() < INIT_VILLAGE_REGION_SIZE && randTileNeighbours.size() > 0) {
								curTile = randTileNeighbours.get(0);
							} else {
								break;
							}
						} else {
							// otherwise, check if there are more tiles that can
							// be considered
							if (randTileNeighbours.size() > 0) {
								curTile = randTileNeighbours.get(0);
								canPlaceRegion = true;
							} else {
								break;
							}
						}
					}

					// if the tile passed the above check, create
					// the region and add the tiles
					if (newRegion.size() >= INIT_VILLAGE_REGION_SIZE) {
						Village v = new Village(randTile, p, newRegion);
						v.transactGold(INIT_VILLAGE_GOLD_AMT);
						remainingGrassTiles.removeAll(newRegion);
					}
				}
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTotalTiles() {
		return totalTiles;
	}

	public Iterable<Tile> getTiles() {
		return tiles.values();
	}

	public List<Tile> getTilesWithTerrain(TerrainType terrainType) {
		List<Tile> ret = new ArrayList<Tile>();
		for (Tile t : tiles.values()) {
			if (t.getTerrainType() == terrainType) {
				ret.add(t);
			}
		}
		return ret;
	}

	public List<Tile> getTilesWithStructure(StructureType structureType) {
		List<Tile> ret = new ArrayList<Tile>();
		for (Tile t : tiles.values()) {
			if (t.getStructure() == structureType) {
				ret.add(t);
			}
		}
		return ret;
	}

	public List<Village> getVillages() {
		List<Village> ret = new ArrayList<Village>();
		for (Tile t : tiles.values()) {
			if (t.getVillage() != null) {
				ret.add(t.getVillage());
			}
		}
		return ret;
	}

	public void replaceTombstonesWithTrees(Player player) {
		for (Tile t : tiles.values()) {
			if (t.getStructure() == StructureType.TOMBSTONE
					&& player == t.getControllingPlayer()) {
				t.setStructure(null);
				t.setTerrainType(TerrainType.TREE);
			}
		}
	}

	/**
	 * For every tile that contains a tree, there is a 50% probability that a
	 * new tree will be grown in an adjacent Grass or Meadow tile.
	 */
	public List<Integer> growNewTrees() {
		List<Integer> ret = new ArrayList<Integer>();
		for (Tile t : getTilesWithTerrain(TerrainType.TREE)) {
			double prob = Math.random();
			if (prob < TREE_GROWTH_PROB) {
				for (Tile n : t.getNeighbours()) {
					if ( (n.getTerrainType() == TerrainType.MEADOW
							|| n.getTerrainType() == TerrainType.GRASS)
							&& n.getUnit() == null
							&& n.getStructure() == null
							&& n.getVillage() == null){
						n.setTerrainType(TerrainType.TREE);
						ret.add(n.hashCode());
						break;
					}
				}
			}
		}
		return ret;
	}

	public Tile getTile(int x, int y) {
		assert (x < width && x >= 0);
		assert (y < height && y >= 0);
		return tiles.get(y * width + x);
	}
	
	public Tile getTile(int hashCode) {
		assert (hashCode < width * height && hashCode >= 0);
		return tiles.get(hashCode);
	}

	public void placeTreesAt(List<Integer> l) {
		for (int i : l) {
			Tile t = tiles.get(i);
			if (t != null) {
				t.setTerrainType(TerrainType.TREE);
			}
		}
	}
}
