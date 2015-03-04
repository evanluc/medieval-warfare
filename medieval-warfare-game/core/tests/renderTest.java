import static org.junit.Assert.*;

import java.util.ArrayList;

import newworldorder.client.driver.UITileDescriptor; 
import newworldorder.client.game.TiledMapStage;
import newworldorder.client.model.*; 

import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
 

public class renderTest {

	@Test
	public static void main(String[] arg) {
		//fail("Not yet implemented");
		int x = 1;
		int y = 2;
		int x2 = 3;
		int y2 = 3;
		TerrainType testTerrain = TerrainType.TREE; 
		StructureType testStructure = StructureType.ROAD;
		UnitType testUnit = UnitType.PEASANT;
		VillageType testVillage = VillageType.TOWN;
		ColourType testColour = ColourType.BLUE;
		
		UITileDescriptor testDescriptor1 = new UITileDescriptor(x,y,testTerrain,testStructure,
				testUnit,testVillage,testColour);
		
		UITileDescriptor testDescriptor2= new UITileDescriptor(x2,y2,testTerrain,testStructure,
				testUnit,testVillage,testColour); 
		
		
		ArrayList<UITileDescriptor> testList = new ArrayList<UITileDescriptor>();
		testList.add(testDescriptor1);
		testList.add(testDescriptor2);

		
	}

}
