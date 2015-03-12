package newworldorder.game.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.CommandConsumer;
import newworldorder.game.command.CommandFactory;
import newworldorder.game.command.GameCommandHandler;
import newworldorder.game.command.IGameCommand;
import newworldorder.game.command.SetupGameCommand;
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
	private static ModelManager instance = null;
	private GameEngine engine;
	private boolean gameRunning;
	private Set<Tile> updatedTiles;
	private AmqpAdapter amqpAdapter;
	private CommandConsumer consumer;
	private String exchange;
	private int localPlayerId;

	private ModelManager() {
		engine = new GameEngine();
		gameRunning = false;
		updatedTiles = new HashSet<Tile>();
	}

	public static ModelManager getInstance() {
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

		IGameCommand command = CommandFactory.createCommand(action);
		amqpAdapter.send(command, exchange, "");
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
	@Override
	public void informOfUserAction(UIActionType action, int x, int y) {
		System.out.println("Received action");

		if (!gameRunning)
			return;

		IGameCommand command = CommandFactory.createCommand(action, x, y);
		amqpAdapter.send(command, exchange, "");
	}

	/**
	 * This method handles MOVEUNIT. For BUILDUNIT* the first coordinate (x1,
	 * y1) is interpreted as the village while the second coordinate (x2, y2) is
	 * interpreted as the tile to place the new unit. For MOVEUNIT the first
	 * coordinate is interpreted as the unit while the second coordinate is
	 * interpreted as the destination tile.
	 */
	@Override
	public void informOfUserAction(UIActionType action, int x1, int y1, int x2, int y2) {
		if (!gameRunning)
			return;

		IGameCommand command = CommandFactory.createCommand(action, x1, y1, x2, y2);
		amqpAdapter.send(command, exchange, "");
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

			ret.add(new UITileDescriptor(t.getX(), t.getY(), tt, st, ut, vt, ct));
		}
		updatedTiles.clear();
		return ret;
	}

	@Override
	public UIVillageDescriptor getVillage(int x, int y) {
		Tile t = engine.getGameState().getMap().getTile(x, y);
		Village v = t.getVillage();
		if (v != null) {
			Map<UnitType, Integer> unittypes = new HashMap<UnitType, Integer>(4);
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

	@Override
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
			addObserverToTiles(gameState.getMap());
			gameRunning = true;
		}
	}

	@Override
	public void saveGame(String filePath) {
		Game gameState = engine.getGameState();
		try {
			ModelSerializer.saveGameState(gameState, filePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void newGame(String username, GameInfo gameInfo, String mapFilePath) {
		newworldorder.game.model.Map presetMap = null;
		List<Player> players = initPlayers(gameInfo.getPlayers());
		exchange = gameInfo.getGameExchange();
		localPlayerId = username.hashCode();
		System.out.println("Exchange: " + exchange);
		this.setupNetworking();
		try {
			presetMap = ModelSerializer.loadMap(mapFilePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (presetMap != null) {
			engine.newGame(players, presetMap);
			if (this.isLocalPlayersTurn()) {
				System.out.println("First player");
				IGameCommand command = new SetupGameCommand(engine.getGameState());
				amqpAdapter.send(command, exchange, "");
			}
			gameRunning = true;
		}
	}

	private List<Player> initPlayers(List<String> playerIds) {
		// TODO how to initialize players?
		List<Player> ret = new ArrayList<Player>();
		// Temporary (pending David's insight)...use hashcode of username
		for (String s : playerIds) {
			ret.add(new Player(s.hashCode(), s, s, 0, 0, null));
		}
		return ret;
	}

	@Override
	public void leaveGame() {
		// TODO Correct implementation
		engine.setGameState(null);
		gameRunning = false;
		updatedTiles.clear();
	}

	@Override
	public void update(Observable tile, Object ignoredParameter) {
		updatedTiles.add((Tile) tile);
	}
	
	@Override
	public void addObserverToTiles(newworldorder.game.model.Map map) {
		for (Tile t : map.getTiles()) {
			t.addObserver(this);
			updatedTiles.add(t);
		}
	}

	@Override
	public int getMapHeight() {
		if (gameRunning)
			return engine.getGameState().getMap().getHeight();
		else
			return -1;
	}

	@Override
	public int getMapWidth() {
		if (gameRunning)
			return engine.getGameState().getMap().getWidth();
		else
			return -1;
	}

	@Override
	public boolean hasUpdatedTiles() {
		// System.out.println("Received call to hasUpdatedTiles");
		return !updatedTiles.isEmpty();
	}

	@Override
	public boolean isLocalPlayersTurn() {
		return (engine.getGameState().getCurrentTurnPlayer().getPlayerId() == localPlayerId);
	}
	
	@Override
	public boolean isLastPlayer() {
		int numPlayers = engine.getGameState().getPlayers().size();
		System.out.println(engine.getGameState().getPlayers().get(numPlayers - 1).getPlayerId() == localPlayerId);
		return engine.getGameState().getPlayers().get(numPlayers - 1).getPlayerId() == localPlayerId;
	}

	@Override
	public void setLocalPlayerId(int localPlayerId) {
		this.localPlayerId = localPlayerId;
	}

	@Override
	public int getLocalPlayerId() {
		return localPlayerId;
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
		consumer = new CommandConsumer(admin, container);
		consumer.startConsumingFromFanoutExchange(exchange);
	}

	@Override
	public String getCurrentPlayerTurn() {
		return engine.getGameState().getCurrentTurnPlayer().getUsername();
	}

	@Override
	public int getTurnNumber() {
		return engine.getGameState().getRoundCount();
	}
	
	@Override
	public void sendCommand(IGameCommand command) {
		amqpAdapter.send(command, exchange, "");
	}
}
