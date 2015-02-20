package newworldorder.server.matchmaking;

import newworldorder.common.IMatchController;

public class MatchController implements IMatchController {
	
	private static MatchController instance;
	
	private MatchQueue twoPlayerQueue;
	private MatchQueue threePlayerQueue;
	private MatchQueue fourPlayerQueue;
	
	private MatchController() {
		twoPlayerQueue = new MatchQueue(2);
		threePlayerQueue = new MatchQueue(3);
		fourPlayerQueue = new MatchQueue(4);
	}
	
	public static MatchController getInstance() {
		if (instance == null) {
			instance = new MatchController();
		}
		
		return instance;
	}
	
	@Override
	public void addToQueue() {
		// TODO
		
	}
	
}
