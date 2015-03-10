package newworldorder.game.command;

import newworldorder.common.network.command.Command;
import newworldorder.game.model.GameEngine;

public interface IGameCommand extends Command {
	public void setGameEngine(GameEngine engine);
}
