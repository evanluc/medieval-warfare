package newworldorder.game;

import newworldorder.client.model.ModelController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;


public class GameScreen implements Screen {
	// private Stage stage = new Stage();
	private TiledMapRenderer tiledMapRenderer;
	private TiledMapStage stage;
	private TiledMap tiledMap;
	private HUD hud;
	private UIStage UIstage;
	final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	private OrthographicCamera camera;


	@Override
	public void show() {
		Gdx.graphics.setDisplayMode(1064, 755, false);
		this.tiledMap = new TmxMapLoader().load("./map/blankMap.tmx");
		this.tiledMapRenderer = new HexagonalTiledMapRenderer(tiledMap);
		this.UIstage = new UIStage(skin);
		this.stage = new TiledMapStage(tiledMap,ModelController.getInstance(),UIstage);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);		
		
	
		stage.tiledMapRenderUpdate(ModelController.getInstance()
				.getUpdatedTiles());

		stage.getViewport().setCamera(camera);

			
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(UIstage);

		inputMultiplexer.addProcessor(stage);


		Gdx.input.setInputProcessor(inputMultiplexer);

		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		stage.tiledMapRenderUpdate(ModelController.getInstance().getUpdatedTiles());
		stage.act();
		stage.draw();
		move(delta);
		UIstage.act();
		if (ModelController.getInstance().isLocalPlayersTurn()) UIstage.currentTurnRenderUpdate();
		else UIstage.notTurnRenderUpdate();
		UIstage.draw();

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
		this.dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		tiledMap.dispose();
		UIstage.dispose();
		skin.dispose();
	}

}
