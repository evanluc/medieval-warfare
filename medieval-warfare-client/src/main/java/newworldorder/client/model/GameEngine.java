package newworldorder.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import newworldorder.client.shared.ColourType;
import newworldorder.client.shared.StructureType;
import newworldorder.client.shared.TerrainType;
import newworldorder.client.shared.UIActionType;
import newworldorder.client.shared.UITileDescriptor;
import newworldorder.client.shared.UIVillageDescriptor;
import newworldorder.client.shared.UnitType;
import newworldorder.client.shared.VillageType;

/**
 * GameEngine class, clean and complete.
 */
public class GameEngine implements Observer {

	private ModelController controller;
	private Game gameState;
	private Set<Tile> updatedTiles;
	private String localPlayerName;

	GameEngine() {
		this.gameState = null;
		this.updatedTiles = new HashSet<Tile>();
	}
	
	public void buildRoad(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Unit u = t.getUnit();
		if (u != null) {
			if (u.getUnitType() == UnitType.PEASANT && t.getStructure() != StructureType.ROAD) {
				u.setCurrentAction(ActionType.BUILDINGROAD);
				u.setImmobileUntilRound(gameState.getRoundCount() + 1);
				t.setStructure(StructureType.ROAD);
			}
		}
	}
	
	public void buildTower(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Village village = t.getRegion().getVillage();
		if (village != null) {
			if (village.getWood() >= 5) {
				t.setStructure(StructureType.WATCHTOWER);
				village.transactWood(-5);
			}
		}
	}
	
	public void buildUnitPeasant(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Region r = t.getRegion();
		if (r != null) {
			Village v = r.getVillage();
			if (v != null)
				buildUnit(v, t, UnitType.PEASANT);
		}
	}
	
	public void buildUnitInfantry(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Region r = t.getRegion();
		if (r != null) {
			Village v = r.getVillage();
			if (v != null)
				buildUnit(v, t, UnitType.INFANTRY);
		}
	}
	
	public void buildUnitSoldier(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Region r = t.getRegion();
		if (r != null) {
			Village v = r.getVillage();
			if (v != null)
				buildUnit(v, t, UnitType.SOLDIER);
		}
	}
	
	public void buildUnitKnight(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Region r = t.getRegion();
		if (r != null) {
			Village v = r.getVillage();
			if (v != null)
				buildUnit(v, t, UnitType.KNIGHT);
		}
	}
	
	void buildUnit(Village v, Tile t, UnitType type) {
		log("Entering buildUnit");
		Region r = v.getRegion();
		// Check pre-requisites for building a unit
		if (t.getUnit() == null && v.getGold() >= Unit.unitCost(type) && r.getTiles().contains(t)
				&& r.getControllingPlayer() == gameState.getCurrentTurnPlayer()) {
			if (v.getVillageType() == VillageType.HOVEL) {
				// Checks if village is allowed to create strong units
				if (type == UnitType.SOLDIER || type == UnitType.KNIGHT) {
					log("Unit not created");
					return;
				}
			}
			else if (v.getVillageType() == VillageType.TOWN) {
				if (type == UnitType.KNIGHT) {
					log("Unit not created");
					return;
				}
			}
			log("Unit successfully created");
			v.transactGold(-1 * Unit.unitCost(type));
			new Unit(type, v, t);
		}
	}
	
	public void cultivateMeadow(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Unit u = t.getUnit();
		if (u != null) {
			if (u.getUnitType() == UnitType.PEASANT && t.getTerrainType() == TerrainType.GRASS) {
				u.setCurrentAction(ActionType.STARTCULTIVATING);
				u.setImmobileUntilRound(gameState.getRoundCount() + 2);
				// The meadow terrain is built in phaseBuild of beginTurn because it
				// should not appear until the second turn that the unit is cultivating the
				// meadow.
			}
		}
	}
	
