package newworldorder.game.command;

import java.util.List;

import newworldorder.client.shared.UIActionType;
import newworldorder.client.model.GameEngine;

public class CommandFactory {
	
	public static IGameCommand createEndTurnCommand() {
		return new EndTurnCommand();
	}
	
	public static IGameCommand createSyncTreesCommand(List<Integer> newTrees) {
		return new SyncTreesCommand(newTrees);
	}
	
	public static IGameCommand createPlaceVillageCommand(int hashcode) {
		return new PlaceVillageCommand(hashcode);
	}

	public static IGameCommand createCommand(UIActionType action, int x, int y) {
		switch (action) {
		case BUILDROAD:
			return new BuildRoadCommand(x, y);
		case BUILDTOWER:
			return new BuildTowerCommand(x, y);
		case BUILDUNITINFANTRY:
			return new BuildUnitInfantryCommand(x, y);
		case BUILDUNITKNIGHT:
			return new BuildUnitKnightCommand(x, y);
		case BUILDUNITPEASANT:
			return new BuildUnitPeasantCommand(x, y);
		case BUILDUNITSOLDIER:
			return new BuildUnitSoldierCommand(x, y);
		case CULTIVATEMEADOW:
			return new CultivateMeadowCommand(x, y);
		case UPGRADEUNITSOLDIER:
			return new UpgradeUnitSoldierCommand(x, y);
		case UPGRADEUNITINFANTRY:
			return new UpgradeUnitInfantryCommand(x, y);
		case UPGRADEUNITKNIGHT:
			return new UpgradeUnitKnightCommand(x, y);
		case UPGRADEVILLAGETOWN:
			return new UpgradeVillageTownCommand(x, y);
		case UPGRADEVILLAGEFORT:
			return new UpgradeVillageFortCommand(x, y);
		case ENDTURN:
			return new EndTurnCommand();
		default:
			return null; // TODO throw exception instead
		}
	}

	public static IGameCommand createCommand(UIActionType action, int x1, int y1, int x2, int y2) {
		return new MoveUnitCommand(x1, y1, x2, y2);
	}
	
	private static class EndTurnCommand implements IGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2144057378224658285L;
		private GameEngine engine;

		@Override
		public void execute() {
			engine.endTurn();
		}

		@Override
		public void setGameEngine(GameEngine engine) {
			this.engine = engine;
		}
	}
	
	private static class SyncTreesCommand implements IGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6605499829717076737L;
		private GameEngine engine;
		private final List<Integer> newTrees;
		
		public SyncTreesCommand(List<Integer> newTrees) {
			this.newTrees = newTrees;
		}
		
		@Override
		public void execute() {
			engine.placeTreesAt(newTrees);
		}

		@Override
		public void setGameEngine(GameEngine engine) {
			this.engine = engine;
		}
	}

	private static class PlaceVillageCommand implements IGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2715015379925412885L;
		private GameEngine engine;
		int hashcode;
		
		public PlaceVillageCommand(int hashcode) {
			this.hashcode = hashcode;
		}

		@Override
		public void execute() {
			engine.placeVillageAt(hashcode);
		}

		@Override
		public void setGameEngine(GameEngine engine) {
			this.engine = engine;
		}
	}

	private static class MoveUnitCommand implements IGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4408577083911356294L;
		private GameEngine engine;
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

		@Override
		public void setGameEngine(GameEngine engine) {
			this.engine = engine;
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

	private static abstract class SingleTileGameCommand implements IGameCommand {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6257373390683253415L;
		protected GameEngine engine;
		protected int x;
		protected int y;

		private SingleTileGameCommand(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void setGameEngine(GameEngine engine) {
			this.engine = engine;
		}

		@Override
		public final void execute() {
			doExecute();
		}

		protected abstract void doExecute();
	}
}
