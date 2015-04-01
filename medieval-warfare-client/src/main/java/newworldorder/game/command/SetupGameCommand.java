package newworldorder.game.command;

import newworldorder.client.model.ModelController;
//import newworldorder.game.model.Game;
import newworldorder.client.model.GameEngine;

public class SetupGameCommand implements IGameCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6384201347176706444L;
	private final Object gameState;
	private GameEngine engine;

	public SetupGameCommand(Object gameState) {
		super();
		this.gameState = gameState;
	}

	@Override
	public void execute() {
		engine.setGameState(gameState);
	}

	@Override
	public void setGameEngine(GameEngine engine) {
		this.engine = engine;
	}

}
