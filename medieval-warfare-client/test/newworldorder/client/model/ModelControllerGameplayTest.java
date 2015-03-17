package newworldorder.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import newworldorder.client.shared.UITileDescriptor;

public class ModelControllerGameplayTest {

	private ModelController model;
	private List<String> playerIds;
	private static final String mappath = "assets/maps/seaside-skirmish.mwm";
	private static final String savepath = "assets/saves/junit-saved-game.mwg";

	@Before
	public void setUp() throws Exception {
		model = ModelController.getInstance();
		playerIds = new ArrayList<String>();
		playerIds.add("Bob");
		playerIds.add("1234");
		playerIds.add("666");
	}

	@Test
	public void testGameplay() {
		UITileDescriptor a, b;
		int randx = 0, randy = 0;
		model.newGame("Bob", playerIds, mappath);

		assertFalse(model.getUpdatedTiles().isEmpty());

		randx = (int) Math.floor(Math.random() * (model.getMapWidth() - 1));
		randy = (int) Math.floor(Math.random() * (model.getMapHeight() - 1));

		a = model.getTile(randx, randy);

		model.saveGame(savepath);

		model.leaveGame();

		model.loadGame(savepath);

		assertFalse(model.getUpdatedTiles().isEmpty());

		b = model.getTile(randx, randy);

		assertEquals(a.terrainType, b.terrainType);
		assertEquals(a.colourType, b.colourType);
		assertEquals(a.structureType, b.structureType);
		assertEquals(a.unitType, b.unitType);
		assertEquals(a.villageType, b.villageType);
	}

}
