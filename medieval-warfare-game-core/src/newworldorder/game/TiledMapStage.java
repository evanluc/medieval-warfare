package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.List;

import newworldorder.game.driver.UITileDescriptor;
import newworldorder.game.model.ColourType;
import newworldorder.game.model.StructureType;
import newworldorder.game.model.TerrainType;
import newworldorder.game.model.UnitType;
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
		UITileDescriptor currentTile; 
		int i = 0; 
		while (i < UpdateTilesList.size()) {
			currentTile = UpdateTilesList.get(i);
			i++;
			tiledMapColourUpdate(currentTile);
			tiledMapTerrainUpdate(currentTile);
			tiledMapUnitUpdate(currentTile);
			tiledMapStructureUpdate(currentTile);

		}
	}

	public void tiledMapColourUpdate(UITileDescriptor updatedDescription){
		int x = updatedDescription.x; 
		int y = updatedDescription.y;
		Cell updateCell = tiledMapDescriptors.colourLayer.getCell(x,y);

		ColourType newColour = updatedDescription.colourType;
		switch(newColour){
			case BLUE: updateCell.setTile(tiledMapDescriptors.blueTile); 
			break; 
			
			case RED: updateCell.setTile(tiledMapDescriptors.redTile);
			break;
		
			case GREEN: updateCell.setTile(tiledMapDescriptors.greenTile);
			break;
			
			case PURPLE: updateCell.setTile(tiledMapDescriptors.purpleTile);
			break;
			
			case ORANGE: updateCell.setTile(tiledMapDescriptors.orangeTile);
			break;
			
			case BROWN: updateCell.setTile(tiledMapDescriptors.brownTile);
			break;			
		
			case PINK: updateCell.setTile(tiledMapDescriptors.pinkTile);
			break;		
	
			case YELLOW: updateCell.setTile(tiledMapDescriptors.yellowTile);
			break;					
			
		}
		

	}
	
	public void tiledMapTerrainUpdate(UITileDescriptor updatedDescription){
		int x = updatedDescription.x;
		int y = updatedDescription.y;
		Cell updateCell = tiledMapDescriptors.terrainLayer.getCell(x,y);
		
		TerrainType newTerrain = updatedDescription.terrainType;
		switch(newTerrain){
			case MEADOW: updateCell.setTile(tiledMapDescriptors.meadowTile); 
			break; 
			
			case GRASS: updateCell.setTile(tiledMapDescriptors.grassTile);
			break;
			
			case SEA: updateCell.setTile(tiledMapDescriptors.oceanTile);
			break;
			
			case TREE: updateCell.setTile(tiledMapDescriptors.treeTile);
			break;
				
			
		}
		
		
	}

	public void tiledMapUnitUpdate(UITileDescriptor updatedDescription){
		int x = updatedDescription.x;
		int y = updatedDescription.y;
		Cell updateCell = tiledMapDescriptors.unitLayer.getCell(x,y);
		
		UnitType newUnit = updatedDescription.unitType;
		
		switch(newUnit){
		case PEASANT: updateCell.setTile(tiledMapDescriptors.peasantTile);
		break;
		
		case INFANTRY: updateCell.setTile(tiledMapDescriptors.infantryTile);
		break;
		
		case SOLDIER: updateCell.setTile(tiledMapDescriptors.soldierTile);
		break;
		
		case KNIGHT: updateCell.setTile(tiledMapDescriptors.knightTile);
		break;
			
		}
		
	}
	
	public void tiledMapStructureUpdate(UITileDescriptor updatedDescription){
		int x = updatedDescription.x;
		int y = updatedDescription.y;
		Cell updateCell = tiledMapDescriptors.structureLayer.getCell(x,y);
		
		StructureType newStructure = updatedDescription.structureType;
		
		switch(newStructure){
		
		case ROAD: updateCell.setTile(tiledMapDescriptors.roadTile);
		break;
		
		case WATCHTOWER: updateCell.setTile(tiledMapDescriptors.towerTile);
		break;
		
		case TOMBSTONE: updateCell.setTile(tiledMapDescriptors.tombstoneTile);
		break;
			
		}
		
		
	}
	
	public void dialogTest(){
		
		final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		new Dialog("Available actions", skin) {

			{
				text("Choose a course of action: ");
				button("Build unit", "goodbye");
				button("Build structure", "goodbye");
				button("Structure upgrade", "goodbye");
				button("Move unit", "glad you stay");
				button("End turn", "glad you stay");
				
			}

			protected void result(final Object object) {
				System.out.println("clicked");
				
			}
		}.show(this);
		
		this.draw();

	}
	
	}

	

