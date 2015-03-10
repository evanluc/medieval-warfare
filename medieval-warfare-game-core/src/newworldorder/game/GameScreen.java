package newworldorder.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	// private Stage stage = new Stage();
	private TiledMapRenderer tiledMapRenderer;
	private OrthographicCamera camera;
	private TiledMapStage stage;
	private Game game;
	private Viewport viewport;

	public GameScreen(final MedievalWarfareGame game, TiledMapRenderer tiledMapRenderer, TiledMapStage stage,
			OrthographicCamera camera) {
		this.tiledMapRenderer = tiledMapRenderer;
		this.camera = camera;
		this.stage = stage;
		this.game = game;
		viewport = new ExtendViewport(839, 744, camera);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