	public void upgradeUnitInfantry(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Unit u = t.getUnit();
		if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.INFANTRY))
			upgradeUnit(u, UnitType.INFANTRY);
	}
	
	public void upgradeUnitSoldier(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Unit u = t.getUnit();
		if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.SOLDIER))
			upgradeUnit(u, UnitType.SOLDIER);
	}
	
	public void upgradeUnitKnight(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Unit u = t.getUnit();
		if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.KNIGHT))
			upgradeUnit(u, UnitType.KNIGHT);
	}

	void upgradeUnit(Unit u, UnitType newLevel) {
		Village v = u.getVillage();
		if (v.getVillageType() == VillageType.HOVEL) {
			if (newLevel == UnitType.SOLDIER || newLevel == UnitType.KNIGHT) {
				return;
			}
		}
		if (v.getVillageType() == VillageType.TOWN) {
			if (newLevel == UnitType.KNIGHT) {
				return;
			}
		}
		int cost = Unit.unitCost(newLevel) - Unit.unitCost(u.getUnitType());
		if (v.getGold() >= cost) {
			u.setUnitType(newLevel);
			v.transactGold(-1 * cost);
		}
	}
	
	public void upgradeVillageTown(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Village v = t.getVillage();
		if (v != null && Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.TOWN))
			upgradeVillage(v, VillageType.TOWN);
	}
	
	public void upgradeVillageFort(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Village v = t.getVillage();
		if (v != null && Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.FORT))
			upgradeVillage(v, VillageType.FORT);
	}
	
	void upgradeVillage(Village v, VillageType newLevel) {
		int cost = Village.villageCost(newLevel) - Village.villageCost(v.getVillageType());
		if (v.getWood() >= cost) {
			v.setVillageType(newLevel);
			v.transactWood(-1 * cost);
		}
	}
	
	public void moveUnit(int x1, int y1, int x2, int y2) {
		Tile t1 = gameState.getMap().getTile(x1, y1);
		Tile t2 = gameState.getMap().getTile(x2, y2);
		Unit u = t1.getUnit();
		if (u != null)
			moveUnit(u, t2);
	}
	
	void moveUnit(Unit u, Tile dest) {
		MoveType moveType = getMoveType(u, dest);

		if (moveType == MoveType.INVALIDMOVE) {
			// Do nothing;
		}
		else if (moveType == MoveType.COMBINEUNITS) {
			combineUnits(dest.getUnit(), u);
		}
		else {
			// If there is an enemy unit, we kill it because we checked whether
			// it was defeatable in getMoveType
			if (dest.getControllingPlayer() != u.getControllingPlayer()) {
				if (dest.getUnit() != null) {
					killUnit(dest.getUnit());
				}
				if (dest.getStructure() == StructureType.WATCHTOWER) {
					dest.setTerrainType(null);
				}

			}

			// Move the unit
			Tile origin = u.getTile();
			origin.setUnit(null);
			dest.setUnit(u);
			u.setTile(dest);

			// Non-combat type actions
			if (moveType == MoveType.TRAMPLEMEADOW) {
				trampleMeadow(u);
			}
			else if (moveType == MoveType.CLEARTOMB) {
				clearTombstone(u);
			}
			else if (moveType == MoveType.GATHERWOOD) {
				gatherWood(u);
			}

			// Exiting own territory type actions
			if (dest.getControllingPlayer() == null) {
				u.getVillage().getRegion().addTile(dest);
				combineRegions(dest);
				u.setImmobileUntilRound(gameState.getRoundCount() + 1);
				u.setCurrentAction(ActionType.MOVED);
			}
			else if (dest.getControllingPlayer() != u.getControllingPlayer()) {
				takeoverTile(dest);
				u.setImmobileUntilRound(gameState.getRoundCount() + 1);
				u.setCurrentAction(ActionType.MOVED);
			}
		}
	}
		private MoveType getMoveType(Unit u, Tile dest) {
		Tile origin = u.getTile();
		List<Tile> adjacent = origin.getNeighbours();

		if (u.getImmobileUntilRound() <= gameState.getRoundCount() && adjacent.contains(dest)
				&& u.getControllingPlayer() == gameState.getCurrentTurnPlayer()) {
			TerrainType landOnDest = dest.getTerrainType();
			StructureType structureOnDest = dest.getStructure();
			Unit unitOnDest = dest.getUnit();
			Village villageOnDest = dest.getVillage();
			Region regionOnDest = dest.getRegion();

			if (regionOnDest != null) {
				if (regionOnDest.getControllingPlayer() != u.getControllingPlayer() && u.getUnitType() == UnitType.PEASANT) {
					return MoveType.INVALIDMOVE;
				}
			}

			if (unitOnDest != null) {
				if (unitOnDest.getControllingPlayer() == u.getControllingPlayer()) {
					// Combine units
					return MoveType.COMBINEUNITS;
				}
				else if (Unit.unitLevel(unitOnDest.getUnitType()) >= Unit.unitLevel(u.getUnitType())) {
					// Enemy unit is stronger
					return MoveType.INVALIDMOVE;
				}
				else if (villageOnDest == null) {
					// Enemy unit is weaker
					if (structureOnDest == StructureType.WATCHTOWER && Unit.unitLevel(u.getUnitType()) <= Unit.unitLevel(UnitType.INFANTRY)) {
						// but tile contains a watchtower and the attacking unit
						// is weaker than the tower
						return MoveType.INVALIDMOVE;
					}
					return MoveType.FREEMOVE;
				}
			}

			if (structureOnDest == StructureType.WATCHTOWER) {
				// dest contains a watchtower
				if (dest.getControllingPlayer() != u.getControllingPlayer()) {
					if (Unit.unitLevel(u.getUnitType()) <= Unit.unitLevel(UnitType.INFANTRY)) {
						return MoveType.INVALIDMOVE;
					}
				}
			}

			if (villageOnDest != null) {
				// Unit invades enemy village
				if ((u.getUnitType() == UnitType.KNIGHT || u.getUnitType() == UnitType.SOLDIER)
						&& villageOnDest.getControlledBy() != u.getVillage().getControlledBy()) {
					if (dest.getUnit() == null || Unit.unitLevel(u.getUnitType()) > Unit.unitLevel(dest.getUnit().getUnitType())) {
						return MoveType.FREEMOVE;
					}
					else {
						return MoveType.INVALIDMOVE;
					}
				}
				else if (villageOnDest.getControlledBy() == u.getVillage().getControlledBy()) {
					return MoveType.FREEMOVE;
				}
				else {
					return MoveType.INVALIDMOVE;
				}
			}
			if (landOnDest == TerrainType.TREE) {
				if (u.getUnitType() == UnitType.KNIGHT) {
					return MoveType.INVALIDMOVE;
				}
				else {
					return MoveType.GATHERWOOD;
				}
			}
			if (structureOnDest == StructureType.TOMBSTONE) {
				if (u.getUnitType() == UnitType.KNIGHT) {
					return MoveType.INVALIDMOVE;
				}
				else {
					return MoveType.CLEARTOMB;
				}
			}
			if (landOnDest == TerrainType.MEADOW) {
				if (u.getUnitType() == UnitType.KNIGHT || u.getUnitType() == UnitType.SOLDIER) {
					return MoveType.TRAMPLEMEADOW;
				}
				else {
					return MoveType.FREEMOVE;
				}
			}
			if (landOnDest == TerrainType.SEA) {
				return MoveType.INVALIDMOVE;
			}
			if (landOnDest == TerrainType.GRASS) {
				return MoveType.FREEMOVE;
			}
			return MoveType.INVALIDMOVE;
		}
		else {
			return MoveType.INVALIDMOVE;
		}
	}

	private MoveType getMoveType(Tile origin, Tile dest, UnitType uType, int immobileUntilRound, Player controllingPlayer) {
		List<Tile> adjacent = origin.getNeighbours();

		if (immobileUntilRound <= gameState.getRoundCount() && adjacent.contains(dest)
				&& controllingPlayer == gameState.getCurrentTurnPlayer() && uType != null) {
			TerrainType landOnDest = dest.getTerrainType();
			StructureType structureOnDest = dest.getStructure();
			Unit unitOnDest = dest.getUnit();
			Village villageOnDest = dest.getVillage();
			Region regionOnDest = dest.getRegion();

			if (regionOnDest != null) {
				if (regionOnDest.getControllingPlayer() != controllingPlayer && uType == UnitType.PEASANT) {
					return MoveType.INVALIDMOVE;
				}
			}

			if (unitOnDest != null) {
				if (unitOnDest.getControllingPlayer() == controllingPlayer) {
					// Combine units
					return MoveType.COMBINEUNITS;
				}
				else if (Unit.unitLevel(unitOnDest.getUnitType()) >= Unit.unitLevel(uType)) {
					// Enemy unit is stronger
					return MoveType.INVALIDMOVE;
				}
				else if (villageOnDest == null) {
					// Enemy unit is weaker
					if (structureOnDest == StructureType.WATCHTOWER && Unit.unitLevel(uType) <= Unit.unitLevel(UnitType.INFANTRY)) {
						// but tile contains a watchtower and the attacking unit
						// is weaker than the tower
						return MoveType.INVALIDMOVE;
					}
					return MoveType.FREEMOVE;
				}
			}

			if (structureOnDest == StructureType.WATCHTOWER) {
				// dest contains a watchtower
				if (dest.getControllingPlayer() != controllingPlayer) {
					if (Unit.unitLevel(uType) <= Unit.unitLevel(UnitType.INFANTRY)) {
						return MoveType.INVALIDMOVE;
					}
				}
			}

			if (villageOnDest != null) {
				// Unit invades enemy village
				if ((uType == UnitType.KNIGHT || uType == UnitType.SOLDIER)
						&& villageOnDest.getControlledBy() != controllingPlayer) {
					if (dest.getUnit() == null || Unit.unitLevel(uType) > Unit.unitLevel(dest.getUnit().getUnitType())) {
						return MoveType.FREEMOVE;
					}
					else {
						return MoveType.INVALIDMOVE;
					}
				}
				else if (villageOnDest.getControlledBy() == controllingPlayer) {
					return MoveType.FREEMOVE;
				}
				else {
					return MoveType.INVALIDMOVE;
				}
			}
			if (landOnDest == TerrainType.TREE) {
				if (uType == UnitType.KNIGHT) {
					return MoveType.INVALIDMOVE;
				}
				else {
					return MoveType.GATHERWOOD;
				}
			}
			if (structureOnDest == StructureType.TOMBSTONE) {
				if (uType == UnitType.KNIGHT) {
					return MoveType.INVALIDMOVE;
				}
				else {
					return MoveType.CLEARTOMB;
				}
			}
			if (landOnDest == TerrainType.MEADOW) {
				if (uType == UnitType.KNIGHT || uType == UnitType.SOLDIER) {
					return MoveType.TRAMPLEMEADOW;
				}
				else {
					return MoveType.FREEMOVE;
				}
			}
			if (landOnDest == TerrainType.SEA) {
				return MoveType.INVALIDMOVE;
			}
			if (landOnDest == TerrainType.GRASS) {
				return MoveType.FREEMOVE;
			}
			return MoveType.INVALIDMOVE;
		}
		else {
			return MoveType.INVALIDMOVE;
		}
	}
	
	private void combineUnits(Unit dest, Unit moved) {
		UnitType newLevel = null;
		int villageLevel = Village.villageLevel(dest.getVillage().getVillageType());
		if (dest.getUnitType() == UnitType.PEASANT && moved.getUnitType() == UnitType.PEASANT) {
			newLevel = UnitType.INFANTRY;
		}
		else if (villageLevel == 2 && dest.getUnitType() == UnitType.INFANTRY && moved.getUnitType() == UnitType.INFANTRY) {
			newLevel = UnitType.KNIGHT;
		}
		else if (villageLevel >= 1
				&& ((dest.getUnitType() == UnitType.PEASANT && moved.getUnitType() == UnitType.INFANTRY) || (dest.getUnitType() == UnitType.INFANTRY && moved
						.getUnitType() == UnitType.PEASANT))) {
			newLevel = UnitType.SOLDIER;
		}
		else if (villageLevel == 2
				&& ((dest.getUnitType() == UnitType.PEASANT && moved.getUnitType() == UnitType.SOLDIER) || (dest.getUnitType() == UnitType.SOLDIER && moved
						.getUnitType() == UnitType.PEASANT))) {
			newLevel = UnitType.KNIGHT;
		}
		else {
			return;
		}
		dest.setUnitType(newLevel);
		moved.getVillage().removeUnit(moved);
		moved.getTile().setUnit(null);
	}
	
	private void trampleMeadow(Unit u) {
		Tile t = u.getTile();
		// Only Soldier and Knight trample meadows
		if (Unit.unitLevel(u.getUnitType()) >= Unit.unitLevel(UnitType.INFANTRY) && t.getTerrainType() == TerrainType.MEADOW
				&& t.getStructure() != StructureType.ROAD) {
			u.getTile().setTerrainType(TerrainType.GRASS);
		}
	}
	
	private void clearTombstone(Unit u) {
		u.getTile().setStructure(null);
		u.setImmobileUntilRound(gameState.getRoundCount() + 1);
		u.setCurrentAction(ActionType.CLEARINGTOMBSTONE);
	}
	
	private void gatherWood(Unit u) {
		u.getTile().setTerrainType(TerrainType.GRASS);
		u.setImmobileUntilRound(gameState.getRoundCount() + 1);
		u.setCurrentAction(ActionType.CHOPPINGTREE);
		u.getVillage().transactWood(1);
	}
	

	private void combineRegions(Tile t) {
		List<Tile> adjacent = t.getNeighbours();

		for (Tile t1 : adjacent) {
			if (t.getControllingPlayer() == t1.getControllingPlayer() && t.getRegion() != t1.getRegion()) {
				Village oldV;
				Region oldR;
				Village newV;
				Region newR;

				// set the old and new regions and villages depending on which
				// level is greater.
				if (Village.villageLevel(t.getRegion().getVillage().getVillageType()) >= Village.villageLevel(t1.getRegion().getVillage()
						.getVillageType())) {
					oldR = t1.getRegion();
					oldV = oldR.getVillage();
					newR = t.getRegion();
					newV = newR.getVillage();
				}
				else {
					oldR = t.getRegion();
					oldV = oldR.getVillage();
					newR = t1.getRegion();
					newV = newR.getVillage();
				}

				// add the old village's gold and wood to the new village
				newV.transactWood(oldV.getWood());
				newV.transactGold(oldV.getGold());

				// loop through all tiles in old region and update
				for (Tile oldTile : oldR.getTiles()) {
					oldR.removeTile(oldTile);
					oldTile.setRegion(newR);
					newR.addTile(oldTile);
				}

				// loop through all units in old region and update
				for (Unit u : oldV.getSupportedUnits()) {
					oldV.removeUnit(u);
					u.setVillage(newV);
					newV.addUnit(u);
				}

				oldV.getTile().setVillage(null);
				oldV.getControlledBy().removeVillage(oldV);
			}
		}
	}

	/**
	 * This method determines whether or not the tiles in a given region are
	 * connected and if tiles have been disconnected, it determines whether the
	 * disconnected tiles can form a new region and creates a new region if that
	 * is the case.
	 * 
	 * @param r
	 *            The region to reconcile.
	 */
	private void reconcileRegions(Region r) {
		Player controllingPlayer = r.getControllingPlayer();
		Village originalVillage = r.getVillage();
		List<Tile> unreached = new ArrayList<Tile>(r.getTiles());
		List<Set<Tile>> newRegionTiles = new ArrayList<Set<Tile>>();

		while (unreached.size() > 0) {
			// Take the first tile (an arbitrary tile)
			Tile begin = unreached.get(0);
			Set<Tile> temp = new HashSet<Tile>();
			// Move the tile from the unreached list to the the temp list
			temp.add(begin);
			unreached.remove(begin);
			// Determine the set of connected tiles
			Set<Tile> connected = reachableTilesInSameRegion(begin, r, new HashSet<Tile>());
			// Add all the connected tiles to the temp list and remove them from
			// the unreached list
			temp.addAll(connected);
			unreached.removeAll(connected);
			// Add the temp list to the new region list
			newRegionTiles.add(temp);
		}

		for (Set<Tile> regCandidate : newRegionTiles) {
			if (regCandidate.size() < 3) {
				for (Tile t : regCandidate) {
					t.setRegion(null);
					if (t.getUnit() != null) {
						killUnit(t.getUnit());
					}
					if (t.getVillage() != null) {
						t.setVillage(null);
						t.setTerrainType(TerrainType.TREE);
					}
				}
			}
			else {
				Region newRegion;
				Village newVillage;
				newRegion = new Region(regCandidate, controllingPlayer);
				if (regCandidate.contains(originalVillage.getTile())) {
					newVillage = new Village(originalVillage.getTile(), controllingPlayer, regCandidate);
					newRegion.setVillage(newVillage);
				}
				else {
					if (isTurnOfPlayer(localPlayerName)) {
						newRegion.createVillage();
						newVillage = newRegion.getVillage();
						controller.placeVillageAtForPeers(newVillage.getTile().hashCode());
					}
				}

				for (Tile t : regCandidate) {
					newRegion.addTile(t);
					t.setRegion(newRegion);
				}
			}
		}
	}

	/**
	 * Recursive function to traverse the map and find all reachable tiles from
	 * the same former region
	 * 
	 * @param from
	 *            The tile to search from
	 * @param r
	 *            The "original" region the tiles were a part of
	 * @param reached
	 *            The set of tiles that has been reached
	 * @return
	 */
	private Set<Tile> reachableTilesInSameRegion(Tile from, Region r, Set<Tile> reached) {
		for (Tile t : from.getNeighbours()) {
			if (t.getRegion() == r && !reached.contains(t)) {
				reached.add(t);
				reached.addAll(reachableTilesInSameRegion(t, r, reached));
			}
		}
		return new HashSet<Tile>(reached);
	}
	
	private void takeoverTile(Tile dest) {
		Unit unit = dest.getUnit();
		Region destRegion = dest.getRegion();
		Village destVillage = dest.getVillage();
		Village unitsVillage = unit.getVillage();

		if (destVillage != null) {
			unitsVillage.transactGold(destVillage.getGold());
			unitsVillage.transactWood(destVillage.getWood());
			dest.setVillage(null);
		}

		destRegion.removeTile(dest);
		unitsVillage.getRegion().addTile(dest);
		reconcileRegions(destRegion);
		checkWinConditions();
	}
	
	private void checkWinConditions() {
		List<Player> players = new ArrayList<Player>(gameState.getPlayers());
		Iterator<Player> iter = players.iterator();
		while (iter.hasNext()) {
			Player p = iter.next();
			if (p.getVillages().size() <= 0) {
				iter.remove();
			}
		}
		if (players.size() <= 1) {
			gameState.setHasWon(true);
			//TODO: players.get(0).incrementWinCount();
			for (Player p : gameState.getPlayers()) {
				if (!p.equals(players.get(0))) {
					//TODO: p.incrementLossCount();
				}
			}
		}
	}
	
	int getMapHeight() {
		return gameState.getMap().getHeight();
	}
	
	int getMapWidth() {
		return gameState.getMap().getWidth();
	}
	
	UITileDescriptor getTile(int x, int y) {
		Tile t =gameState.getMap().getTile(x, y);
		TerrainType tt = t.getTerrainType();
		UnitType ut = null;
		StructureType st = t.getStructure();
		VillageType vt = null;
		ColourType ct = null;

		if (t.getControllingPlayer() != null) {
			ct = t.getControllingPlayer().getColour();
			if (t.getUnit() != null)
				ut = t.getUnit().getUnitType();
			if (t.getVillage() != null)
				vt = t.getVillage().getVillageType();
		}

		updatedTiles.remove(t);

		return new UITileDescriptor(x, y, tt, st, ut, vt, ct);
	}
	
	boolean hasUpdatedTiles() {
		return !updatedTiles.isEmpty();
	}
	
	List<UITileDescriptor> getUpdatedTiles() {
		List<UITileDescriptor> ret = new ArrayList<UITileDescriptor>();
		for (Tile t : updatedTiles) {
			TerrainType tt = t.getTerrainType();
			UnitType ut = null;
			StructureType st = t.getStructure();
			VillageType vt = null;
			ColourType ct = null;

			if (t.getControllingPlayer() != null) {
				ct = t.getControllingPlayer().getColour();
				if (t.getUnit() != null)
					ut = t.getUnit().getUnitType();
				if (t.getVillage() != null)
					vt = t.getVillage().getVillageType();
			}

			ret.add(new UITileDescriptor(t.getX(), t.getY(), tt, st, ut, vt, ct));
		}
		updatedTiles.clear();
		return ret;
	}
	
	UIVillageDescriptor getVillage(int x, int y) {
		Tile t = gameState.getMap().getTile(x, y);
		Village v = t.getVillage();
		if (v != null) {
			java.util.Map<UnitType, Integer> unittypes = new HashMap<UnitType, Integer>(4);
			List<Unit> units = v.getSupportedUnits();
			for (Unit u : units) {
				Integer count = unittypes.get(u.getUnitType());
				if (count == null) {
					unittypes.put(u.getUnitType(), 1);
				}
				else {
					unittypes.put(u.getUnitType(), count + 1);
				}
			}
			return new UIVillageDescriptor(x, y, v.getVillageType(), v.getGold(), v.getWood(), unittypes, v.getTotalIncome(),
					v.getTotalUpkeep());
		}
		else {
			return null;
		}
	}
	
	boolean isTurnOfPlayer(String name) {
		return (gameState.getCurrentTurnPlayer().getUsername().compareTo(name) == 0);
	}
	
	boolean isTurnOfLastPlayer() {
		int size = gameState.getPlayers().size();
		return (gameState.getCurrentTurnPlayer().getUsername().compareTo(gameState.getPlayers().get(size - 1).getUsername()) == 1);
	}
	
	String getCurrentTurnPlayerName() {
		return gameState.getCurrentTurnPlayer().getUsername();
	}
	
	int getCurrentRoundCount() {
		return gameState.getRoundCount();
	}
	
	void newGame(List<String> players, Map map) {
		List<Player> temp = new ArrayList<Player>();
		for (String s : players) {
			temp.add(new Player(s));
		}
		Map.setUpMap(temp, map);
		Game game = new Game(temp, map);
		Player.setUpPlayers(temp);
		setGameState(game);
		beginTurn(gameState.getCurrentTurnPlayer());
	}
	
	/**
	 * Not sure if the list from gameState.getPlayers() keeps its order, it
	 * should since the return is not making a copy.
	 */
	public void endTurn() {
		List<Player> players = gameState.getPlayers();
		int turnPosition = players.indexOf(gameState.getCurrentTurnPlayer());
		if (turnPosition == players.size() - 1) {
			gameState.incrementRoundCount();
			turnPosition = 0;
		}
		else {
			turnPosition++;
		}
		beginTurn(players.get(turnPosition));
	}
	
	private void beginTurn(Player p) {
		Map map = gameState.getMap();
		gameState.setTurnOf(p);
		phaseBuild(p);
		phaseTombstone(p, map);
		phaseIncome(p);
		phasePayment(p);
	}
	

	private void phaseBuild(Player p) {
		List<Unit> units = p.getUnits();
		ActionType currentAction;
		for (Unit u : units) {
			currentAction = u.getCurrentAction();
			if (currentAction == ActionType.STARTCULTIVATING) {
				u.setCurrentAction(ActionType.FINISHCULTIVATING);
			}
			else if (currentAction == ActionType.FINISHCULTIVATING) {
				u.setCurrentAction(ActionType.READYFORORDERS);
				u.getTile().setTerrainType(TerrainType.MEADOW);
			}
			else {
				// The road structure was already placed as part of the build
				// road operation
				u.setCurrentAction(ActionType.READYFORORDERS);
			}
		}
	}
	
	private void phaseTombstone(Player p, Map m) {
		m.replaceTombstonesWithTrees(p);
	}

	private void phaseIncome(Player p) {
		List<Village> villages = p.getVillages();
		for (Village v : villages) {
			v.transactGold(v.getTotalIncome());
		}
	}

	private void phasePayment(Player p) {
		List<Village> villages = p.getVillages();
		for (Village v : villages) {
			int totalUpkeep = v.getTotalUpkeep();
			if (v.getGold() < totalUpkeep) {
				killAllUnits(v);
			}
			else {
				v.transactGold(-1 * totalUpkeep);
			}
		}
	}
	
	void leaveGame() {
		updatedTiles.clear();
		gameState = null;
	}
	
	void addObserverToTiles() {
		if (gameState == null)
			return;
		Map map = gameState.getMap();
		for (Tile t : map.getTiles()) {
			t.addObserver(this);
			updatedTiles.add(t);
		}
	}
	
	List<Integer> growNewTrees() {
		return gameState.getMap().growNewTrees();
	}
	
	public void placeTreesAt(List<Integer> l) {
		gameState.getMap().placeTreesAt(l);
	}
	
	public void placeVillageAt(int hashcode) {
		Tile t = gameState.getMap().getTile(hashcode);
		Region r = t.getRegion();
		r.createVillage(t);
	}

	void setLocalPlayerName(String localPlayerName) {
		this.localPlayerName = localPlayerName;
	}

	Game getGameState() {
		return gameState;
	}

	public void setGameState(Object gameState) {
		if (gameState != null && gameState.getClass() == Game.class) {
			this.gameState = (Game)gameState;
			this.addObserverToTiles();
		}
	}
	
	List<UIActionType> getLegalMoves(int x, int y){
		List<UIActionType> legalMoves = new ArrayList<UIActionType>();
		Tile t = gameState.getMap().getTile(x, y);
		Unit u = t.getUnit();
		Region r = t.getRegion();
		Village v = null;
		Player p = null;
		if( r != null){
			v  = r.getVillage();
			p = r.getControllingPlayer(); 
		}else{
			return legalMoves;
		}
		if( p != gameState.getCurrentTurnPlayer()){
			return legalMoves;
		}
		for(UIActionType action : UIActionType.values()){
			switch (action) {
			case BUILDROAD:
				t = gameState.getMap().getTile(x, y);
				if (u != null) {
					if (u.getUnitType() == UnitType.PEASANT && t.getStructure() != StructureType.ROAD) {
						legalMoves.add(UIActionType.BUILDROAD);
					}
				}
				break;
			case BUILDTOWER:
				t = gameState.getMap().getTile(x, y);
				v = t.getRegion().getVillage();
				if (v.getWood() >= 5) {
					legalMoves.add(UIActionType.BUILDTOWER);
				}
				break;
			case BUILDUNITINFANTRY:
				if(u == null && v.getGold() >= Unit.unitCost(UnitType.INFANTRY)){
					legalMoves.add(UIActionType.BUILDUNITINFANTRY);
				}
				break;
			case BUILDUNITKNIGHT:
				if(u == null && v.getGold() >= Unit.unitCost(UnitType.KNIGHT)){
					legalMoves.add(UIActionType.BUILDUNITKNIGHT);
				}
				break;
			case BUILDUNITPEASANT:
				if(u == null && v.getGold() >= Unit.unitCost(UnitType.PEASANT)){
					legalMoves.add(UIActionType.BUILDUNITPEASANT);
				}
				break;
			case BUILDUNITSOLDIER:
				if(u == null && v.getGold() >= Unit.unitCost(UnitType.SOLDIER)){
					legalMoves.add(UIActionType.BUILDUNITSOLDIER);
				}
				break;
			case CULTIVATEMEADOW:
				if (u != null) {
					if (u.getUnitType() == UnitType.PEASANT && t.getTerrainType() == TerrainType.GRASS) {
						legalMoves.add(UIActionType.CULTIVATEMEADOW);
					}
				}
				break;
			case UPGRADEUNITSOLDIER:
				if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.SOLDIER)){
					if (v.getVillageType() == VillageType.HOVEL) {
						break;
					}
					int cost = Unit.unitCost(UnitType.SOLDIER) - Unit.unitCost(u.getUnitType());
					if (v.getGold() >= cost) {
						legalMoves.add(UIActionType.UPGRADEUNITSOLDIER);
					}
				}
				break;
			case UPGRADEUNITINFANTRY:
				if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.INFANTRY)){
					int cost = Unit.unitCost(UnitType.INFANTRY) - Unit.unitCost(u.getUnitType());
					if (v.getGold() >= cost) {
						legalMoves.add(UIActionType.UPGRADEUNITINFANTRY);
					}
				}
				break;
			case UPGRADEUNITKNIGHT:
				if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.SOLDIER)){
					if (v.getVillageType() == VillageType.HOVEL || v.getVillageType() == VillageType.TOWN) {
						break;
					}
					int cost = Unit.unitCost(UnitType.KNIGHT) - Unit.unitCost(u.getUnitType());
					if (v.getGold() >= cost) {
						legalMoves.add(UIActionType.UPGRADEUNITKNIGHT);
					}
				}
				break;
			case UPGRADEVILLAGETOWN:
				if (Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.FORT)){
					int cost = Village.villageCost(VillageType.TOWN) - Village.villageCost(v.getVillageType());
					if (v.getWood() >= cost) {
						legalMoves.add(UIActionType.UPGRADEVILLAGETOWN);
					}
				}
				break;
			case UPGRADEVILLAGEFORT:
				if (Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.FORT)){
					int cost = Village.villageCost(VillageType.TOWN) - Village.villageCost(v.getVillageType());
					if (v.getWood() >= cost) {
						legalMoves.add(UIActionType.UPGRADEVILLAGETOWN);
					}
				}
				break;
			case ENDTURN:
				legalMoves.add(UIActionType.ENDTURN);
			default:
				// TODO throw exception instead
			}
		}
		return legalMoves; 
	}
	/**
	 * Gets all tiles reachable in one turn by a unit starting at tile x,y 
	 * @param x x position of starting tile
	 * @param y y position of starting tile
	 * @return set of tiles reachable in one turn
	 */
	List<UITileDescriptor> getReachableTiles(int x, int y){
		List<Tile> reachableTiles = new ArrayList<Tile>();
		Tile startTile = this.gameState.getMap().getTile(x, y);
		Unit unitOnStart = startTile.getUnit();
		UnitType onStartType = null; 
		int immobileUntil = Integer.MAX_VALUE;
		Player controllingPlayer = null;
		if(unitOnStart != null){
			onStartType = unitOnStart.getUnitType();
			immobileUntil = unitOnStart.getImmobileUntilRound();
			controllingPlayer = unitOnStart.getControllingPlayer();
		}else{
			//No unit at tile, no reachable tiles
			return new ArrayList<UITileDescriptor>();
		}
		reachableTiles.add(startTile);
		List<Tile> adjacentToStart = startTile.getNeighbours();
		for(Tile t : adjacentToStart){
			MoveType move = getMoveType(startTile, t, onStartType, immobileUntil, controllingPlayer );
			if(move == MoveType.FREEMOVE || move == MoveType.TRAMPLEMEADOW){
				reachableTiles.add(t);
				if (t.getControllingPlayer() == controllingPlayer) {
					getReachableHelper(t, onStartType, immobileUntil, controllingPlayer, reachableTiles);
				}
			}
		}
		return tileListToUITileDescriptorList(reachableTiles);
	}
	private void getReachableHelper(Tile aTile, UnitType uType, int immobileUntil, Player controllingPlayer, List<Tile> reachableTiles){
		for(Tile t : aTile.getNeighbours()){
			MoveType move = getMoveType(aTile, t, uType, immobileUntil, controllingPlayer );
			if(move == MoveType.FREEMOVE || move == MoveType.TRAMPLEMEADOW){
				if(!reachableTiles.contains(t)){
					reachableTiles.add(t);
					if (t.getControllingPlayer() == controllingPlayer) {
						getReachableHelper(t, uType, immobileUntil, controllingPlayer, reachableTiles);
					}
				}
			}
		}
	}

	private List<UITileDescriptor> tileListToUITileDescriptorList(List<Tile> tileList){
		List<UITileDescriptor> toReturn = new ArrayList<UITileDescriptor>();
		for(Tile t : tileList){
			UITileDescriptor aDescriptor = new UITileDescriptor(t.getX(), t.getY(), null, null, null, null, null);
			toReturn.add(aDescriptor);
		}
		return toReturn;
	}

	private void killAllUnits(Village v) {
		List<Unit> units = new ArrayList<Unit>(v.getSupportedUnits());

		for (Unit u : units) {
			killUnit(u);
		}
	}

	private void killUnit(Unit u) {
		u.getVillage().removeUnit(u);
		u.getTile().setUnit(null);
		u.getTile().setStructure(StructureType.TOMBSTONE);
	}

	private void log(String msg) {
		System.out.println(msg);
	}

	@Override
	public void update(Observable o, Object arg) {
		updatedTiles.add((Tile) o);
	}
}
