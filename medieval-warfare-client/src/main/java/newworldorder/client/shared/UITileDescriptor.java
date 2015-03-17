package newworldorder.client.shared;



/**
 * This class is a very simple descriptor type that contains all the information about
 * a tile that the GUI might be interested in. All the fields are publicly accessible
 * because there will never be any modification of the attributes after instantiation.
 * 
 * @author David
 *
 */
public class UITileDescriptor {
	public UITileDescriptor(int x, int y, TerrainType terrainType,
			StructureType structureType, UnitType unitType,
			VillageType villageType, ColourType colourType) {
		super();
		this.x = x;
		this.y = y;
		this.terrainType = terrainType;
		this.structureType = structureType;
		this.unitType = unitType;
		this.villageType = villageType;
		this.colourType = colourType;
	}
	public final int x;
	public final int y;
	public final TerrainType terrainType;
	public final StructureType structureType;
	public final UnitType unitType;
	public final VillageType villageType;
	public final ColourType colourType;
}
