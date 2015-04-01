package newworldorder.client.model;

import java.util.List;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.client.shared.UIActionType;
import newworldorder.client.shared.UITileDescriptor;
import newworldorder.client.shared.UIVillageDescriptor;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.CommandConsumer;
import newworldorder.game.command.CommandFactory;
import newworldorder.game.command.GameCommandHandler;
import newworldorder.game.command.IGameCommand;
import newworldorder.game.command.SetupGameCommand;
//import newworldorder.game.command.SyncTreesCommand;

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
	private AmqpAdapter amqpAdapter;
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
			List<Integer> newTrees = engine.growNewTrees();
			IGameCommand command = CommandFactory.createSyncTreesCommand(newTrees);
			sendCommand(command);
		}
		IGameCommand endTurnCommand = CommandFactory.createEndTurnCommand();
		sendCommand(endTurnCommand);
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

		IGameCommand command = CommandFactory.createCommand(action, x, y);
		sendCommand(command);
	}

	/**
	 * This method handles MOVEUNIT. The first coordinate is interpreted 
	 * as the unit while the second coordinate is interpreted as the 
	 * destination tile.
	 */
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2) {
		if (!gameRunning)
			return;

		IGameCommand command = CommandFactory.createCommand(action, x1, y1, x2, y2);
		sendCommand(command);
	}
	
	public List<Integer> growNewTrees() {
		return engine.growNewTrees();
	}
	
	public void placeTreesAt(List<Integer> l) {
		engine.placeTreesAt(l);
	}
	
	public void placeVillageAtForPeers(int hashcode) {
		IGameCommand command = CommandFactory.createPlaceVillageCommand(hashcode);
		sendCommand(command);
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
	
	void newGame(String username, List<String> players, String mapFilePath) {
		this.localPlayerName = username;
		Map presetMap = null;
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
			}
			gameRunning = true;
		}
	}

	public void newGame(String username, List<String> players, String exchange, String mapFilePath) {
		this.localPlayerName = username;
		engine.setLocalPlayerName(username);
		this.exchange = exchange;
		System.out.println("Exchange: " + exchange);
		this.setupNetworking();
		Map presetMap = null;
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
				IGameCommand command = new SetupGameCommand(engine.getGameState());
				amqpAdapter.send(command, exchange, "");
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
	
	public List<UITileDescriptor> getReachableTiles(int x, int y){
		return engine.getReachableTiles(x, y);
	}
	public List<UIActionType> getLegalMoves(int x, int y){
		return engine.getLegalMoves(x, y);
	}
	
	public void sendCommand(IGameCommand command) {
		amqpAdapter.send(command, exchange, "");
	}
	
	private void setupNetworking() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("104.236.30.10", 5672);
		connectionFactory.setUsername("newworldorder");
		connectionFactory.setPassword("warfare");
		amqpAdapter = new AmqpAdapter(new RabbitTemplate(connectionFactory));
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setAutoStartup(false);
		container.setConnectionFactory(connectionFactory);
		GameCommandHandler handler = new GameCommandHandler(engine);
		container.setMessageListener(new MessageListenerAdapter(handler, "handle"));
		AmqpAdmin admin = new RabbitAdmin(connectionFactory);
		CommandConsumer consumer = new CommandConsumer(admin, container);
		consumer.startConsumingFromFanoutExchange(exchange);
	}

}
