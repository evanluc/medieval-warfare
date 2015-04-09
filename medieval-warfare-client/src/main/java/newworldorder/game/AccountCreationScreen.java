package newworldorder.game;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import newworldorder.client.controller.ClientController;
import newworldorder.client.controller.IController;

public class AccountCreationScreen implements Screen{

		
		private IController controller;
		
		OrthographicCamera camera;
		Stage stage;
		SpriteBatch batch;
		private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		MedievalWarfareGame thisGame;

		Sprite sprite;

		public AccountCreationScreen(MedievalWarfareGame thisGame) {
			super();
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
			usernameField.setMessageText("Username");
			TextField passwordField = new TextField("", skin);
			passwordField.setPasswordMode(true);
			passwordField.setPasswordCharacter('*');
			passwordField.setMessageText("Password");
			TextField confirmPasswordField = new TextField("", skin);
			confirmPasswordField.setPasswordMode(true);
			confirmPasswordField.setPasswordCharacter('*');
			confirmPasswordField.setMessageText("Confirm Password");
			//adding username and password nodes
//			final Node usernameNode= new Node (usernameField);
//			final Node passwordNode = new Node(passwordField);
//			final Node confirmPasswordNode = new Node(confirmPasswordField);

			/*creating login and button tables */
			Table table = new Table();

//			final Tree tree = new Tree(skin);


			TextButton enterButton = new TextButton("Enter", skin);
			
			TextButton backButton = new TextButton("Back", skin);

			
//			final Node enter = new Node(enterButton);
//			
//			final Node back = new Node(backButton);
			
			table.add(usernameField).row();
			table.add(passwordField).row();
			table.add(confirmPasswordField).row();
			table.add(enterButton).row();
			table.add(backButton).row();
			stage.addActor(table);
			table.setPosition(stage.getCamera().position.x - table.getWidth() / 2, stage.getCamera().position.y - table.getHeight()
					/ 2);
			
			
			enterButton.addListener(new ClickListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					
					String username = usernameField.getText();
					String password = passwordField.getText();
					String confirmPassword = confirmPasswordField.getText();
					if(!password.equals(confirmPassword))
					{
						new Dialog("Error", skin){
							@Override
							protected void result (Object object){
								this.hide();
							}
						}.text("Please ensure that your passwords match!").button("OK").show(stage);
						return false;
					}
					else{
						//login check
						controller = ClientController.getInstance();
						if (controller.newAccount(username, password)){
							// switch to matchmaking screen
							controller.login(username, password);
							thisGame.setMatchmakingScreen();
						} 
						else{ 
							new Dialog("Error", skin){
								@Override
								protected void result (Object object) {
									this.hide();	
								}
							}.text("Please enter a valid \n username or password").button("OK").show(stage);
						} //end of no password case
						return true;
					}//end of click listener
				}	
			});
			
			backButton.addListener(new ClickListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					thisGame.setLoginScreen();
					return true;
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
			this.dispose();
		}

		@Override
		public void dispose() {
//			stage.dispose();
//			batch.dispose();
//			skin.dispose();
		}


}

