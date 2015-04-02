package newworldorder.client.shared;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a very simple descriptor type containing all the information about a
 * village that the UI might be interested in.
 * @author David
 *
 */
public class UIVillageDescriptor {
	public UIVillageDescriptor(int x, int y, VillageType villageType, int gold,
			int wood, int health, Map<UnitType, Integer> unitCounts, int income,
			int expenses) {
		super();
		this.x = x;
		this.y = y;
		this.villageType = villageType;
		this.gold = gold;
		this.wood = wood;
		this.health = health;
		this.unitCounts = new HashMap<UnitType, Integer>(unitCounts);
		this.income = income;
		this.expenses = expenses;
	}
	public final int x;
	public final int y;
	public final VillageType villageType;
	public final int gold;
	public final int wood;
	public final int health;
	public final Map<UnitType,Integer> unitCounts;
	public final int income;
	public final int expenses;
}
