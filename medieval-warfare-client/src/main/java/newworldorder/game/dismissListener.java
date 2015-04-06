package newworldorder.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class dismissListener extends ClickListener{
	private Window window;
	
	public dismissListener(Window window){
		this.window = window;
	}
	
	@Override
	 public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		window.setVisible(false);
		return false;	
	}
}
