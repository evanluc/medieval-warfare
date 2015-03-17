package newworldorder.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Game class clean and complete.
 * This will be deleted soon, please start using corresponding definition in newworldorder.client.model or newworldorder.client.shared
 */
@Deprecated
public class Game implements Serializable {

	private static final long serialVersionUID = 2079718066507692385L;
	private int roundCount = 0;
	private boolean hasWon = false;
	private final List<Player> players;
	private List<Message> chat;
	private Player turnOf;
	private final Map map;

	/**
	 * @param players
	 * @param map
	 */
	public Game(List<Player> players, Map map) {
		this.players = new ArrayList<Player>(players);
		this.chat = new ArrayList<Message>();
		this.turnOf = this.players.get(0);
		this.map = map;
	}

	public int getRoundCount() {
		return roundCount;
	}

	public boolean hasWon() {
		return hasWon;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getCurrentTurnPlayer() {
		return turnOf;
	}

	public void setTurnOf(Player turnOf) {
		this.turnOf = turnOf;
	}

	public Map getMap() {
		return map;
	}

	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	public void incrementRoundCount() {
		roundCount++;
	}

	public List<Message> getAllChat() {
		return new ArrayList<Message>(chat);
	}

	public List<Message> getChatSentBy(Player p) {
		List<Message> ret = new ArrayList<Message>();
		for (Message m : chat) {
			if (m.getSender() == p) {
				ret.add(m);
			}
		}
		return ret;
	}
}
