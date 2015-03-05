package newworldorder.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Village class is clean and complete.
 */
public class Village implements Serializable {
    
	private static final long serialVersionUID = -3748359571009668032L;
	private VillageType villageType;
    private int gold;
    private int wood;
    private final Player controlledBy;
    private final Tile tile;
    private final Region controlledRegion;
    private Set<Unit> supportedUnits;
    
    /**
     * Creating a Village with the list of 
     * @param pTile The tile the Village is on. This object must be in the pControlledTiles list of tiles.
     * @param pPlayer The Player who controls this village.
     * @param pControlledTiles The Tiles this village controls.
     */
    public Village(Tile pTile, Player pPlayer, List<Tile> pControlledTiles) {
        assert(pControlledTiles.contains(pTile));
        tile = pTile;
        tile.setVillage(this);
        controlledBy = pPlayer;
        controlledRegion = new Region(pControlledTiles, pPlayer);
        controlledRegion.setVillage(this);
        supportedUnits = new HashSet<Unit>();
        villageType = VillageType.HOVEL;
        gold = 0;
        wood = 0;
        controlledBy.addVillage(this);
    }
    
    public static int villageLevel(VillageType pVillageType) {
        if (pVillageType == VillageType.HOVEL) {
            return 0;
        } else if (pVillageType == VillageType.TOWN) {
            return 1;
        } else if (pVillageType == VillageType.FORT) {
            return 2;
        }
        return -1;
    }

    public static int villageCost(VillageType pVillageType) {
        if (pVillageType == VillageType.HOVEL) {
            return 0;
        } else if (pVillageType == VillageType.TOWN) {
            return 8;
        } else if (pVillageType == VillageType.FORT) {
            return 16;
        }
        return -1;
    }
    
    public VillageType getVillageType() {
        return villageType;
    }

    public void setVillageType(VillageType pVillageType) {
        villageType = pVillageType;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }
    
    public Player getControlledBy() {
        return controlledBy;
    }
    
    public Tile getTile()
    {
        return tile;
    }

    public Region getRegion() {
        return controlledRegion;
    }

    public List<Unit> getSupportedUnits() {
        return new ArrayList<Unit>(supportedUnits);
    }
    
    public void transactGold(int g) {
        gold = gold + g;
    }

    public void transactWood(int w) {
        wood = wood + w;
    }
    
    public void addUnit(Unit u) {
        supportedUnits.add(u);
    }

    public void removeUnit(Unit u) {
        supportedUnits.remove(u);
    }

    public int getTotalUpkeep() {
        int total = 0;
        for (Unit u : supportedUnits) {
            total += u.getUpkeep();
        }
        return total;
    }

    public int getTotalIncome() {
        int grass;
        int meadow;
        int total = 0;
        grass = controlledRegion.getTileCount(TerrainType.GRASS);
        meadow = controlledRegion.getTileCount(TerrainType.MEADOW);
        total = grass + 2 * meadow;
        return total;
    }

    public void kill() {
        killUnits();
        this.controlledBy.removeVillage(this);
    }
    
    public void killUnits() {
    	List<Unit> aList = new ArrayList<Unit>(supportedUnits);
    	for(Unit u : aList){
    		u.kill();
    	}
    }
}
