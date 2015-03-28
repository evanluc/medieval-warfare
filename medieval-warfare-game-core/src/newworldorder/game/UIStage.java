package newworldorder.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIStage extends Stage{
	public UIStage(Skin skin){

		Table table = new Table();
		table.setFillParent(true);
		this.addActor(table);
		final Tree tree = new Tree(skin);
		final Node move = new Node(new TextButton("Move unit", skin));
		final Node build = new Node(new TextButton("Build", skin));
		final Node upgrade = new Node(new TextButton("Upgrade", skin));
		final Node unit = new Node(new TextButton("Unit name", skin));
		tree.add(move);
		tree.add(build);
		tree.add(upgrade);
		upgrade.add(unit);

		table.add(tree).fill().expand();

	}
	
	
	public void buttonRenderUpdate(){
		
		
	}
}
