package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen implements Screen {
	// private Stage stage = new Stage();
	private TiledMapRenderer tiledMapRenderer;
	private TiledMapStage stage;
	private MedievalWarfareGame game;
	private HUD hud;
	private Stage UIstage;
	final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	private OrthographicCamera camera;

	public GameScreen(final MedievalWarfareGame game,
			TiledMapRenderer tiledMapRenderer, TiledMapStage stage, OrthographicCamera camera2) {
		this.tiledMapRenderer = tiledMapRenderer;
		this.stage = stage;
		this.game = game;
		this.UIstage = new UIStage(skin);

	}

	@Override
	public void show() {
		Gdx.graphics.setDisplayMode(1064, 850, false);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);		
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		//Gdx.input.setInputProcessor(stage);
		stage.tiledMapRenderUpdate(game.getModel()
				.getUpdatedTiles());

		// System.out.println(game.getModel().getUpdatedTiles());
		stage.getViewport().setCamera(camera);

		float height = stage.getHeight();
		hud = new HUD("HUD", skin, game.getModel().getCurrentPlayerTurn(), game
				.getModel().getTurnNumber(), height);
		hud.setWidth(150);
		hud.setHeight(150);
		hud.setPosition(35, height);

		UIstage.addActor(hud);

		
		inputMultiplexer.addProcessor(UIstage);
		Gdx.input.setInputProcessor(inputMultiplexer);

		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		stage.tiledMapRenderUpdate(game.getModel().getUpdatedTiles());
		hud.setCurrentUsername(game.getModel().getCurrentPlayerTurn());
		hud.setCurrentTurn(game.getModel().getTurnNumber());
		stage.act();
		stage.draw();
		move(delta);
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
