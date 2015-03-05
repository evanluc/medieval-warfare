package newworldorder.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import newworldorder.game.MedievalWarfareGame;

public class DesktopLauncher {
	public DesktopLauncher() {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Medieval Warfare - By newWorldOrder();";
		cfg.resizable = false;
		cfg.height = 744;
		cfg.width = 839;
		new LwjglApplication(new MedievalWarfareGame(), cfg);
	}
}
