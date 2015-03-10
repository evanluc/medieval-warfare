package newworldorder.game.driver;

import java.util.ArrayList;
import java.util.List;

import newworldorder.common.matchmaking.GameInfo;

public class SampleSavedGameGenerator {

	private static final String mappath = "maps/seaside-skirmish.mwm";
	private static final String savepath = "saves/sample-save-game.mwg";

	public static void main(String[] args) {
		IModelCommunicator model = ModelManager.getInstance();
		List<String> playerIds = new ArrayList<String>();
		playerIds.add("1001");
		playerIds.add("1234");
		playerIds.add("666");
		GameInfo info = new GameInfo(playerIds, "dummy-exchange");
		
		System.out.println("Attempt creating new game");

		model.newGame(info, mappath);
		
		System.out.println("Created new game");

		model.saveGame(savepath);
		
		System.out.println("Saved game");
	}

}
