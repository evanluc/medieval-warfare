package newworldorder.client.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import newworldorder.client.driver.UITileDescriptor;

import java.util.Iterator;
import java.util.List;
public class TiledMapStage extends Stage {

	private TiledMap tiledMap;
	private final TiledMapDescriptors tiledMapDescriptors; 

	public TiledMapStage(TiledMap tiledMap) {

		//creating our tiledMapDescriptors for easy access when we update
		this.tiledMapDescriptors = new TiledMapDescriptors(tiledMap);
		this.tiledMap = tiledMap;
		System.out.println("Get stage height = " + this.getHeight());
		System.out.println("Get stage width = " + this.getWidth());

		TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("colour");
		createActorsForLayer(tiledLayer);

	}  

	private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
		for (int x = 0; x < tiledLayer.getWidth(); x++) {
			for (int y = 0; y < tiledLayer.getHeight(); y++) {
				TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
				TiledMapActor actor = new TiledMapActor(tiledMap, tiledLayer, cell,x,y);
				float setHeight; 
				if(x % 2 == 0){
					setHeight = y * tiledLayer.getTileHeight() + (tiledLayer.getTileHeight()*1/2);
				}
				else setHeight = y * tiledLayer.getTileHeight();
				float setWidth = x * tiledLayer.getTileWidth() *3/4 + (tiledLayer.getTileWidth() * 1/4);
				actor.setBounds(setWidth, setHeight, tiledLayer.getTileWidth()*1/2,
						tiledLayer.getTileHeight());
				addActor(actor);
				EventListener eventListener = new TiledMapClickListener(actor);
				actor.addListener(eventListener);

			}
		}
	}

	public void tiledMapRenderUpdate (List<UITileDescriptor> UpdateTilesList) {
		Iterator itrTilesList = UpdateTilesList.iterator();
		System.out.println("size of the update tiles list: " + UpdateTilesList.size());
		UITileDescriptor currentTile; 
		int i = 0; 
		int x;
		int y;
		while (i < UpdateTilesList.size()) {
			currentTile = UpdateTilesList.get(i);
			System.out.println("You are accessing tile ("+currentTile.x+ "," + currentTile.y + ")");
			System.out.println("Updating colour to " + currentTile.colourType);
			i++;
			tiledMapColourUpdate(currentTile);
		}
	}

	public void tiledMapColourUpdate(UITileDescriptor updatedDescription){
		int x = updatedDescription.x; 
		int y = updatedDescription.y;
		Cell updateCell = tiledMapDescriptors.colourLayer.getCell(x,y);
		updateCell.setTile(tiledMapDescriptors.blueTile);


	}

}

