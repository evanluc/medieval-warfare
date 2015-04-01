package newworldorder.game.command;

import java.io.Serializable;

import newworldorder.common.network.command.Command;
import newworldorder.client.model.GameEngine;

public interface IGameCommand extends Command, Serializable {
	public void execute(GameEngine engine);
}
