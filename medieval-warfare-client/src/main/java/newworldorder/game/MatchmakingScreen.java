package newworldorder.game;

import newworldorder.client.controller.ClientController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;

public class MatchmakingScreen implements Screen {
	
	OrthographicCamera camera;
	Stage stage;
	SpriteBatch batch;
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	GameScreen gameScreen;
	MedievalWarfareGame thisGame;
	
	Sprite sprite;

	public MatchmakingScreen() {
		super();
		
	}

	@Override
	public void show() {
		Gdx.graphics.setDisplayMode(1064, 755, false);

		batch = new SpriteBatch();
		Texture texture = new Texture(Gdx.files.internal("./images/background.jpg"));
		TextureRegion region = new TextureRegion(texture);
		sprite = new Sprite(region);
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		
		stage = new Stage();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());		
		stage.getViewport().setCamera(camera);
		Gdx.input.setInputProcessor(stage);
		
		TextButton loginButton = new TextButton("Load Game", skin);
		loginButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}//end of click listener
		});
		
		final Node login = new Node(loginButton);
		
		final Tree tree = new Tree(skin);
		
		tree.add(login);
		
		final SelectBox<String> mapSelect = new SelectBox<String>(skin);
		Array<String> items = new Array<String>();
		items.add("Seaside Skirmish");
		items.add("The Dark Forest");
		items.add("Half-Moon Bay");
		mapSelect.setItems(items);
		
		tree.add(new Node(mapSelect));
		
		Table table = new Table();
		table.add(tree).fill().expand();
		
		
		
		stage.addActor(table);
		
		table.setPosition(stage.getCamera().position.x - table.getWidth() / 2, stage.getCamera().position.y - table.getHeight()
				/ 2);
		
		final SelectBox<String> onlinePlayerSelect = new SelectBox<String>(skin);
		stage.addActor(onlinePlayerSelect);
		onlinePlayerSelect.setItems(items);
		onlinePlayerSelect.setHeight(40);
		onlinePlayerSelect.setWidth(20);
		onlinePlayerSelect.setPosition(10, 10);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor( 1f, 1f, 1f, 1f );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );	  
		batch.begin();
		sprite.draw(batch);
		batch.end();
		stage.act();
		stage.draw();
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
		this.dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		skin.dispose();
	}

}
