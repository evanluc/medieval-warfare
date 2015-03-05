package newworldorder.game.model;

import java.util.ArrayList;
import java.util.List;



/**
 * Game class clean and complete.
 */
public class Game {
    
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
    public Game(List<Player> players, Map map)
    {
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
    
    public Player getTurnOf() {
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
