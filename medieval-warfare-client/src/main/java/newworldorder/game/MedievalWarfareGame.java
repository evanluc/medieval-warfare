package newworldorder.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

public class MedievalWarfareGame extends Game {
	public TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	SpriteBatch sb;
	Sprite sprite;
	Texture texture;
	OrthographicCamera camera;
	GameScreen gameScreen;
	LoginScreen loginScreen;
	MatchmakingScreen matchmakingScreen;

	@Override
	public void create () {

	gameScreen = new GameScreen(this);
	loginScreen = new LoginScreen(this);
	matchmakingScreen = new MatchmakingScreen(this);
//	this.setScreen(matchmakingScreen);
	this.setScreen(loginScreen);
//	this.setScreen(gameScreen);
	}
	
	public MedievalWarfareGame() {
		super();
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void setGameScreen() {
		this.setScreen(gameScreen);
	}
	
	public void setLoginScreen() {
		this.setScreen(loginScreen);
	}
	
	public void setMatchmakingScreen() {
		this.setScreen(matchmakingScreen);
	}
}
