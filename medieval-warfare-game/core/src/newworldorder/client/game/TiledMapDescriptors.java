package newworldorder.client.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

public class TiledMapDescriptors {
	public TiledMapDescriptors(TiledMap tiledMap){
		this.colourLayer = (TiledMapTileLayer) tiledMap.getLayers().get("colour");
		this.terrainLayer = (TiledMapTileLayer) tiledMap.getLayers().get("terrain");
		
		
		this.colourTileSet = tiledMap.getTileSets().getTileSet("hextiles");
		this.blueTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(1);
		
	}
	
//Our tiledMapLayers for easy access. 	
	public final TiledMapTileLayer colourLayer;
	public final TiledMapTileLayer terrainLayer;
	
//OUr tileset for easy access.
	
	public final TiledMapTileSet colourTileSet; 
	
//Our tiledMap colour tiles for easy access. 	
	public final TiledMapTile blueTile;
	
}
