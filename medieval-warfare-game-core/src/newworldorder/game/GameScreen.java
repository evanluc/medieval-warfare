package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

public class GameScreen implements Screen {
	//private Stage stage = new Stage();
	private TiledMapRenderer tiledMapRenderer;
	private OrthographicCamera camera;
	private TiledMapStage stage;
	private MedievalWarfareGame game;

	public GameScreen(final MedievalWarfareGame game, TiledMapRenderer tiledMapRenderer, TiledMapStage stage, OrthographicCamera camera){
		this.tiledMapRenderer = tiledMapRenderer; 
		this.camera = camera;
		this.stage = stage; 
		this.game = game; 
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);	
		((TiledMapStage) stage).tiledMapRenderUpdate(game.getModel().getUpdatedTiles());
		System.out.println(game.getModel().getUpdatedTiles());
		stage.getViewport().setCamera(camera);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		((TiledMapStage) stage).tiledMapRenderUpdate(game.getModel().getUpdatedTiles());
		stage.draw();
		move(delta);

	}

	public void move(float delta){
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
            camera.translate(-3,0);		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            camera.translate(3,0);		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			camera.translate(0,3);
		}    
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			camera.translate(0,-3);
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
