package newworldorder.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Tile class is clean.
 */
public class Tile extends Observable implements Serializable {
    
	private static final long serialVersionUID = -1574156206965797597L;
	private TerrainType terrainType;
    private final int x;
    private final int y;
    private final int mapHeight;
    private final int mapWidth;
    private Region region;
    private Unit unit;
    private Village villageOnTile;
    private StructureType occupyingStructure;
    private final Map map;
    private final List<Tile> neighbours;
    
    public Tile(int px, int py, Map myMap) {
        x = px;
        y = py;
        map = myMap;
        mapHeight = myMap.getHeight();
        mapWidth = myMap.getWidth();
        neighbours = new ArrayList<Tile>();
    }
    
    public void cacheNeighbours() {
        final int yp = y+1;
        final int ym = y-1;
        final int xp = x+1;
        final int xm = x-1;
        final int mod2 = x % 2;

        // North
        if (ym >= 0) neighbours.add(map.getTile(x, ym));
        // South
        if (yp < mapHeight) neighbours.add(map.getTile(x, yp));
        // East
        if (xp < mapWidth) {
            // N-E
            if (mod2 == 0 && ym >= 0) neighbours.add(map.getTile(xp, ym));
            else if (mod2 == 1) neighbours.add(map.getTile(xp, y));
            // S-E
            if (mod2 == 0) neighbours.add(map.getTile(xp, y));
            else if (mod2 == 1 && yp < mapHeight) neighbours.add(map.getTile(xp, yp));
        }
        // West
        if (xm >= 0) {
            // N-W
            if (mod2 == 0 && ym >= 0) neighbours.add(map.getTile(xm, ym));
            else if (mod2 == 1) neighbours.add(map.getTile(xm, y));
            // S-W
            if (mod2 == 0) neighbours.add(map.getTile(xm, y));
            else if (mod2 == 1 && yp < mapHeight) neighbours.add(map.getTile(xm, yp));
        }
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public StructureType getStructure() {
        return occupyingStructure;
    }
    
    public void setStructure(StructureType structure) {
    	super.notifyObservers();
    	occupyingStructure = structure;
    }

    public Village getVillage() {
        return villageOnTile;
    }
    
    public void setVillage(Village myVillage) {
    	super.notifyObservers();
    	villageOnTile = myVillage;
    }

    public Region getRegion() {
        return region;
    }
    
    public void setRegion(Region newRegion) {
    	super.notifyObservers();
    	region = newRegion;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit newUnit) {
    	super.notifyObservers();
    	unit = newUnit;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType type) {
    	super.notifyObservers();
    	terrainType = type;
    }

    public List<Tile> getNeighbours() {
        return new ArrayList<Tile>(neighbours);
    }

    public void killUnit() {
    	super.notifyObservers();
        if (unit != null)
        {
            unit.kill();
            unit = null;
        }
    }

    public Player getControllingPlayer() {
        if (region != null) {
            return region.getControllingPlayer();
        }
        return null;
    }

    public int hashCode() {
        return y * mapWidth + x;
    }
    
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }
}
