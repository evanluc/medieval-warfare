package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import newworldorder.client.controller.ClientController;
import newworldorder.client.model.ModelController;
import newworldorder.client.networking.CommandFactory;
import newworldorder.common.model.Stats;

public class MatchmakingScreen implements Screen {
	
	ClientController controller = ClientController.getInstance();
	OrthographicCamera camera;
	Stage stage;
	SpriteBatch batch;
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	GameScreen gameScreen;
	MedievalWarfareGame thisGame;
	private Image mapPreviewImage;
	private boolean mapChanged = false;
	private float alpha = 0f;
	Sprite sprite;
	
	List<String> onlinePlayers;
	Array<String> mapOptionsArray;
	SelectBox<String> mapSelectBox;
	List<String> pending; 
	List<String> party;
	Dialog invite;
	int i = 100000;
	ModelController modelController = ModelController.getInstance();
	SelectBox<String> numPlayerSelect;
	
	private String loadGamePath;
	private String mapFilePath = "assets/maps/seaside-skirmish.mwm";;

	public MatchmakingScreen(MedievalWarfareGame thisGame) {
		super();
		this.thisGame = thisGame;
		this.loadGamePath = null;
		this.mapFilePath = "assets/maps/seaside-skirmish.mwm";
	}

	@Override
	public void show() {
		Gdx.graphics.setDisplayMode(1064, 655, false);

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
		
		initUI();
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
		// David changed this because he needed to load the saved game to validate
		// if the players in the party match the players in the save game so even if
		// they don't match there is still a game state and it switches. This condition
		// is pretty much equivalent and comes after the start game command is received.
		if(CommandFactory.hasNetworking()){ 
			thisGame.setGameScreen();
		}
		if (mapChanged) {
			alpha += delta;
			mapPreviewImage.setColor(1.0f, 1.0f, 1.0f, alpha);
			if (alpha >= 1.0f) {
				mapChanged = false;
				alpha = 0f;
			}
		}
		if (loadGamePath != null) {
			// disable new game options
			if (!mapOptionsArray.contains("Saved Game", false)) {
				mapOptionsArray.add("Saved Game");
				mapSelectBox.setItems(mapOptionsArray);
			}
			mapSelectBox.setSelected("Saved Game");
		}
		java.util.List<String> pendInv = controller.getNotAcceptedPlayersInParty(); 
		String[] pendingPlayers =  pendInv.toArray(new String[pendInv.size()]);
		pending.setItems(new Array<String>(pendingPlayers));
		
		java.util.List<String> inParty = controller.getAcceptedPlayersInParty(); 
		String[] partyPlayers =  inParty.toArray(new String[inParty.size()]);
		party.setItems(new Array<String>(partyPlayers));
		i++;
		if( i >= 100){
			java.util.List<String> online = controller.getOnlinePlayers();
			String[] players = online.toArray(new String[online.size()]);
			onlinePlayers.setItems(new Array<String>(players));
			i = 0;
		}
		//Makes the dialog for accepting invite if you have been invited
		if (!controller.getPlayersInParty().isEmpty() && !controller.acceptedPartyInvite() && invite == null) {
			invite = new Dialog("Party Invite", skin){
				@Override
				protected void result (Object object) {
					this.hide();	
				}
			};
			
			
			
			TextButton selectButton = new TextButton("Accept Invite", skin);
			selectButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					controller.acceptInvite();
					invite = null;
					return true;
				}
			});
			invite.button(selectButton);
			invite.show(stage);
		}
		if(controller.getPlayersInParty().isEmpty()){
			numPlayerSelect.setVisible(true);
			
		}else{
			numPlayerSelect.setVisible(false);
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
		this.dispose();
	}

	@Override
	public void dispose() {
//		stage.dispose();
//		batch.dispose();
//		skin.dispose();
		controller.logout();
	}
	
	private void initUI() {
		Window table = new Window("Matchmaking", skin);
	    table.setFillParent(true);
//	    table.setDebug(true);
	    
	    TextButton loadButton = new TextButton("Load Game", skin);
		loadButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Dialog loadGame = new Dialog("Load Game", skin){
					@Override
					protected void result (Object object) {
						this.hide();	
					}
				};
				
				Window loadGameWindow = new Window("Please select a saved game to load", skin);
				loadGameWindow.setMovable(false);
				List<String> loadGameList = new List<>(skin);
				FileHandle[] files = Gdx.files.local("assets/saves/").list();
				String[] saveFiles = new String[files.length];
				for(int i = 0; i < files.length; i++) {
					saveFiles[i] = files[i].name();
				}
				Array<String> p = new Array<>(saveFiles);
				loadGameList.setItems(p);
				ScrollPane loadGamePane = new ScrollPane(loadGameList, skin);
				loadGameWindow.add(loadGamePane).expand().fill();
				
				loadGame.add(loadGameWindow).expandY().fill().pad(20);
				TextButton selectButton = new TextButton("Load Game", skin);
				selectButton.addListener(new ClickListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						loadGamePath = loadGameList.getSelected();
						return true;
					}
				});
				loadGame.button(selectButton);
				
				loadGame.show(stage);
				return true;
			}
		});
		
		mapSelectBox = new SelectBox<>(skin);
		mapOptionsArray = new Array<>();
		mapOptionsArray.add("Seaside Skirmish");
		mapOptionsArray.add("The Dark Forest");
		mapOptionsArray.add("Half-Moon Bay");
		mapSelectBox.setItems(mapOptionsArray);
		TextureRegionDrawable[] maps = new TextureRegionDrawable[4];
		String[] mapNames = {"seaside-skirmish", "dark-forest", "half-moon-bay"};
		
		mapSelectBox.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mapChanged = true;
				mapPreviewImage.setColor(1.0f, 1.0f, 1.0f, 0.0f);
				mapPreviewImage.setDrawable(maps[mapSelectBox.getSelectedIndex()]);
				if (mapSelectBox.getSelected().equals("Saved Game")) {
					mapFilePath = null;
				} else {
					mapOptionsArray.removeValue("Saved Game", false);
					loadGamePath = null;
					mapSelectBox.setItems(mapOptionsArray);
					mapFilePath = "assets/maps/" + mapNames[mapSelectBox.getSelectedIndex()] + ".mwm";
				}
			}
			
		});
		
		numPlayerSelect = new SelectBox<>(skin);
		Array<String> nums = new Array<>();
		nums.add("Choose # players");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		numPlayerSelect.setItems(nums);
		
		table.row().width(150).pad(35, 0, 40, 0);
		table.add(loadButton).expandX().width(125);
		table.add(mapSelectBox).expandX().width(175);
		table.add(numPlayerSelect).expandX().width(155);
		table.row();
		
		Window onlineWindow = new Window("Online Players", skin);
		onlineWindow.setMovable(false);
		onlinePlayers = new List<>(skin);
		String[] players = {"player1", "player2", "player3", "player4", "player5", "player6", "player7"};
		Array<String> p = new Array<>(players);
		onlinePlayers.setItems(p);
		ScrollPane onlinePlayerPane = new ScrollPane(onlinePlayers, skin);
		onlineWindow.add(onlinePlayerPane).expand().fill();
		table.add(onlineWindow).expandY().fill().pad(20);
		
		Table middleColumn = new Table();
		
		maps[0] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("./images/seaside-skirmish.jpg"))));
		maps[1] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("./images/dark-forest.jpg"))));
		maps[2] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("./images/half-moon-bay.jpg"))));
		maps[3] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("./images/saved-game-preview.jpg"))));
		
		TextButton inviteButton = new TextButton("Invite To Party", skin);
		inviteButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				String selected = onlinePlayers.getSelected();
				if(selected != null && !selected.equals(controller.getUsername())){
					controller.invitePlayer(controller.getUsername(), selected);
				}
				return true;
			}
		});
		TextButton kickButton = new TextButton("Kick From Party", skin);
		kickButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				String selected = party.getSelected();
				if(selected != null){
					controller.kickFromParty(selected);
				}
				return true;
			}
		});
		TextButton statsButton = new TextButton("View Stats", skin);
		statsButton.addListener(new ClickListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					Dialog statisticDialog = new Dialog(onlinePlayers.getSelected()+" Statistics", skin){
						@Override
						protected void result (Object object) {
							this.hide();	
						}
					}.button("Close").show(stage);;
					Window statsWindow = new Window(onlinePlayers.getSelected(), skin);
					Stats playerStats = controller.getStatsForPlayer(onlinePlayers.getSelected());
					statsWindow.setMovable(false);
					List<String> statList = new List<>(skin);
					int playerWins = playerStats.getWins();
					int playerLosses = playerStats.getLosses();
					String wins = "Wins: " + playerWins;
					String losses = "Losses: " + playerLosses;
					String[] stats = new String[2];
					stats[0] = wins;
					stats[1] = losses;
					Array<String> p = new Array<>(stats);
					statList.setItems(p);
					ScrollPane statsPane = new ScrollPane(statList, skin);
					statsWindow.add(statsPane).expand().fill();
					statisticDialog.add(statsWindow).expandY().fill().pad(20);
					statisticDialog.show(stage);
					return true;
				}
			});
		TextButton playButton = new TextButton("Play", skin);
		playButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (loadGamePath != null) {
					modelController.loadGame(ClientController.getInstance().getLocalUsername(), "assets/saves/" + loadGamePath);

					if (modelController.validatePlayers(controller.getAcceptedPlayersInParty())) {
						controller.startPartyGame();
						while (!CommandFactory.hasNetworking()){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						modelController.distributeGameState();
					}
				} else {
					System.out.println(mapFilePath);
					modelController.setMapFilePath(mapFilePath);
					if(controller.getPlayersInParty().isEmpty()){
						try{
							controller.requestGame(Integer.parseInt((numPlayerSelect.getSelected())));
						}catch(NumberFormatException e){
							
						}
					}else{
						controller.startPartyGame();
					}
				}
				return false;
			}
		});
		middleColumn.columnDefaults(0).width(150).pad(5);
		mapPreviewImage = new Image(maps[mapSelectBox.getSelectedIndex()]);
		middleColumn.add(mapPreviewImage).size(450, 300).expand().top().pad(10, 0, 40, 0).fill();
		middleColumn.row();
		middleColumn.add(inviteButton).uniform();
		middleColumn.row();
		middleColumn.add(kickButton).uniform();
		middleColumn.row();
		middleColumn.add(statsButton).uniform();
		middleColumn.row();
		middleColumn.add(playButton).uniform();
		
		table.add(middleColumn).top().expandX().fill().padBottom(75);
		
		Table partyInfo = new Table();
		
		pending = new List<>(skin);
		String[] pendingPlayers = {"player1", "player2", "player3"};
		pending.setItems(new Array<String>(pendingPlayers));
		ScrollPane pendingPlayerPane = new ScrollPane(pending, skin);
		Window pendingWindow = new Window("Pending Invitations", skin);
		pendingWindow.setMovable(false);
		pendingWindow.add(pendingPlayerPane).expand().fill();
		
		partyInfo.add(pendingWindow).expand().fill().pad(25, 0, 10, 0);
		partyInfo.row();
		
		party = new List<>(skin);
		String[] partyPlayers = {"player3", "player4", "player5"};
		party.setItems(new Array<String>(partyPlayers));
		ScrollPane partyPlayerPane = new ScrollPane(party, skin);
		Window partyWindow = new Window("In Party", skin);
		partyWindow.setMovable(false);
		partyWindow.add(partyPlayerPane).expand().fill();
		partyInfo.add(partyWindow).expand().fill().pad(10, 0, 0, 0);

		table.add(partyInfo).fill().pad(20);

		stage.addActor(table);
		

	}
}
