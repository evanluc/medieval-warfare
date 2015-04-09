package newworldorder.game;

import java.util.List;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;
import newworldorder.client.shared.UITileDescriptor;
import newworldorder.client.shared.UIVillageDescriptor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIStage extends Stage{
	private Skin skin;
	private Tree tree;
	private Table table;
	private HUD hud;
	private Window notTurnWindow;
	private Label wood;
	private Label gold;
	private Label income;
	private Label expenses;
	private Label health;
	private TextButton endTurn;
	private Window villageWindow;
	private Label terrain;
	private Label unit;
	private Label structure;
	private Window tileWindow;


	public UIStage(Skin skin){
		this.skin = skin;

		hud = new HUD("HUD", skin, ModelController.getInstance().getCurrentTurnPlayer(), ModelController.getInstance().getTurnNumber());
		hud.setPosition(5,this.getCamera().viewportHeight);
		this.addActor(hud);

		this.notTurnWindow = new Window("Not your turn!", skin);
		Label stopItText = new Label("Your opponent is still making moves.\n Please wait until their turn ends\n"
				+ "In the meantime you can still survey your tiles and villages.",skin);
		notTurnWindow.add(stopItText).row();			
		notTurnWindow.setWidth(350);
		notTurnWindow.setHeight(100);
		notTurnWindow.setPosition(this.getCamera().position.x - notTurnWindow.getWidth()/2, 5);
		this.addActor(notTurnWindow);
		//village stuff
		villageWindow = new Window("Village stats", skin);
		wood = new Label("Village wood : " ,skin);
		gold = new Label("Village gold : " ,skin);
		income = new Label("Village income : " ,skin);
		expenses = new Label("Village expense : " ,skin);
		health = new Label ("Village health : " ,skin);
		villageWindow.add(wood).row();
		villageWindow.add(gold).row();
		villageWindow.add(income).row();
		villageWindow.add(expenses).row();
		villageWindow.add(health).row();
		villageWindow.setVisible(false);
		villageWindow.setPosition(this.getCamera().viewportWidth - 5,5);
		this.addActor(villageWindow);

		//village stuff

		//tile window stuff
		tileWindow = new Window("Tile stats", skin);
		terrain = new Label("Terrain : " ,skin);
		unit = new Label("Unit : " ,skin);
		structure = new Label("Structure : " ,skin);
		villageWindow.setVisible(false);
		this.addActor(tileWindow);
		//tile window stuff

		//buttons for table
		this.table = new Table();
		table.setFillParent(true);
		System.out.println(table.getWidth());
		this.addActor(table);
		//buttons for table

	}
	//here we render stuff for the current turn
	public void currentTurnRenderUpdate(){
		notTurnWindow.setVisible(false);
		hud.setCurrentUsername(ModelController.getInstance().getCurrentTurnPlayer());
		hud.setCurrentTurn(ModelController.getInstance().getTurnNumber());
	}

	public void notTurnRenderUpdate(){
		//		if (yourTurnWindow.isVisible()) yourTurnWindow.setVisible(false);
		notTurnWindow.setVisible(true);
		hud.setCurrentUsername(ModelController.getInstance().getCurrentTurnPlayer());
		hud.setCurrentTurn(ModelController.getInstance().getTurnNumber());

	}

	public void infoRenderUpdate(TiledMapActor selectedCell){
		buttonRenderUpdate(selectedCell);
		villageRenderUpdate(selectedCell);
		tileRenderUpdate(selectedCell);
	}

	public void buttonRenderUpdate(TiledMapActor selectedCell){
		table.clear();
		List <UIActionType> legalMovesList= ModelController.getInstance().getLegalMoves(selectedCell.getXCell(), selectedCell.getYCell());
		for (UIActionType UIAction : legalMovesList){
			if (UIAction != UIActionType.ENDTURN){
				TextButton newButton = new TextButton(uiActionTypeToString(UIAction), skin);
				table.add(newButton).pad(1);
				if (UIAction == UIActionType.MOVEUNIT) newButton.addListener(new SingleClickListener(selectedCell,tree,(TiledMapStage) selectedCell.getStage(),UIAction));
				else newButton.addListener(new DoubleClickListener(selectedCell,tree,(TiledMapStage) selectedCell.getStage(),UIAction));
			}
		}
		table.bottom().pad(10);
	}

	public void villageRenderUpdate(TiledMapActor selectedCell){
		if(ModelController.getInstance().getVillage(selectedCell.getXCell(), selectedCell.getYCell()) != null){
			System.out.println("here is a village");
			UIVillageDescriptor villageDescription = ModelController.getInstance().getVillage(selectedCell.getXCell(), selectedCell.getYCell());
			wood.setText("Wood : " + villageDescription.wood);
			gold.setText("Gold : " + villageDescription.gold);
			income.setText("Income : " + villageDescription.income);
			expenses.setText("Expenses : " + villageDescription.expenses);
			health.setText("Health : " + villageDescription.health+"/2");
			villageWindow.setVisible(true);
		}
		else villageWindow.setVisible(false);
	}
	//village render stuff

	public void tileRenderUpdate(TiledMapActor selectedCell){
		UITileDescriptor tileDescription = ModelController.getInstance().getTile(selectedCell.getXCell(), selectedCell.getYCell());
		tileWindow.clear();
		if(tileDescription.structureType != null){ 
			structure.setText(tileDescription.structureType.toString());
			tileWindow.add(structure);	
		}

		if(tileDescription.unitType != null){ 
			structure.setText(tileDescription.unitType.toString());
			tileWindow.add(unit);	
		}
		
		if(tileDescription.terrainType != null){ 
			structure.setText(tileDescription.terrainType.toString());
			tileWindow.add(terrain);	
		}
		tileWindow.setVisible(true);

	}

	//tile render stuff


//



private String uiActionTypeToString(UIActionType action){
	switch(action){
	case MOVEUNIT: return "Move unit";
	case BUILDROAD: return "Build road";
	case BUILDTOWER: return "Build tower";
	case BUILDUNITINFANTRY: return "Build infantry";
	case BUILDUNITKNIGHT:return "Build knight";
	case BUILDUNITPEASANT:return "Build peasant";
	case BUILDUNITSOLDIER:return"Build soldier";
	case CULTIVATEMEADOW:return"Cultivate meadow";
	case ENDTURN:return"End your turn";
	case UPGRADEUNITINFANTRY:return"Upgrade to infantry";
	case UPGRADEUNITKNIGHT:return"Upgrade to knight";
	case UPGRADEUNITSOLDIER:return "Upgrade to soldier";
	case UPGRADEVILLAGEFORT:return "Upgrade to fort";
	case UPGRADEVILLAGETOWN:return "Upgrade to town";
	case BOMBARDTILE:return "Upgrade bombarding tile!";
	case BUILDUNITCANNON: return "Upgrade to town";
	case UPGRADEVILLAGECASTLE: return "Upgrade to castle";

	}
	return null;
}
}
