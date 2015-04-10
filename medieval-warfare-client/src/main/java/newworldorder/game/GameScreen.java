package newworldorder.game;

import newworldorder.client.controller.ClientController;
import newworldorder.client.model.ModelController;
import newworldorder.client.networking.CommandFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


public class GameScreen implements Screen {
	// private Stage stage = new Stage();
	private TiledMapRenderer tiledMapRenderer;
	private TiledMapStage stage;
	private TiledMap tiledMap;
	private UIStage UIstage;
	final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	private OrthographicCamera camera;
	private boolean hasOpenedDialog = false;
	MedievalWarfareGame thisGame;
	
	public GameScreen(MedievalWarfareGame thisGame) {
		super();
		this.thisGame = thisGame;
	}
	public UIStage getUIStage(){
		return this.UIstage;
	}

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
		if (ModelController.getInstance().isLocalPlayersTurn()){
			if(stage.getCurrentlyOutlined()!= null) UIstage.yourTurnInfoRenderUpdate(stage.getCurrentlyOutlined());
			UIstage.currentTurnRenderUpdate();
		}
		else{
			if(stage.getCurrentlyOutlined() != null )UIstage.notTurnInfoRenderUpdate(stage.getCurrentlyOutlined());
			UIstage.notTurnRenderUpdate();
		}
		UIstage.draw();
		if(this.getUIStage().getHUD().isWantsToLeave()){
			thisGame.setMatchmakingScreen();
			CommandFactory.createDisconnectCommand();
			ModelController.getInstance().getEngine().setPlayerHasDisconnected(false);
			ClientController.getInstance().login(ClientController.getInstance().username,
					ClientController.getInstance().password);
		}
		if(ModelController.getInstance().getEngine().isPlayerHasDisconnected() && !hasOpenedDialog){
			ModelController.getInstance().getEngine().setPlayerHasDisconnected(false);
			hasOpenedDialog = true;
			Dialog confirmDialog = new Dialog("Player has disconnected, please save game or leave", skin){
				@Override
				protected void result (Object object) {
					hasOpenedDialog = false;
					this.hide();	
				}
			};
			confirmDialog.setModal(true);
			Window saveGameWindow = new Window("Please provide a save game file name", skin);
			saveGameWindow.setMovable(false);
			List<String> saveGameList = new List<>(skin);
			FileHandle[] files = Gdx.files.local("assets/saves/").list();
			String[] saveFiles = new String[files.length];
			for(int i = 0; i < files.length; i++) {
				saveFiles[i] = files[i].name();
			}
			Array<String> p = new Array<>(saveFiles);
			saveGameList.setItems(p);
			ScrollPane saveGameListPane = new ScrollPane(saveGameList, skin);
			saveGameWindow.add(saveGameListPane).expand().fill().row();
			confirmDialog.add(saveGameWindow).expandY().fill().pad(20).row();
			TextField saveFileNameTextField = new TextField("", skin);
			saveGameWindow.add(saveFileNameTextField).expand().fill().row();
			TextButton confirmButton = new TextButton("Save Game", skin);
			confirmButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					if (!saveFileNameTextField.getText().isEmpty()) {
						thisGame.setMatchmakingScreen();
						ModelController.getInstance().saveGame("assets/saves/"+saveFileNameTextField.getText()+".mwg");
						CommandFactory.setHasNetworking(false);
						ModelController.getInstance().clearGameState();
						hasOpenedDialog = false;	
						ClientController.getInstance().login(ClientController.getInstance().username,
								ClientController.getInstance().password);
					}
					return true;
				}
			});
			saveGameListPane.addListener(new ClickListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (saveGameList.getSelected() != null) saveFileNameTextField.setText(saveGameList.getSelected().substring(0, saveGameList.getSelected().length()-4));
				
				return false;
				}
			});
			confirmDialog.button(confirmButton).bottom().row();
			TextButton closeButton = new TextButton("Close", skin);
			closeButton.addListener(new ClickListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					thisGame.setMatchmakingScreen();
					CommandFactory.setHasNetworking(false);
					ModelController.getInstance().clearGameState();
					hasOpenedDialog = false;
					ClientController.getInstance().login(ClientController.getInstance().username,
							ClientController.getInstance().password);
					return true;
				}
			});
			confirmDialog.button(closeButton).bottom().row();
			
			confirmDialog.show(stage);
		}
		
		if(ModelController.getInstance().isGameOver() && !hasOpenedDialog){
			hasOpenedDialog = true;
			Dialog confirmDialog = new Dialog("Click Close to return to matchmaking", skin){
				@Override
				protected void result (Object object) {
					hasOpenedDialog = false;
					this.hide();	
				}
			};
			confirmDialog.setModal(true);
			Window saveGameWindow = new Window("The game has ended!", skin);

			TextButton closeButton = new TextButton("Close", skin);
			closeButton.addListener(new ClickListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					thisGame.setMatchmakingScreen();
					CommandFactory.setHasNetworking(false);
					ModelController.getInstance().clearGameState();
					hasOpenedDialog = false;
					ClientController.getInstance().login(ClientController.getInstance().username,
							ClientController.getInstance().password);
					return true;
				}
			});
			confirmDialog.button(closeButton);
			
			confirmDialog.show(stage);
		}
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
		stage.dispose();
		UIstage.dispose();
	}

	@Override
	public void dispose() {
//		stage.dispose();
//		tiledMap.dispose();
//		UIstage.dispose();
//		skin.dispose();
	}

}
