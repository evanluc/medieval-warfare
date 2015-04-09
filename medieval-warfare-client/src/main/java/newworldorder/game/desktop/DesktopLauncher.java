package newworldorder.game.desktop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.game.MedievalWarfareGame;
import newworldorder.client.model.ModelController;

public class DesktopLauncher {
	public DesktopLauncher() {
		List<String> players = new ArrayList<String>();
		ModelController model = ModelController.getInstance();

		players.add("dummy username");
		players.add("dummy username2");

		GameInfo info = new GameInfo(players, "dummy-exchange");

		model.newGame("dummy username", info.getPlayers(), null, "assets/maps/seaside-skirmish.mwm");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.height = 850;
		config.width = 1064;
		MedievalWarfareGame game = new MedievalWarfareGame();
		new LwjglApplication(game, config);
	}

	public DesktopLauncher(String username, GameInfo info) {
		ModelController model = ModelController.getInstance();

		model.newGame(username, info.getPlayers(), info.getGameExchange(), "assets/maps/seaside-skirmish.mwm");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = true;
//		config.height = 850;
//		config.width = 1064;
		final MedievalWarfareGame game = new MedievalWarfareGame();
		//new LwjglApplication(game, config);
		new LwjglApplication(game);
	}

	// public DesktopLauncher() {
	// LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	// cfg.title = "Medieval Warfare - By newWorldOrder();";
	// // cfg.resizable = false;
	// cfg.height = 744;
	// cfg.width = 839;
	// // cfg.fullscreen = true;
	// new LwjglApplication(new MedievalWarfareGame(), cfg);
	// }

	public static void main(String[] args) {
		new DesktopLauncher();
	}
}
