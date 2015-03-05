package newworldorder.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Region class is clean and complete.
 */
public class Region implements Serializable {
    
	private static final long serialVersionUID = 5056315454979573917L;
	private List<Tile> tiles;
    private Village controllingVillage = null;
    private final Player controllingPlayer;
    
    public Region(List<Tile> pTiles, Player pPlayer) {
        tiles = new ArrayList<Tile>(pTiles);
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
        return tiles;
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
        /* TODO Handle a region of tiles that are all unsuitable for building a village */
        if (controllingVillage == null) {
            // Randomly select a tile in the region that is a grass terrain.
            // Place a new village on that tile.
            int tileIndex = (int)Math.floor(Math.random() * (tiles.size() - 1));
            while (tiles.get(tileIndex).getTerrainType() == TerrainType.TREE) {
                // Right now this will infinite loop if, say, we have a 3-region of just trees
                tileIndex = (int)Math.floor(Math.random() * (tiles.size() - 1));
            }
            Village village = new Village(tiles.get(tileIndex), controllingPlayer, tiles);
            tiles.get(tileIndex).setVillage(village);
            controllingVillage = village;
        }
    }
}