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
	public void clicked(InputEvent event, float x, float y) {
		window.setVisible(false);
		
	}
}
