package newworldorder.game.command;

import newworldorder.game.model.Game;
import newworldorder.game.model.GameEngine;

public class SetupGameCommand implements IGameCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6384201347176706444L;
	private final Game gameState;
	private GameEngine engine;

	public SetupGameCommand(Game gameState) {
		super();
		this.gameState = gameState;
	}

	@Override
	public void execute() {
		System.out.println("Received initial game state");
		engine.setGameState(gameState);
	}

	@Override
	public void setGameEngine(GameEngine engine) {
		this.engine = engine;
	}

}
