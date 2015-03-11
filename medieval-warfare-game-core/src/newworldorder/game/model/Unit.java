package newworldorder.game.model;

import java.io.Serializable;

/**
 * Unit class clean and complete.
 */
public class Unit implements Serializable {

	private static final long serialVersionUID = -2187574739448732604L;
	private UnitType unitType;
    private ActionType currentAction;
    private int immobileUntilRound;
    private Tile tile;
    private Village myVillage;
    private final Player controllingPlayer;
    private int upkeep;
    
    public Unit(UnitType pUnitType, Village pVillage, Tile pTile) {

        myVillage = pVillage;
        tile = pTile;
        this.setUnitType(pUnitType);
        immobileUntilRound = -1;
        currentAction = ActionType.READYFORORDERS;
        controllingPlayer = myVillage.getControlledBy();
        myVillage.addUnit(this);
        tile.setUnit(this);
    }
    
    public static int unitLevel(UnitType u) {
        if (u == UnitType.PEASANT) {
            return 1;
        } else if (u == UnitType.INFANTRY) {
            return 2;
        } else if (u == UnitType.SOLDIER) {
            return 3;
        } else if (u == UnitType.KNIGHT) {
            return 4;
        }
        return -1;
    }

    public static int unitCost(UnitType u) {
        int level;
        level = Unit.unitLevel(u);
        level = level * 10;
        return level;
    }

    public UnitType getUnitType() {
        return unitType;
    }
    
    public void setUnitType(UnitType pUnitType) {
        unitType = pUnitType;
        int level, cost;
        level = Unit.unitLevel(unitType);
        cost = 2;
        for (int i = 1; i < level; i++) {
            cost = cost * 3;
        }
        upkeep = cost;
        tile.setUnit(this);
    }

    public ActionType getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(ActionType pCurrentAction) {
        currentAction = pCurrentAction;
    }
    
    public int getImmobileUntilRound()
    {
        return immobileUntilRound;
    }

    public void setImmobileUntilRound(int pImmobileUntilRound) {
        immobileUntilRound = pImmobileUntilRound;
    }

    public Tile getTile() {
        return tile;
    }
    
    public void setTile(Tile pTile) {
        tile = pTile;
    }

    public Village getVillage() {
        return myVillage;
    }
    
    public void setVillage(Village pVillage) {
        myVillage = pVillage; 
    }
    
    public Player getControllingPlayer()
    {
        return controllingPlayer;
    }

    public int getUpkeep() {
        return upkeep;
    }
}
