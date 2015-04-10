package newworldorder.client.model;

import java.util.List;

import newworldorder.client.controller.ClientController;
import newworldorder.client.networking.CommandFactory;
import newworldorder.client.shared.UIActionType;
import newworldorder.client.shared.UITileDescriptor;
import newworldorder.client.shared.UIVillageDescriptor;

/**
 * This is the only public facing class in the model package. All communication with the model should go through
 * this class. In particular, classes outside of the model package should not have any knowledge of any class or enum
 * inside the model other than this class.
 */
public class ModelController {
	private static ModelController instance = null;
	private GameEngine engine = null;
	private boolean gameRunning;
	private String localPlayerName;
	private String mapFilePath = "assets/maps/seaside-skirmish.mwm";
	public boolean gameOver = false;
	
	private ModelController() {
		super();
		engine = new GameEngine(this);
	}
	
	public static ModelController getInstance() {
		if (instance == null) {
			instance = new ModelController();
			CommandFactory.getInstance(); // initialize the command factory
		}
		return instance;
	}
	
	/**
	 * This method handles ENDTURN.
	 */
	public void informOfUserAction(UIActionType action) {
		if (!gameRunning)
			return;
		if (isLocalPlayersTurn()) {
			if (isLastPlayer()) {
				List<Integer> newTrees = engine.growNewTrees();
				CommandFactory.createSyncTreesCommand(newTrees);
			}
			CommandFactory.createEndTurnCommand();
		}
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

		CommandFactory.createCommand(action, x, y);
	}

	/**
	 * This method handles MOVEUNIT. The first coordinate is interpreted 
	 * as the unit while the second coordinate is interpreted as the 
	 * destination tile.
	 */
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2) {
		if (!gameRunning)
			return;

		CommandFactory.createCommand(action, x1, y1, x2, y2);
	}
	
	public List<Integer> growNewTrees() {
		return engine.growNewTrees();
	}
	
	public void placeTreesAt(List<Integer> l) {
		engine.placeTreesAt(l);
	}
	
	public void placeVillageAtForPeers(int hashcode) {
		CommandFactory.createPlaceVillageCommand(hashcode);
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

	public void loadGame(String username, String filePath) {
		gameOver = false;
		Game gameState = null;
		try {
			gameState = ModelSerializer.loadGameState(filePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (gameState != null) {
			this.localPlayerName = username;
			
			engine.setGameState(gameState);
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
	
	public void setMapFilePath(String path) {
		this.mapFilePath = path;
	}
	
	public void setupNetworking(String exchange) {
		if (exchange != null) {
			System.out.println("Exchange : " + exchange);
			CommandFactory.setupNetworking(exchange);
		}
	}

	public void newGame(String username, List<String> players) {
		gameOver = false;
		this.localPlayerName = username;
		engine.setLocalPlayerName(username);
		Map presetMap = null;
		try {
			presetMap = ModelSerializer.loadMap(mapFilePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("engine.getGameState()= "+engine.getGameState());
		if (engine.getGameState() != null) {
			gameRunning = true;
			return;
		}
		System.out.println("presetMap="+presetMap);
		if (presetMap != null) {
			System.out.println("players.get(0).compareTo(username) == 0  "+ (players.get(0).compareTo(username) == 0));
			if (players.get(0).compareTo(username) == 0){
				engine.newGame(players, presetMap);
				System.out.println("First player");
				while (!CommandFactory.hasNetworking()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				CommandFactory.createSetupGameCommand(engine.getGameState());
			}
			gameRunning = true;
		}
	}
	
	public void distributeGameState() {
		CommandFactory.createSetupGameCommand(engine.getGameState());
	}
	
	public void setGameState(Object gameState) {
		gameOver = false;
		if (gameState instanceof Game || gameState == null) {
			engine.resetObservers();
			engine.setGameState((Game) gameState);
		}
	}
	
	public Object getGameState() {
		return engine.getGameState();
	}
	
	public GameEngine getEngine() {
		return engine;
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
		return engine.isLastPlayer();
	}
	
	public boolean isGameOver() {
		return gameOver;
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
	
	public List<UITileDescriptor> getReachableTiles(int x, int y){
		return engine.getReachableTiles(x, y);
	}
	public List<UITileDescriptor> getBombardableTiles(int x, int y){
		return engine.getBombardableTiles(x, y);
	}
	public List<UIActionType> getLegalMoves(int x, int y){
		return engine.getLegalMoves(x, y);
	}
	
	public boolean validatePlayers(List<String> playerNames) {
		if (engine.getGameState() == null) {
			return false;
		} else {
			List<Player> players = engine.getGameState().getPlayers();
			for (Player p : players) {
				if (!playerNames.contains(p.getUsername())) {
					return false;
				}
			}
			if (playerNames.size() != players.size()) return false;
		}
		return true;
	}
	public boolean hasGameState(){
		if(engine.getGameState() == null){
			return false;
		}else{
			return true;
		}
	}
	public void clearGameState() {
			System.out.println("In clearGameState in ModelController");
			engine.resetObservers();
			engine.clearGameState();
	}	
	public void endGame(String winners, List<String> losers) {
		System.out.println("In model controlelr end game");
		ClientController.getInstance().endGame(losers, winners);
		gameOver = true;
	}
}
