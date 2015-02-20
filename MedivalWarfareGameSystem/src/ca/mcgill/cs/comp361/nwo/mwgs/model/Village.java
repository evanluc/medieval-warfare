package ca.mcgill.cs.comp361.nwo.mwgs.model;

import java.util.List;



/**
 * Village class is clean and complete.
 */
public class Village {
    
    private VillageType villageType;
    private int gold;
    private int wood;
    private final Player controlledBy;
    private final Tile tile;
    private final Region controlledRegion;
    private List<Unit> supportedUnits;
    
    /**
     * Creating a Village with the list of 
     * @param pTile The tile the Village is on. This object must be in the pControlledTiles list of tiles.
     * @param pPlayer The Player who controls this village.
     * @param pControlledTiles The Tiles this village controls.
     */
    public Village(Tile pTile, Player pPlayer, List<Tile> pControlledTiles) {
        assert(pControlledTiles.contains(pTile));
        tile = pTile;
        controlledBy = pPlayer;
        controlledRegion = new Region(pControlledTiles, pPlayer);
        controlledRegion.setVillage(this);
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
        return supportedUnits;
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
        for (Unit u : supportedUnits) {
            u.kill();
        }
    }
}
