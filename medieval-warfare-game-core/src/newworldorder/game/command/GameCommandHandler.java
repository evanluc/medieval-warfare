package newworldorder.game.command;

import newworldorder.game.model.GameEngine;

public class GameCommandHandler {
	private final GameEngine engine;

	public GameCommandHandler(GameEngine engine) {
		super();
		this.engine = engine;
	}

	public void handle(IGameCommand command) {
		System.out.println("Received command: " + command.getClass().getName());
		command.setGameEngine(engine);
		command.execute();
	}
}
