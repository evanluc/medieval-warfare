package newworldorder.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TiledMapActor extends Actor {

    TiledMap tiledMap;

    TiledMapTileLayer tiledLayer;

    TiledMapTileLayer.Cell cell;
    
    private int x; 
	private int y;
	
    
    public TiledMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell, int x, int y) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
        this.x = x;
        this.y = y;
    }
    
    public int getXCell() {
    	return x;     	
    }

    public int getYCell(){
    	return y;
    }
    

}
