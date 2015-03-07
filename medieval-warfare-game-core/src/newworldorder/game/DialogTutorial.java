package newworldorder.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogTutorial extends ScreenAdapter {
	private Stage stage; 
	private Skin skin;

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage = new Stage());
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		new Dialog("confirm exit", skin) {

			{
				text("rly exit");
				button("yes", "goodbye");
				button("no", "glad you stay");
			}

			@Override
			protected void result(final Object object) {
				new Dialog("", skin) {

					{
						text(object.toString());
						button("OK");
					}

				}.show(stage);
			}

		}.show(stage);
	}

	//	public static class ExitDialog extends Dialog {
	//
	//		public ExitDialog(String title, Skin skin, String windowStyleName) {
	//			super(title, skin, windowStyleName);
	//		}
	//
	//		public ExitDialog(String title, Skin skin) {
	//			super(title, skin);
	//		}
	//
	//		public ExitDialog(String title, WindowStyle windowStyle) {
	//			super(title, windowStyle);
	//		}
	//
	//		{
	//			text("Do you really want to leave?");
	//			button("Yes", "See you next time!");
	//			button("No", "Enjoy your stay :)");
	//		}
	//
	//		@Override
	//		protected void result(Object object) {
	//			System.out.println(object);
	//		}
	//
	//	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(stage.getViewport());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
}
