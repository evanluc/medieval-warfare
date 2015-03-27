package newworldorder.client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import newworldorder.client.shared.StructureType;
import newworldorder.client.shared.TerrainType;

/**
 * Tile class is clean.
 */
class Tile extends Observable implements Serializable {
    
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
    transient private Set<Tile> neighbours;
    
    public Tile(int px, int py, Map myMap) {
        x = px;
        y = py;
        map = myMap;
        mapHeight = myMap.getHeight();
        mapWidth = myMap.getWidth();
        neighbours = new HashSet<Tile>(6);
    }
    
    public void cacheNeighbours() {
        final int yp = y + 1;
        final int ym = y - 1;
        final int xp = x + 1;
        final int xm = x - 1;
        final int xparity = x % 2;

        // Bottom
        if (ym >= 0) neighbours.add(map.getTile(x, ym));
        
        // Top
        if (yp < mapHeight) neighbours.add(map.getTile(x, yp));
        
        if (xp < mapWidth) { // Right
            if (xparity == 0) { // Even
            	neighbours.add(map.getTile(xp, y)); // Bottom-Right
            	if (yp < mapHeight) 
            		neighbours.add(map.getTile(xp, yp)); // Top-Right
            } else { // Odd
            	neighbours.add(map.getTile(xp, y)); // Top-Right
            	if (ym >= 0)
            		neighbours.add(map.getTile(xp, ym)); // Bottom-Right
            }
        }
        
        if (xm >= 0) { // Left     
            if (xparity == 0) { // Even
            	neighbours.add(map.getTile(xm, y)); // Bottom-Left
            	if (yp < mapHeight) 
            		neighbours.add(map.getTile(xm, yp)); // Top-Left
            } 
            else { // Odd
            	neighbours.add(map.getTile(xm, y)); // Top-Left
            	if (ym >= 0) 
            		neighbours.add(map.getTile(xm, ym)); // Bottom-Left
            }
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
    	occupyingStructure = structure;
    	super.setChanged();
    	super.notifyObservers();
    }

    public Village getVillage() {
        return villageOnTile;
    }
    
    public void setVillage(Village myVillage) {
    	villageOnTile = myVillage;
    	super.setChanged();
    	super.notifyObservers();
    }

    public Region getRegion() {
        return region;
    }
    
    public void setRegion(Region newRegion) {
    	region = newRegion;
    	super.setChanged();
    	super.notifyObservers();
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit newUnit) {
    	unit = newUnit;
    	super.setChanged();
    	super.notifyObservers();
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType type) {
    	terrainType = type;
    	super.setChanged();
    	super.notifyObservers();
    }

    public List<Tile> getNeighbours() {
        return new ArrayList<Tile>(neighbours);
    }

    public Player getControllingPlayer() {
        if (region != null) {
            return region.getControllingPlayer();
        }
        return null;
    }

    @Override
	public int hashCode() {
        return y * mapWidth + x;
    }
    
    @Override
	public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }
    
    private void readObject(ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException
    {
        inputStream.defaultReadObject();
        if (this.neighbours == null) {
        	neighbours = new HashSet<Tile>();
        }
    }
}
