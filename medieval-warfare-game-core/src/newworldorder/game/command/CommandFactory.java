package newworldorder.game.command;

import newworldorder.game.driver.UIActionType;
import newworldorder.game.model.GameEngine;
import newworldorder.game.model.Tile;
import newworldorder.game.model.Unit;
import newworldorder.game.model.UnitType;
import newworldorder.game.model.Village;
import newworldorder.game.model.VillageType;

public class CommandFactory {
	
	public static IGameCommand createCommand(UIActionType action) {
		return new EndTurnCommand();
	}
	
	public static IGameCommand createCommand(UIActionType action, int x, int y) {
		switch (action) {
		case BUILDROAD:
			return new BuildRoadCommand(x, y);
		case BUILDTOWER:
			return new BuildTowerCommand(x, y);
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
		default:
			return null; // TODO throw exception instead
		}
	}
	
	public static IGameCommand createCommand(UIActionType action, int x1, int y1, int x2, int y2) {
		return new MoveUnitCommand(x1, y1, x2, y2);
	}
	
	private static class EndTurnCommand implements IGameCommand {
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
	
	private static class MoveUnitCommand implements IGameCommand {
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
			Tile t1 = engine.getGameState().getMap().getTile(x1, y1);
			Tile t2 = engine.getGameState().getMap().getTile(x2, y2);
			Unit u = t1.getUnit();
			if (u != null)
				engine.moveUnit(u, t2);
		}

		@Override
		public void setGameEngine(GameEngine engine) {
			this.engine = engine;
		}
		
	}

	private static class BuildRoadCommand extends SingleTileGameCommand {
		private BuildRoadCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			Unit u = t.getUnit();
			if (u != null)
				engine.buildRoad(u);
		}
	}

	private static class BuildTowerCommand extends SingleTileGameCommand {
		private BuildTowerCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			engine.buildTower(t);
		}
	}

	private static class CultivateMeadowCommand extends SingleTileGameCommand {
		private CultivateMeadowCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			Unit u = t.getUnit();
			if (u != null)
				engine.cultivateMeadow(u);
		}
	}

	private static class UpgradeUnitSoldierCommand extends SingleTileGameCommand {
		private UpgradeUnitSoldierCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			Unit u = t.getUnit();
			if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.SOLDIER))
				engine.upgradeUnit(u, UnitType.SOLDIER);
		}
	}

	private static class UpgradeUnitInfantryCommand extends SingleTileGameCommand {
		private UpgradeUnitInfantryCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			Unit u = t.getUnit();
			if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.INFANTRY))
				engine.upgradeUnit(u, UnitType.INFANTRY);
		}
	}

	private static class UpgradeUnitKnightCommand extends SingleTileGameCommand {
		private UpgradeUnitKnightCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			Unit u = t.getUnit();
			if (u != null && Unit.unitLevel(u.getUnitType()) < Unit.unitLevel(UnitType.KNIGHT))
				engine.upgradeUnit(u, UnitType.KNIGHT);
		}
	}

	private static class UpgradeVillageTownCommand extends SingleTileGameCommand {
		private UpgradeVillageTownCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			Village v = t.getVillage();
			if (v != null && Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.TOWN))
				engine.upgradeVillage(v, VillageType.TOWN);
		}
	}

	private static class UpgradeVillageFortCommand extends SingleTileGameCommand {
		private UpgradeVillageFortCommand(int x, int y) {
			super(x, y);
		}

		@Override
		public void doExecute() {
			Village v = t.getVillage();
			if (v != null && Village.villageLevel(v.getVillageType()) < Village.villageLevel(VillageType.FORT))
				engine.upgradeVillage(v, VillageType.FORT);
		}
	}

	private static abstract class SingleTileGameCommand implements IGameCommand {
		protected GameEngine engine;
		protected Tile t;
		private int x, y;

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
			t = engine.getGameState().getMap().getTile(x, y);
			doExecute();
		}

		protected abstract void doExecute();
	}
}
