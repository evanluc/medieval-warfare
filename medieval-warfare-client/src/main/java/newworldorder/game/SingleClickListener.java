package newworldorder.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import newworldorder.client.shared.UIActionType;

public class SingleClickListener extends ClickListener {
	private TiledMapActor actor;
	private Window window;
	private TiledMapStage stage;
	private UIActionType actionType;
	private Tree tree;

	public SingleClickListener(TiledMapActor actor, Window window, TiledMapStage stage, UIActionType actionType) {
		this.actor = actor;
		this.window = window;
		this.stage = stage;
		this.actionType = actionType;
	}

	public SingleClickListener(TiledMapActor actor, Tree tree, TiledMapStage stage, UIActionType actionType) {
		this.actor = actor;
		this.tree = tree;
		this.stage = stage;
		this.actionType = actionType;
	}	
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		System.out.println("moving unit");
		stage.setMultiActionInput(true);
		stage.setPreviousActor(actor);
		if (window != null) window.setVisible(false);
		if (tree != null) stage.getUIStage().buttonRenderUpdate(actor);
	}
}
