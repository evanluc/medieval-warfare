package newworldorder.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;

public class MedievalWarfareGame extends Game {
	public TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	SpriteBatch sb;
	Sprite sprite;
	Texture texture;
	OrthographicCamera camera;

	@Override
	public void create() {

		tiledMap = new TmxMapLoader().load("./map/biggerTiles.tmx");
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		tiledMapRenderer = new HexagonalTiledMapRenderer(tiledMap);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);

		TiledMapStage stage = new TiledMapStage(tiledMap);
		this.setScreen(new GameScreen(this, tiledMapRenderer, stage, camera));

	}

	@Override
	public void render() {

		super.render();
	}
}
