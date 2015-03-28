package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

	Stage stage;
	SpriteBatch batch;
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	private TextField textField;
	GameScreen gameScreen;
	MedievalWarfareGame thisGame;

	Sprite sprite;

	public LoginScreen(GameScreen gameScreen, MedievalWarfareGame thisGame){
		textField = new TextField("Enter username", skin);
		this.gameScreen = gameScreen;
		this.thisGame = thisGame;
	}


	@Override
	public void show() {
		stage = new Stage();

		batch = new SpriteBatch();
		Texture texture = new Texture(Gdx.files.internal("./images/background.jpg"));
		TextureRegion region = new TextureRegion(texture);
		sprite = new Sprite(region);
		sprite.setSize(1f,
				1f * sprite.getHeight() / sprite.getWidth() );
		Gdx.input.setInputProcessor(stage);


		//password field
		TextField usernameField = new TextField("Enter Username", skin);
		TextField passwordField = new TextField("Enter password", skin);
		passwordField.setPasswordMode(true);
		//adding username and password nodes
		final Node usernameNode= new Node (usernameField);
		final Node passwordNode = new Node(passwordField);

		/*creating login and button tables */
		Table table = new Table();
		table.setFillParent(true);

		final Tree tree = new Tree(skin);


		TextButton loginButton = new TextButton("Login", skin);

		
		final Node login = new Node(loginButton);

		tree.add(usernameNode);
		tree.add(passwordNode);
		tree.add(login);
		table.add(tree).fill().expand();	

//		table.setPosition(stage.getCamera().position.x - table.getWidth()/2, stage.getCamera().position.y - table.getHeight()/2);	
		//table.setPosition(stage.getWidth() - table.getWidth() / 2, stage.getHeight() - table.getHeight()/2);
		stage.addActor(table);

		
		loginButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

				//login check
				if ((passwordField.getText().equals(validPassword) == false || (usernameField.getText().equals(validUsername)) == false)){
					new Dialog("Error", skin){
						protected void result (Object object) {
							this.hide();	
						}
					}.text("Please enter a valid \n username or password").button("OK").show(stage);
				} //end of no password case

				else{ 
					thisGame.setScreen(gameScreen);
				}
			}//end of click listener
		});



		
	//	;

		//just to compare
		/*
		notTurnWindow.setPosition(stage.getCamera().position.x - notTurnWindow.getWidth() / 2, stage.getCamera().position.y - notTurnWindow.getHeight()
				/ 2);
		*/
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
