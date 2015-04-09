package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

public class MatchmakingScreen implements Screen {
	
	OrthographicCamera camera;
	Stage stage;
	SpriteBatch batch;
	private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	GameScreen gameScreen;
	MedievalWarfareGame thisGame;
	private Image map;
	Sprite sprite;

	public MatchmakingScreen() {
		super();
		
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
	
	private void initUI() {
		Window table = new Window("Matchmaking", skin);
	    table.setFillParent(true);
//	    table.setDebug(true);
	    
	    TextButton loadButton = new TextButton("Load Game", skin);
		loadButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		SelectBox<String> mapSelect = new SelectBox<>(skin);
		Array<String> items = new Array<>();
		items.add("Seaside Skirmish");
		items.add("The Dark Forest");
		items.add("Half-Moon Bay");
		mapSelect.setItems(items);
		TextureRegionDrawable[] maps = new TextureRegionDrawable[3];
		mapSelect.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				map.setDrawable(maps[mapSelect.getSelectedIndex()]);
			}
			
		});
		
		SelectBox<String> numPlayerSelect = new SelectBox<>(skin);
		Array<String> nums = new Array<>();
		nums.add("Choose # players");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		numPlayerSelect.setItems(nums);
		
		table.row().width(150).pad(35, 0, 40, 0);
		table.add(loadButton).expandX().width(125);
		table.add(mapSelect).expandX().width(175);
		table.add(numPlayerSelect).expandX().width(155);
		table.row();
		
		Window onlineWindow = new Window("Online Players", skin);
		onlineWindow.setMovable(false);
		List<String> onlinePlayers = new List<>(skin);
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
		
		TextButton inviteButton = new TextButton("Invite To Party", skin);
		inviteButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		TextButton kickButton = new TextButton("Kick From Party", skin);
		kickButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		TextButton statsButton = new TextButton("View Stats", skin);
		statsButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		TextButton playButton = new TextButton("Play", skin);
		playButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		middleColumn.columnDefaults(0).width(150).pad(5);
		map = new Image(maps[mapSelect.getSelectedIndex()]);
		middleColumn.add(map).size(450, 300).expand().top().pad(10, 0, 40, 0).fill();
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
		
		List<String> pending = new List<>(skin);
		String[] pendingPlayers = {"player1", "player2", "player3"};
		pending.setItems(new Array<String>(pendingPlayers));
		ScrollPane pendingPlayerPane = new ScrollPane(pending, skin);
		Window pendingWindow = new Window("Pending Invitations", skin);
		pendingWindow.setMovable(false);
		pendingWindow.add(pendingPlayerPane).expand().fill();
		
		partyInfo.add(pendingWindow).expand().fill().pad(25, 0, 10, 0);
		partyInfo.row();
		
		List<String> party = new List<>(skin);
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
