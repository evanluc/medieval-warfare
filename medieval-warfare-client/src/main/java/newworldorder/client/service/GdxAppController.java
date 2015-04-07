package newworldorder.client.service;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import newworldorder.client.model.ModelController;
import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.service.IGameLauncher;
import newworldorder.game.MedievalWarfareGame;

public class GdxAppController implements IGameLauncher {
	private String username;
	private static ModelController model;
	private static final String mappath = "assets/maps/seaside-skirmish.mwm"; //TODO This needs to be removed eventually
	private static boolean gameRunning = false;

	@Override
	public void launchGame(GameInfo info) {
		model = ModelController.getInstance();
		model.newGame(username, info.getPlayers(), info.getGameExchange(), mappath);
	}
	
	public static void showGdxApp() {
		if (!gameRunning) {
			gameRunning = true;
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.resizable = false;
			config.height = 850;
			config.width = 1064;
			new LwjglApplication(new MedievalWarfareGame(), config);
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
