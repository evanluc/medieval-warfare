package newworldorder.client.model;

import java.util.ArrayList;
import java.util.List;

public class SampleSavedGameGenerator {

	private static final String mappath = "assets/maps/half-moon-bay.mwm";
	private static final String savepath = "assets/saves/half-moon-bay-save-game.mwg";

	public static void main(String[] args) {
		ModelController model = ModelController.getInstance();
		List<String> players = new ArrayList<String>();
		players.add("david");
		players.add("grady");

		System.out.println("Attempt creating new game");

		model.newGame("david", players, null, mappath);

		System.out.println("Created new game");

		model.saveGame(savepath);

		System.out.println("Saved game");
	}

}
