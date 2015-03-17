package newworldorder.client.model;

import java.util.List;

import newworldorder.client.shared.UIActionType;
import newworldorder.client.shared.UITileDescriptor;
import newworldorder.client.shared.UIVillageDescriptor;

public class ModelController {
	private static ModelController instance = null;
	private GameEngine engine = null;
	private boolean gameRunning;
	private String localPlayerName;
	private String exchange;
	
	
	private ModelController() {
		super();
		engine = new GameEngine();
	}
	
	public static ModelController getInstance() {
		if (instance == null) {
			instance = new ModelController();
		}
		return instance;
	}
	
	/**
	 * This method handles ENDTURN.
	 */
	public void informOfUserAction(UIActionType action) {
		if (!gameRunning)
			return;

		if (isLocalPlayersTurn() && isLastPlayer()) {
			// List<Integer> newTrees = engine.growNewTrees();
			// TODO: SyncTreesCommand command = new SyncTreesCommand(newTrees);
			// sendCommand(command);
		}
		engine.endTurn();
	}

	/**
	 * This method handles BUILDROAD, BUILDTOWER, CULTIVATEMEADOW, UPGRADEUNIT*,
	 * UPGRADEVILLAGE*, and BUILDUNIT*. All other UIActionTypes are ignored.
	 * 
	 * @param action
	 *            The UIActionType
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void informOfUserAction(UIActionType action, int x, int y) {
		System.out.println("Received action");

		if (!gameRunning)
			return;

		switch (action) {
		case BUILDROAD:
			engine.buildRoad(x, y);
		case BUILDTOWER:
			engine.buildTower(x, y);
		case BUILDUNITINFANTRY:
			engine.buildUnitInfantry(x, y);
		case BUILDUNITKNIGHT:
			engine.buildUnitKnight(x, y);
		case BUILDUNITPEASANT:
			engine.buildUnitPeasant(x, y);
		case BUILDUNITSOLDIER:
			engine.buildUnitSoldier(x, y);
		case CULTIVATEMEADOW:
			engine.cultivateMeadow(x, y);
		case UPGRADEUNITSOLDIER:
			engine.upgradeUnitSoldier(x, y);
		case UPGRADEUNITINFANTRY:
			engine.upgradeUnitInfantry(x, y);
		case UPGRADEUNITKNIGHT:
			engine.upgradeUnitKnight(x, y);
		case UPGRADEVILLAGETOWN:
			engine.upgradeVillageTown(x, y);
		case UPGRADEVILLAGEFORT:
			engine.upgradeVillageFort(x, y);
		case ENDTURN:
			engine.endTurn();
		default:
			// TODO throw exception instead
		}
	}

	/**
	 * This method handles MOVEUNIT. The first coordinate is interpreted 
	 * as the unit while the second coordinate is interpreted as the 
	 * destination tile.
	 */
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2) {
		if (!gameRunning)
			return;

		engine.moveUnit(x1, y1, x2, y2);
	}

	public UITileDescriptor getTile(int x, int y) {
		return engine.getTile(x, y);
	}

	public List<UITileDescriptor> getUpdatedTiles() {
		return engine.getUpdatedTiles();
	}

	public UIVillageDescriptor getVillage(int x, int y) {
		return engine.getVillage(x, y);
	}

	public void loadGame(String filePath) {
		Game gameState = null;
		try {
			gameState = ModelSerializer.loadGameState(filePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (gameState != null) {
			engine.setGameState(gameState);
			engine.addObserverToTiles(gameState.getMap());
			gameRunning = true;
		}
	}

	public void saveGame(String filePath) {
		Game gameState = engine.getGameState();
		try {
			ModelSerializer.saveGameState(gameState, filePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void newGame(String username, List<String> players, String mapFilePath) {
		this.localPlayerName = username;
		Map presetMap = null;
		System.out.println("Exchange: " + exchange);
		try {
			presetMap = ModelSerializer.loadMap(mapFilePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (presetMap != null) {
			if (players.get(0).compareTo(username) == 0) {
				engine.newGame(players, presetMap);
				System.out.println("First player");
				// TODO: IGameCommand command = new SetupGameCommand(engine.getGameState());
				// amqpAdapter.send(command, exchange, "");
			}
			gameRunning = true;
		}
	}
	
	public void setGameState(Object gameState) {
		if (gameState instanceof Game) {
			engine.setGameState((Game) gameState);
		}
	}
	
	public Object getGameState() {
		return engine.getGameState();
	}

	public void leaveGame() {
		engine.leaveGame();
		gameRunning = false;
	}

	public int getMapHeight() {
		if (gameRunning)
			return engine.getMapHeight();
		else
			return -1;
	}

	public int getMapWidth() {
		if (gameRunning)
			return engine.getMapWidth();
		else
			return -1;
	}

	public boolean hasUpdatedTiles() {
		return engine.hasUpdatedTiles();
	}

	public boolean isLocalPlayersTurn() {
		return (engine.isTurnOfPlayer(localPlayerName));
	}
	
	public boolean isLastPlayer() {
		return engine.isTurnOfLastPlayer();
	}

	public void setLocalPlayerName(String localPlayerName) {
		this.localPlayerName = localPlayerName;
	}

	public String getLocalPlayerName() {
		return localPlayerName;
	}

	public String getCurrentTurnPlayer() {
		return engine.getCurrentTurnPlayerName();
	}

	public int getTurnNumber() {
		return engine.getCurrentRoundCount();
	}
	
//	public void sendCommand(IGameCommand command) {
//		amqpAdapter.send(command, exchange, "");
//	}

}
