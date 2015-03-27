package newworldorder.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import newworldorder.client.shared.TerrainType;

/**
 * Region class is clean and complete.
 */
class Region implements Serializable {
    
	private static final long serialVersionUID = 5056315454979573917L;
	private Set<Tile> tiles;
    private Village controllingVillage = null;
    private final Player controllingPlayer;
    
    public Region(Set<Tile> pTiles, Player pPlayer) {
        tiles = new HashSet<Tile>(pTiles);
        controllingPlayer = pPlayer;
        for(Tile t : tiles){
        	t.setRegion(this);
        }
    }

    /**
     * This does not check if t disconnects the other tiles in the region.
     * @param t The tile to remove.
     */
    public void removeTile(Tile t) {
        if (tiles.remove(t)) t.setRegion(null);;
    }

    /**
     * This does not check if t is connected to the other tiles in the region.
     * @param t The tile to add.
     */
    public void addTile(Tile t) {
        if (tiles.add(t)) t.setRegion(this);
    }

    public List<Tile> getTiles() {
        return new ArrayList<Tile>(tiles);
    }

    public Village getVillage() {
        return controllingVillage;
    }

    public void setVillage(Village pVillage) {
        controllingVillage = pVillage;
    }

    public Player getControllingPlayer() {
        return controllingPlayer;
    }

    public int getTileCount(TerrainType pTerrainType) {
        int ret = 0;
        for (Tile t : tiles) {
            if (pTerrainType == t.getTerrainType()) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * This will do nothing if the region still has a controlling village.
     */
    public void createVillage() {
        if (controllingVillage == null) {
            // Randomly select a tile in the region that is a grass terrain.
            // Place a new village on that tile. If no such tile exists, pick one
        	// tree tile and replace it with a grass tile.
            
            List<Tile> temp = new ArrayList<Tile>(tiles);
            int tileIndex;
            Tile tile;
            
			do {
                // Right now this will infinite loop if, say, we have a 3-region of just trees
                tileIndex = (int)Math.floor(Math.random() * (temp.size() - 1));
                tile = temp.get(tileIndex);
            } while (tile.getTerrainType() == TerrainType.TREE && temp.size() > 0);
			
            Village village = new Village(tile, controllingPlayer, this);
            tile.setVillage(village);
            tile.setTerrainType(TerrainType.GRASS);
            
            controllingVillage = village;
        }
    }
    
    public void createVillage(Tile t) {
    	if (controllingVillage == null) {
    		Village village = new Village(t, controllingPlayer, this);
    		t.setVillage(village);
    		t.setTerrainType(TerrainType.GRASS);
    	}
    }
}