package newworldorder.game;

import newworldorder.client.shared.UIActionType;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TreeClearListener extends ClickListener{

	private Table table;
	
	public TreeClearListener(Table table) {
		this.table = table;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {	
		table.clearChildren();
	}	
}
