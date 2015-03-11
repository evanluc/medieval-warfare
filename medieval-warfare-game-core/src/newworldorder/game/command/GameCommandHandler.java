package newworldorder.game.command;

import newworldorder.game.model.GameEngine;

public class GameCommandHandler {
	private final GameEngine engine;

	public GameCommandHandler(GameEngine engine) {
		super();
		this.engine = engine;
	}

	public void handle(IGameCommand command) {
		System.out.println("Game command received.");
		command.setGameEngine(engine);
		System.out.println("Game engine has been set in command.");
		command.execute();
		System.out.println("Command executed successfully.");
	}
}
