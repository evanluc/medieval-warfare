package newworldorder.game;

import java.util.List;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	private Window window;
	private Node build;
	public UIStage(Skin skin){
		this.skin = skin;
		this.table = new Table();
		table.setFillParent(true);

	//	this.window = new Window("Available rows", skin);
		this.addActor(table);
		this.tree = new Tree(skin);
		final Node move = new Node(new TextButton("Move unit", skin));
		this.build = new Node(new TextButton("Build", skin));
		final Node upgrade = new Node(new TextButton("Upgrade", skin));
		final Node endturn = new Node(new TextButton("End turn", skin));
		final Node unit = new Node(new TextButton("Unit name", skin));
		tree.add(move);
		tree.add(build);
		tree.add(upgrade);
		tree.add(endturn);
		upgrade.add(unit);
		table.add(tree).fill().expand();
		//table.setPosition( this.getWidth() / 2 - table.getWidth()/2, this.getHeight() - table.getHeight());
		table.bottom();
		


	}
	
	
	public void buttonRenderUpdate(TiledMapActor selectedCell){
		tree.clear();
		
		List <UIActionType> legalMovesList= ModelController.getInstance().getLegalMoves(selectedCell.getXCell(), selectedCell.getYCell());
		for (UIActionType UIAction : legalMovesList){
			tree.add(new Node(new TextButton(uiActionTypeToString(UIAction), skin)));
		} 
		//adding dismiss node

		TextButton dismiss = new TextButton("dismiss",skin);
		dismiss.addListener(new TreeClearListener(table));
		Node dismissNode = new Node(dismiss);
		//window.add(tree).fill().expand();
		tree.add(dismissNode);
	}
	
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
		}
		return null;
	}
}
