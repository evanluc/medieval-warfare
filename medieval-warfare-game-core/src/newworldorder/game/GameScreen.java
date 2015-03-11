package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {
	// private Stage stage = new Stage();
	private TiledMapRenderer tiledMapRenderer;
	private OrthographicCamera camera;
	private TiledMapStage stage;
	private MedievalWarfareGame game;
	private HUD hud;

	public GameScreen(final MedievalWarfareGame game,
			TiledMapRenderer tiledMapRenderer, TiledMapStage stage,
			OrthographicCamera camera) {
		this.tiledMapRenderer = tiledMapRenderer;
		this.camera = camera;
		this.stage = stage;
		this.game = game;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		((TiledMapStage) stage).tiledMapRenderUpdate(game.getModel()
				.getUpdatedTiles());

		// System.out.println(game.getModel().getUpdatedTiles());
		stage.getViewport().setCamera(camera);

		float height = stage.getHeight();
		final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		hud = new HUD("HUD", skin, game.getModel().getCurrentPlayerTurn(), game
				.getModel().getTurnNumber(), height);
		hud.setWidth(150);
		hud.setHeight(150);
		hud.setPosition(35, height);

		stage.addActor(hud);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		((TiledMapStage) stage).tiledMapRenderUpdate(game.getModel().getUpdatedTiles());
		/*if (game.getModel().getCurrentPlayerTurn() != null) {
			hud.setCurrentUsername(game.getModel().getCurrentPlayerTurn());
			hud.setCurrentTurn(game.getModel().getTurnNumber());
		}*/
		stage.draw();
		move(delta);

	}

	public void move(float delta) {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			camera.translate(-3, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			camera.translate(3, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			camera.translate(0, 3);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			camera.translate(0, -3);
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
