package newworldorder.client.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;



/**
 * Map class clean and complete.
 */
public class Map {
    
    private static final double TREE_GROWTH_PROB = 0.5;
    private final int width;
    private final int height;
    private final int totalTiles;
    private final Hashtable<Integer, Tile> tiles;
    
    public Map(int pWidth, int pHeight) {
        width = pWidth;
        height = pHeight;
        totalTiles = pWidth * pHeight;
        tiles = new Hashtable<Integer,Tile>();
        for (int i = 0 ; i < width ; i++) {
        	for(int j = 0 ; j < height ; j++){
        		Tile t = new Tile(i,j,this);
                tiles.put(t.hashCode(), t);
        	}
        }
        for(Tile t : tiles.values()){
        	t.cacheNeighbours();
        }
    }
    
    public static Map setUpMap(List<Player> players, String jsonMap) {
        Map map = JsonParser.parseMap(jsonMap);
        return map;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
        Tile tile;
        for (int i = 0 ; i < totalTiles; i++) {
            tile = tiles.get(i);
            if (player == tile.getControllingPlayer()) {
                tile.setStructure(null);
                tile.setTerrainType(TerrainType.TREE);
            }
        }
    }

    /**
     * For every tile that contains a tree, there is a 50% probability that a 
     * new tree will be grown in an adjacent Grass or Meadow tile.
     */
    public void growNewTrees() {
        for (Tile t : getTilesWithTerrain(TerrainType.TREE)) {
            double prob = Math.random();
            if (prob > TREE_GROWTH_PROB) {
                for (Tile n : t.getNeighbours()) {
                    if (n.getTerrainType() == TerrainType.MEADOW || n.getTerrainType() == TerrainType.GRASS) {
                        n.setTerrainType(TerrainType.TREE);
                        break;
                    }
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        assert(x < width && x >= 0);
        assert(y < height && y >= 0);
        return tiles.get(y * width + x);
    }
}
