package newworldorder.client.networking;

import java.util.List;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import newworldorder.client.service.GdxAppController;
import newworldorder.client.shared.UIActionType;
import newworldorder.client.model.GameEngine;
import newworldorder.client.model.ModelController;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.CommandConsumer;

public class CommandFactory {
	
	private static CommandFactory instance;
	private static AmqpAdapter amqpAdapter;
	private static ModelController model;
	private static String exchange;
	
	private CommandFactory() {
		super();
	}
	
	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
			CommandFactory.model = ModelController.getInstance();
		}
		return instance;
	}
	
	private static void sendCommand(IGameCommand command) {
		amqpAdapter.send(command, exchange, "");
	}
	
	private static void setupNetworking() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("104.236.30.10", 5672);
		connectionFactory.setUsername("newworldorder");
		connectionFactory.setPassword("warfare");
		amqpAdapter = new AmqpAdapter(new RabbitTemplate(connectionFactory));
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setAutoStartup(false);
		container.setConnectionFactory(connectionFactory);
		GameCommandHandler handler = new GameCommandHandler(model.getEngine());
		container.setMessageListener(new MessageListenerAdapter(handler, "handle"));
		AmqpAdmin admin = new RabbitAdmin(connectionFactory);
		CommandConsumer consumer = new CommandConsumer(admin, container);
		consumer.startConsumingFromFanoutExchange(exchange);
	}	

	public static void setupNetworking(String exchange) {
		// TODO this will need to be fixed if setupNetworking can only be called once.
		CommandFactory.exchange = exchange; 
		setupNetworking();
	}
	
	public static void createSetupGameCommand(Object gameState) {
		CommandFactory.sendCommand(new SetupGameCommand(gameState));
	}
	
	public static void createEndTurnCommand() {
		CommandFactory.sendCommand(new EndTurnCommand());
	}
	
	public static void createSyncTreesCommand(List<Integer> newTrees) {
		CommandFactory.sendCommand( new SyncTreesCommand(newTrees));
	}
	
	public static void createPlaceVillageCommand(int hashcode) {
		CommandFactory.sendCommand( new PlaceVillageCommand(hashcode));
	}

	public static void createCommand(UIActionType action, int x, int y) {
		IGameCommand command = null;
		switch (action) {
		case BUILDROAD:
			command = new BuildRoadCommand(x, y);
			break;
		case BUILDTOWER:
			command =  new BuildTowerCommand(x, y);
			break;
		case BUILDUNITINFANTRY:
			command =  new BuildUnitInfantryCommand(x, y);
			break;
		case BUILDUNITKNIGHT:
			command =  new BuildUnitKnightCommand(x, y);
			break;
		case BUILDUNITPEASANT:
			command =  new BuildUnitPeasantCommand(x, y);
			break;
		case BUILDUNITSOLDIER:
			command =  new BuildUnitSoldierCommand(x, y);
			break;
		case BUILDUNITCANNON:
			command = new BuildUnitCannonCommand(x, y);
			break;
		case CULTIVATEMEADOW:
			command =  new CultivateMeadowCommand(x, y);
			break;
		case UPGRADEUNITSOLDIER:
			command =  new UpgradeUnitSoldierCommand(x, y);
			break;
		case UPGRADEUNITINFANTRY:
			command =  new UpgradeUnitInfantryCommand(x, y);
			break;
		case UPGRADEUNITKNIGHT:
			command =  new UpgradeUnitKnightCommand(x, y);
			break;
		case UPGRADEVILLAGETOWN:
			command =  new UpgradeVillageTownCommand(x, y);
			break;
		case UPGRADEVILLAGEFORT:
			command =  new UpgradeVillageFortCommand(x, y);
			break;
		case ENDTURN:
			command =  new EndTurnCommand();
			break;
		default:
			command =  null; // TODO throw exception instead
			break;
		}
		CommandFactory.sendCommand(command);
	}

	public static void createCommand(UIActionType action, int x1, int y1, int x2, int y2) {
		IGameCommand command = null;
		switch(action){
		case MOVEUNIT:
			command = new MoveUnitCommand(x1, y1, x2, y2);
			break;
		case BOMBARDTILE:
			command = new BombardTileCommand(x1, y1, x2, y2);
			break;
		default:
			command = null; //TODO: throw exception instead
		}
		
		CommandFactory.sendCommand( command );
	}
	
	private static abstract class AbstractGameCommand implements IGameCommand {
		private static final long serialVersionUID = -4265280207834818270L;

		protected transient GameEngine engine;
		
		public final void execute (GameEngine engine) {
			this.engine = engine;
			this.execute();
		}
		
		public abstract void execute();
	}
	
	private static class EndTurnCommand extends AbstractGameCommand {
		private static final long serialVersionUID = -2144057378224658285L;
		
		public EndTurnCommand() {
			super();
		}

		@Override
		public void execute() {
			engine.endTurn();
		}
	}
	
	private static class SetupGameCommand extends AbstractGameCommand {
		private static final long serialVersionUID = 2376599495011006579L;
		private final Object gameState;

		public SetupGameCommand(Object gameState) {
			super();
			this.gameState = gameState;
		}

		@Override
		public void execute() {
			engine.setGameState(gameState);
			GdxAppController.showGdxApp();
		}

	}
	
	private static class SyncTreesCommand extends AbstractGameCommand {
		private static final long serialVersionUID = -6605499829717076737L;
		private final List<Integer> newTrees;
		
		public SyncTreesCommand(List<Integer> newTrees) {
			this.newTrees = newTrees;
		}
		
		@Override
		public void execute() {
			engine.placeTreesAt(newTrees);
		}
	}

	private static class PlaceVillageCommand extends AbstractGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2715015379925412885L;
		int hashcode;
		
		public PlaceVillageCommand(int hashcode) {
			this.hashcode = hashcode;
		}

		@Override
		public void execute() {
			engine.placeVillageAt(hashcode);
		}
	}

	private static class MoveUnitCommand extends AbstractGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4408577083911356294L;
		private int x1, x2, y1, y2;

		private MoveUnitCommand(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
		}

		@Override
		public void execute() {
			engine.moveUnit(x1, y1, x2, y2);
		}

	}
	private static class BombardTileCommand extends AbstractGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2633305860510484967L;
		private int x1, x2, y1, y2;

		private BombardTileCommand(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
		}

		@Override
		public void execute() {
			engine.bombardTile(x1, y1, x2, y2);
		}

	}
	private static class BuildRoadCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2362284179466757218L;

		private BuildRoadCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildRoad(x, y);
		}
	}

	private static class BuildTowerCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4592501404786423313L;

		private BuildTowerCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildTower(x, y);
		}
	}

	private static class BuildUnitInfantryCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8361718221141554719L;

		private BuildUnitInfantryCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildUnitInfantry(x, y);
		}
	}

	private static class BuildUnitKnightCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5490048374673492457L;

		private BuildUnitKnightCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildUnitKnight(x, y);
		}
	}

	private static class BuildUnitPeasantCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4507310855569037969L;

		private BuildUnitPeasantCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildUnitPeasant(x, y);
		}
	}

	private static class BuildUnitSoldierCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2101849611567641797L;

		private BuildUnitSoldierCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildUnitSoldier(x, y);
		}
	}
	private static class BuildUnitCannonCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3424346655797191162L;


		private BuildUnitCannonCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildUnitCannon(x, y);
		}
	}
	private static class CultivateMeadowCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1561192796063811126L;

		private CultivateMeadowCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.cultivateMeadow(x, y);
		}
	}

	private static class UpgradeUnitSoldierCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2188245023255296834L;

		private UpgradeUnitSoldierCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.upgradeUnitSoldier(x, y);
		}
	}

	private static class UpgradeUnitInfantryCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7444476144425981205L;

		private UpgradeUnitInfantryCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.upgradeUnitInfantry(x, y);
		}
	}

	private static class UpgradeUnitKnightCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -566497323829082815L;

		private UpgradeUnitKnightCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.upgradeUnitKnight(x, y);
		}
	}

	private static class UpgradeVillageTownCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8383246949202504034L;

		private UpgradeVillageTownCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.upgradeVillageTown(x, y);
		}
	}

	private static class UpgradeVillageFortCommand extends SingleTileGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6713037942187272726L;

		private UpgradeVillageFortCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.upgradeVillageFort(x, y);
		}
	}

	private static abstract class SingleTileGameCommand extends AbstractGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6257373390683253415L;
		protected int x;
		protected int y;

		private SingleTileGameCommand(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public final void execute() {
			doExecute();
		}

		protected abstract void doExecute();
	}
}
