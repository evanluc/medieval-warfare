package newworldorder.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

public class TiledMapDescriptors {
	public TiledMapDescriptors(TiledMap tiledMap){
		this.colourLayer = (TiledMapTileLayer) tiledMap.getLayers().get("colour");
		this.terrainLayer = (TiledMapTileLayer) tiledMap.getLayers().get("terrain");
		this.unitLayer = (TiledMapTileLayer) tiledMap.getLayers().get("unit");
		this.structureLayer = (TiledMapTileLayer) tiledMap.getLayers().get("structure");

		
		
		this.colourTileSet = tiledMap.getTileSets().getTileSet("colourtiles");
		this.terrainTileSet = tiledMap.getTileSets().getTileSet("terraintiles");
		this.unitTileSet = tiledMap.getTileSets().getTileSet("unittiles");
		this.structureTileSet = tiledMap.getTileSets().getTileSet("structuretiles");

		
		//setting our tile colour for easy access
		this.blueTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(1);
		this.brownTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(2);
		this.greenTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(3);
		this.orangeTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(4);
		this.pinkTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(5);
		this.purpleTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(6);
		this.redTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(7);
		this.yellowTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(8);
		this.neutralTile = tiledMap.getTileSets().getTileSet("colourtiles").getTile(9);

		//setting our tile terrains for easy acess
		
		this.oceanTile = tiledMap.getTileSets().getTileSet("terraintiles").getTile(10);
		this.grassTile = tiledMap.getTileSets().getTileSet("terraintiles").getTile(11);
		this.meadowTile = tiledMap.getTileSets().getTileSet("terraintiles").getTile(12);
		this.nullTile = tiledMap.getTileSets().getTileSet("terraintiles").getTile(13);
		this.treeTile = tiledMap.getTileSets().getTileSet("terraintiles").getTile(14);
		
		//setting our unit tiles for easy access
		
		this.infantryTile = tiledMap.getTileSets().getTileSet("unittiles").getTile(15);
		this.knightTile = tiledMap.getTileSets().getTileSet("unittiles").getTile(16);
		this.peasantTile = tiledMap.getTileSets().getTileSet("unittiles").getTile(17);
		this.soldierTile = tiledMap.getTileSets().getTileSet("unittiles").getTile(18);

		this.roadTile = tiledMap.getTileSets().getTileSet("structuretiles").getTile(19);
		this.towerTile = tiledMap.getTileSets().getTileSet("structuretiles").getTile(20);
		this.tombstoneTile = tiledMap.getTileSets().getTileSet("structuretiles").getTile(21);
		
		
	}
	
//Our tiledMapLayers for easy access. 	
	public final TiledMapTileLayer colourLayer;
	public final TiledMapTileLayer terrainLayer;
	public final TiledMapTileLayer unitLayer;
	public final TiledMapTileLayer structureLayer;
	
//OUr tileset for easy access.
	
	public final TiledMapTileSet colourTileSet;
	public final TiledMapTileSet terrainTileSet;
	public final TiledMapTileSet unitTileSet;
	public final TiledMapTileSet structureTileSet;

	
//Our tiledMap colour tiles for easy access. 	
	public final TiledMapTile blueTile;
	public final TiledMapTile greenTile;
	public final TiledMapTile redTile;
	public final TiledMapTile yellowTile;
	public final TiledMapTile purpleTile;
	public final TiledMapTile orangeTile;
	public final TiledMapTile brownTile;
	public final TiledMapTile pinkTile;
	public final TiledMapTile neutralTile;
//our terrain tiles for easy access	
	public final TiledMapTile meadowTile;
	public final TiledMapTile treeTile;
	public final TiledMapTile grassTile;
	public final TiledMapTile oceanTile;
	public final TiledMapTile nullTile;

	
//our unit tiles for easy access
	
	public final TiledMapTile peasantTile;
	public final TiledMapTile infantryTile;
	public final TiledMapTile soldierTile;
	public final TiledMapTile knightTile;

	public final TiledMapTile roadTile;
	public final TiledMapTile towerTile;
	public final TiledMapTile tombstoneTile;
	
}
