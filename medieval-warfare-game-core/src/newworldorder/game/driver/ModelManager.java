package newworldorder.game.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import newworldorder.game.model.ColourType;
import newworldorder.game.model.Game;
import newworldorder.game.model.GameEngine;
import newworldorder.game.model.Player;
import newworldorder.game.model.StructureType;
import newworldorder.game.model.TerrainType;
import newworldorder.game.model.Tile;
import newworldorder.game.model.Unit;
import newworldorder.game.model.UnitType;
import newworldorder.game.model.Village;
import newworldorder.game.model.VillageType;

public class ModelManager implements IModelCommunicator, Observer {
	
	private ModelManager instance;
	private GameEngine engine;
	private boolean gameRunning;
	private Set<Tile> updatedTiles;
	
	private ModelManager() {
		engine = new GameEngine();
		gameRunning = false;
		updatedTiles = new HashSet<Tile>();
	}
	
	@Override
	public ModelManager getInstance() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;
	}


	/**
	 * This method handles ENDTURN.
	 */
	@Override
	public void informOfUserAction(UIActionType action) {
		if (!gameRunning)
			return;
		
		switch (action) {
		case ENDTURN:
			engine.endTurn();
			break;
		default:
			break;
		}
	}
	
	/**
	 * This method handles BUILDROAD, BUILDTOWER, CULTIVATEMEADOW, UPGRADEUNIT*, and 
	 * UPGRADEVILLAGE*. All other UIActionTypes are ignored. 
	 * @param action The UIActionType
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	@Override
	public void informOfUserAction(UIActionType action, int x, int y) {
		if (!gameRunning)
			return;
		
		Unit u;
		Village v;
		Tile t = engine.getGameState().getMap().getTile(x, y);
		
		switch (action) {
		case BUILDROAD:
			u = t.getUnit();
			if (u != null)
				engine.buildRoad(u);
			break;
		case BUILDTOWER:
			engine.buildTower(t);
			break;
		case CULTIVATEMEADOW:
			u = t.getUnit();
			if (u != null)
				engine.cultivateMeadow(u);
			break;
		case UPGRADEUNITSOLDIER:
			u = t.getUnit();
			if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.SOLDIER))
				engine.upgradeUnit(u, UnitType.SOLDIER);
			break;
		case UPGRADEUNITINFANTRY:
			u = t.getUnit();
			if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.INFANTRY))
				engine.upgradeUnit(u, UnitType.INFANTRY);
			break;
		case UPGRADEUNITKNIGHT:
			u = t.getUnit();
			if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.KNIGHT))
				engine.upgradeUnit(u, UnitType.KNIGHT);
			break;
		case UPGRADEVILLAGETOWN:
			v = t.getVillage();
			if (v != null && Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.TOWN))
				engine.upgradeVillage(v, VillageType.TOWN);
			break;
		case UPGRADEVILLAGEFORT:
			v = t.getVillage();
			if (v != null && Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.FORT))
				engine.upgradeVillage(v, VillageType.FORT);
			break;
		default:
			break;
		}
	}

	/**
	 * This method handles BUILDUNIT* and MOVEUNIT. For BUILDUNIT* the first coordinate (x1, y1) is
	 * interpreted as the village while the second coordinate (x2, y2) is interpreted as the tile to
	 * place the new unit. For MOVEUNIT the first coordinate is interpreted as the unit while the 
	 * second coordinate is interpreted as the destination tile.
	 */
	@Override
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2) {
		if (!gameRunning)
			return;
		
		Unit u;
		Village v;
		Tile t1 = engine.getGameState().getMap().getTile(x1, y1);
		Tile t2 = engine.getGameState().getMap().getTile(x2, y2);
		
		switch (action) {
		case BUILDUNITINFANTRY:
			v = t1.getVillage();
			if (v != null)
				engine.buildUnit(v, t2, UnitType.INFANTRY);
			break;
		case BUILDUNITKNIGHT:
			v = t1.getVillage();
			if (v != null)
				engine.buildUnit(v, t2, UnitType.KNIGHT);
			break;
		case BUILDUNITPEASANT:
			v = t1.getVillage();
			if (v != null)
				engine.buildUnit(v, t2, UnitType.PEASANT);
			break;
		case BUILDUNITSOLDIER:
			v = t1.getVillage();
			if (v != null)
				engine.buildUnit(v, t2, UnitType.SOLDIER);
			break;
		case MOVEUNIT:
			u = t1.getUnit();
			if (u != null)
				engine.moveUnit(u, t2);
			break;
		default:
			break;
		}
	}

	@Override
	public UITileDescriptor getTile(int x, int y) {
		Tile t = engine.getGameState().getMap().getTile(x, y);
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

	@Override
	public List<UITileDescriptor> getUpdatedTiles() {
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
			
			ret.add( new UITileDescriptor(t.getX(), t.getY(), tt, st, ut, vt, ct) );
		}
		updatedTiles.clear();
		return ret;
	}
	
	@Override
	public UIVillageDescriptor getVillage(int x, int y) {
		Tile t = engine.getGameState().getMap().getTile(x, y);
		Village v = t.getVillage();
		if (v != null) {
			Map<UnitType,Integer> unittypes = new HashMap<UnitType,Integer>(4);
			List<Unit> units = v.getSupportedUnits();
			for (Unit u : units) {
				Integer count = unittypes.get(u.getUnitType());
				if (count == null) {
					unittypes.put(u.getUnitType(), 1);
				} else {
					unittypes.put(u.getUnitType(), count + 1);
				}
			}
			return new UIVillageDescriptor(x, y, v.getVillageType(), v.getGold(), v.getWood(), unittypes, v.getTotalIncome(), v.getTotalUpkeep());
		} else {
			return null;
		}
	}

	@Override
	public void loadGame(String filePath) {
		Game gameState = null;
		try {
			gameState = ModelSerializer.loadGameState(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (gameState != null) {
			engine.setGameState(gameState);
			addObserverToTiles(gameState.getMap());
			gameRunning = true;
		}
	}

	@Override
	public void saveGame(String filePath) {
		Game gameState = engine.getGameState();
		try {
			ModelSerializer.saveGameState(gameState, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void newGame(List<String> playerIds, String mapFilePath) {
		// TODO how to initialize players?
		newworldorder.game.model.Map presetMap = null;
		List<Player> players = null;
		try {
			presetMap = ModelSerializer.loadMap(mapFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (presetMap != null) {
			engine.newGame(players, presetMap);
			addObserverToTiles(engine.getGameState().getMap());
			gameRunning = true;
		}
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void joinGame(String gameExchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaveGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable tile, Object ignoredParameter) {
		updatedTiles.add((Tile) tile);
	}
	
	private void addObserverToTiles(newworldorder.game.model.Map map) {
		for (Tile t : map.getTiles()) {
			t.addObserver(this);
			updatedTiles.add(t);
		}
	}

}
