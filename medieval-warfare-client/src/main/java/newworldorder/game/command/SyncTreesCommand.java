package newworldorder.game.command;

import java.util.List;

import newworldorder.client.model.GameEngine;

public class SyncTreesCommand implements IGameCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3297669564546905111L;
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
