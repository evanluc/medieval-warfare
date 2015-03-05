package newworldorder.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

public class TiledMapDescriptors {
	public TiledMapDescriptors(TiledMap tiledMap){
		this.colourLayer = (TiledMapTileLayer) tiledMap.getLayers().get("colour");
		this.terrainLayer = (TiledMapTileLayer) tiledMap.getLayers().get("terrain");
		
		
		this.colourTileSet = tiledMap.getTileSets().getTileSet("hextiles");
		this.terrainTileSet = tiledMap.getTileSets().getTileSet("terrain");
		
		//setting our tile colour for easy access
		this.blueTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(1);
		this.greenTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(2);
		this.orangeTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(3);
		this.purpleTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(4);
		this.redTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(5);
		this.yellowTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(6);
		this.brownTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(7);
		this.pinkTile = tiledMap.getTileSets().getTileSet("hextiles").getTile(8);

		//setting our tile terrains for easy acess
		
		this.meadowTile = tiledMap.getTileSets().getTileSet("terrain").getTile(9);
		this.grassTile = tiledMap.getTileSets().getTileSet("terrain").getTile(11);
		this.oceanTile = tiledMap.getTileSets().getTileSet("terrain").getTile(12);
		this.treeTile = tiledMap.getTileSets().getTileSet("terrain").getTile(13);
		
		
	}
	
//Our tiledMapLayers for easy access. 	
	public final TiledMapTileLayer colourLayer;
	public final TiledMapTileLayer terrainLayer;
	
//OUr tileset for easy access.
	
	public final TiledMapTileSet colourTileSet;
	public final TiledMapTileSet terrainTileSet; 
	
//Our tiledMap colour tiles for easy access. 	
	public final TiledMapTile blueTile;
	public final TiledMapTile greenTile;
	public final TiledMapTile redTile;
	public final TiledMapTile yellowTile;
	public final TiledMapTile purpleTile;
	public final TiledMapTile orangeTile;
	public final TiledMapTile brownTile;
	public final TiledMapTile pinkTile;
	
	public final TiledMapTile meadowTile;
	public final TiledMapTile treeTile;
	public final TiledMapTile grassTile;
	public final TiledMapTile oceanTile;
	
}
