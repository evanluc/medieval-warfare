package newworldorder.client.model;

import java.util.ArrayList;
import java.util.List;



/**
 * Player class clean and complete.
 */
public class Player {
    

    private final String username;
    private final String password;
    private PlayerStatus status;
    private int wins;
    private int losses;
    private ColourType colour;
    private Game currentGame;
    private List<Village> controlledVillages;
    
    /**
     * @param username
     * @param password
     * @param wins
     * @param losses
     * @param currentGame
     */
    public Player(String username, String password, int wins, int losses, Game currentGame)
    {
        this.username = username;
        this.password = password;
        this.status = null;
        this.wins = wins;
        this.losses = losses;
        this.colour = null;
        this.currentGame = currentGame;
        this.controlledVillages = new ArrayList<Village>();
    }
    
    public static void setUpPlayers(List<Player> players) {
        int i = 0;
        for (Player p : players) {
            ColourType c = ColourType.values()[i];
            p.setColour(c);
            p.setStatus(PlayerStatus.ONLINE);
            i++;
        }
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
    
    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus pStatus) {
        status = pStatus;
    }
    
    public int getWins()
    {
        return wins;
    }

    public int getLosses()
    {
        return losses;
    }
    
    public ColourType getColour() {
        return colour;
    }
    
    public void setColour(ColourType pColour) {
        colour = pColour;
    }
    
    public Game getCurrentGame()
    {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame)
    {
        this.currentGame = currentGame;
    }

    public List<Village> getVillages() {
        return controlledVillages;
    }

    public void addVillage(Village pVillage) {
        controlledVillages.add(pVillage);
    }
    
    public void removeVillage(Village pVillage) {
        controlledVillages.remove(pVillage);
    }

    public List<Unit> getUnits() {
        List<Unit> units = new ArrayList<Unit>();
        // Declare a new ArrayList and add the units from each village to this list;
        for (Village v : controlledVillages) {
            units.addAll(v.getSupportedUnits());
        }
        return units;
    }

    public void incrementWinCount() {
        wins++;
    }

    public void incrementLossCount() {
        losses--;
    }
}
