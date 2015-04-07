package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LoginScreen implements Screen{

	//temporary password
	private String validPassword = "valid";
	//temporary userame
	private String validUsername = "valid";
	
	OrthographicCamera camera;
	Stage stage;
	SpriteBatch batch;
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	GameScreen gameScreen;
	MedievalWarfareGame thisGame;

	Sprite sprite;

	public LoginScreen(GameScreen gameScreen, MedievalWarfareGame thisGame){
		this.gameScreen = gameScreen;
		this.thisGame = thisGame;

	}


	@Override
	public void show() {
		Gdx.graphics.setDisplayMode(700, 356, false);

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


		//password field
		TextField usernameField = new TextField("", skin);
		TextField passwordField = new TextField("", skin);
		passwordField.setPasswordMode(true);
		//adding username and password nodes
		final Node usernameNode= new Node (usernameField);
		final Node passwordNode = new Node(passwordField);

		/*creating login and button tables */
		Table table = new Table();

		final Tree tree = new Tree(skin);


		TextButton loginButton = new TextButton("Login", skin);

		
		final Node login = new Node(loginButton);

		tree.add(usernameNode);
		tree.add(passwordNode);
		tree.add(login);
		table.add(tree).fill().expand();						
		stage.addActor(table);
		table.setPosition(stage.getCamera().position.x - table.getWidth() / 2, stage.getCamera().position.y - table.getHeight()
				/ 2);
		
		
		loginButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				//login check
				if ((passwordField.getText().equals(validPassword) == false || (usernameField.getText().equals(validUsername)) == false)){
					new Dialog("Error", skin){
						protected void result (Object object) {
							this.hide();	
						}
					}.text("Please enter a valid \n username or password").button("OK").show(stage);
				} //end of no password case

				else{ 
					//call networking stuff
					thisGame.setScreen(gameScreen);
				}
				return false;
			}//end of click listener
		});

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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}


}
