package newworldorder.client.service;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import newworldorder.client.model.ModelController;
import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.service.IGameLauncher;
import newworldorder.game.MedievalWarfareGame;

public class GdxAppController implements IGameLauncher {
	private static GdxAppController instance = null;
	private String username;
	private static ModelController model;
	private static final String mappath = "assets/maps/seaside-skirmish.mwm"; //TODO This needs to be removed eventually
	private static MedievalWarfareGame mwg;
	private static LwjglApplication gdxApp;
	
	private GdxAppController() {
		
	}

	@Override
	public void launchGame(GameInfo info) {
		model = ModelController.getInstance();
		model.setupNetworking(info.getGameExchange());
		model.newGame(username, info.getPlayers());
	}
	
	public static GdxAppController getInstance() {
		if (instance == null) {
			instance = new GdxAppController();
		}
		return instance;
	}
	
	public static void showGdxApp() {
		if (gdxApp == null) {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.resizable = false;
			config.height = 850;
			config.width = 1064;
			mwg = new MedievalWarfareGame();
			gdxApp = new LwjglApplication(mwg, config);
		}
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}
}
