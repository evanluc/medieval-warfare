package newworldorder.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import newworldorder.client.model.ModelController;
import newworldorder.client.shared.UIActionType;

public class DoubleClickListener extends ClickListener {
	private TiledMapActor actor;
	private Window window;
	private UIActionType actionType;
	private Tree tree;
	private ModelController controller = ModelController.getInstance();

	public DoubleClickListener(TiledMapActor actor, Window window, TiledMapStage stage, UIActionType actionType) {
		this.actor = actor;
		this.window = window;
		this.actionType = actionType;
	}

	public DoubleClickListener(TiledMapActor actor, Tree tree, TiledMapStage stage, UIActionType actionType) {
			this.actor = actor;
			this.tree = tree;
			this.actionType = actionType;
	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
		if (window != null) window.setVisible(false);
		if (tree != null) tree.clear();
		System.out.println("sending action type : " + actionType);
		controller.informOfUserAction(actionType, actor.getXCell(), actor.getYCell());
	}
}
