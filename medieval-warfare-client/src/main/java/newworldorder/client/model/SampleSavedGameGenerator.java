package newworldorder.client.model;

import java.util.ArrayList;
import java.util.List;

public class SampleSavedGameGenerator {

	private static final String mappath = "assets/maps/seaside-skirmish.mwm";
	private static final String savepath = "assets/saves/sample-save-game.mwg";

	public static void main(String[] args) {
		ModelController model = ModelController.getInstance();
		List<String> players = new ArrayList<String>();
		players.add("Bob");
		players.add("Tristan");
		players.add("Sam");

		System.out.println("Attempt creating new game");

		model.newGame("Bob", players, mappath);

		System.out.println("Created new game");

		model.saveGame(savepath);

		System.out.println("Saved game");
	}

}
