package newworldorder.game.command;

import newworldorder.game.model.GameEngine;

public class GameCommandHandler {
	private final GameEngine engine;

	public GameCommandHandler(GameEngine engine) {
		super();
		this.engine = engine;
	}

	public void handle(IGameCommand command) {
		command.setGameEngine(engine);
		command.execute();
	}
}
