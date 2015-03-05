package newworldorder.client.gui;



import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("serial")
public class MainView extends JFrame {
	private String aName;	// TODO, this should go somewhere else

	public MainView() {
		super();
		BufferedImage img=null;
		try {
			InputStream stream = getClass().getResourceAsStream("/background.jpg");
			img = ImageIO.read(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setContentPane(new BackgroundPanel(img));
		setTitle("Medieval Warfare");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainView();
	}

	@Override
	public String getName() {
		return aName;
	}

	@Override
	public void setName(String pName) {
		aName = pName;
	}
}