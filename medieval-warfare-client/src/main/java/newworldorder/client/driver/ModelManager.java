package newworldorder.client.driver;

import java.util.List;

import newworldorder.client.model.GameEngine;
import newworldorder.client.model.Tile;
import newworldorder.client.model.Unit;
import newworldorder.client.model.UnitType;
import newworldorder.client.model.Village;
import newworldorder.client.model.VillageType;

public class ModelManager implements IModelCommunicator {
	
	ModelManager instance;
	GameEngine engine;
	boolean gameRunning;
	
	private ModelManager() {
		engine = new GameEngine();
		gameRunning = false;
	}
	
	@Override
	public ModelManager getInstance() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;
	}


	@Override
	public void informOfUserAction(UIActionType action) {
		// TODO Auto-generated method stub
		
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
		// TODO Complete implementation
		
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

	@Override
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub

	}

	@Override
	public UITileDescriptor getTile(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UITileDescriptor> getUpdatedTiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadGame(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGame(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newGame(List<String> playerIds, String mapFilePath) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connect(String serverAddress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

}
