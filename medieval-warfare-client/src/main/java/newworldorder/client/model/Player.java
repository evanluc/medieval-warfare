package newworldorder.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import newworldorder.client.shared.ColourType;

/**
 * Player class clean and complete.
 */
class Player implements Serializable {
    
	private static final long serialVersionUID = -3711737223282059721L;
    private final String username;
    private PlayerStatus status;
    private ColourType colour;
    private Set<Village> controlledVillages;
    
    /**
     * @param username
     * @param currentGame
     */    
    public Player(String username)
    {
        this.username = username;
        this.status = null;
        this.colour = null;
        this.controlledVillages = new HashSet<Village>();
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
    
    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus pStatus) {
        status = pStatus;
    }
    
    public ColourType getColour() {
        return colour;
    }
    
    public void setColour(ColourType pColour) {
        colour = pColour;
    }

    public List<Village> getVillages() {
        return new ArrayList<Village>(controlledVillages);
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

	public int getPlayerId() {
		return username.hashCode();
	}
}
