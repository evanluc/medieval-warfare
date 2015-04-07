package newworldorder.game;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

import newworldorder.client.model.ModelController;

public class MedievalWarfareGame extends Game {
	public TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	SpriteBatch sb;
	Sprite sprite;
	Texture texture;
	OrthographicCamera camera;
	GameScreen gameScreen;

	@Override
	public void create () {

		
	gameScreen = new GameScreen(this);
	//this.setScreen(new LoginScreen(gameScreen, this));
	this.setScreen(gameScreen);
		
	}

	@Override
	public void render () {
		super.render();
	}
}
