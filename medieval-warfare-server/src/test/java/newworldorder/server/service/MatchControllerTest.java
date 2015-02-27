package newworldorder.server.service;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.server.matchmaking.GameInitializer;

public class MatchControllerTest {
	@Mock private GameInitializer gameInitializer;
	private MatchController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new MatchController(gameInitializer);
	}
	
	@After
	public void tearDown() {
		gameInitializer = null;
		controller = null;
	}
	
	@Test
	public void testTwoPlayerQueue() {
		testSimpleInserts(2);
	}
	
	@Test
	public void testThreePlayerQueue() {
		testSimpleInserts(3);
	}
	
	@Test
	public void testFourPlayerQueue() {
		testSimpleInserts(4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidNumberOfPlayers() {
		testSimpleInserts(5);
	}
	
	@Test
	public void testMoreInsertsThanGameSize() {
		String extraPlayer = "extraPlayer";
		int num = 4;
		List<String> expected = insertPlayers(num);
		
		controller.addToQueue(new GameRequest(extraPlayer, num));
		then(gameInitializer).should(times(1)).initializeGame(expected);
	}
	
	private void testSimpleInserts(int numPlayers) {
		List<String> expected = insertPlayers(numPlayers);
		then(gameInitializer).should(times(1)).initializeGame(expected);
	}
	
	private List<String> insertPlayers(int numPlayers) {
		List<String> players = new ArrayList<>();
		
		for (int i = 0; i < numPlayers; i++) {
			String player = "player" + String.valueOf(i);
			GameRequest request = new GameRequest(player, numPlayers);
			controller.addToQueue(request);
			players.add(player);
		}
		
		return players;
	}
}
