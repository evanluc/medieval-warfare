package newworldorder.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/*
 *  Support custom painting on a panel in the form of
 *
 *  a) images - that can be scaled, tiled or painted at original size
 *  b) non solid painting - that can be done by using a Paint object
 *
 *  Also, any component added directly to this panel will be made
 *  non-opaque so that the custom painting can show through.
 */

@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel
{
	public static final int SCALED = 0;
	public static final int TILED = 1;
	public static final int ACTUAL = 2;

	private Paint painter;
	private Image image;
	private int style = SCALED;
	private float alignmentX = 0.5f;
	private float alignmentY = 0.5f;
	private boolean isTransparentAdd = true;
	private static Clip music;
	private ImageIcon musicPlaying;
	private ImageIcon musicOff;
	private int count = 0;

	/*
	 *  Set image as the background with the SCALED style
	 *  with a boolean option for having music or not.
	 */
	public BackgroundPanel(Image image, boolean backgroundMusic)
	{
		this(image, SCALED);
		if(backgroundMusic==true)
		{
			try{ 
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/titlescreen.wav"));
				music = AudioSystem.getClip();
				music.open(audioInputStream);
				music.loop(Clip.LOOP_CONTINUOUSLY);
				music.start();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			BufferedImage img1=null;
			try {
				InputStream stream = getClass().getResourceAsStream("/speaker-volume.png");
				img1 = ImageIO.read(stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			musicPlaying =new ImageIcon(img1);
			BufferedImage img2=null;
			try {
				InputStream stream = getClass().getResourceAsStream("/speaker-quiet.png");
				img2 = ImageIO.read(stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			musicOff =new ImageIcon(img2);
			JButton musicButton = new JButton(musicPlaying);
			musicButton.setBorder(BorderFactory.createEmptyBorder());
			musicButton.setContentAreaFilled(false);
			musicButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					musicButton.setIcon(musicButton.getIcon()==musicPlaying? musicOff : musicPlaying);
					if(musicButton.getIcon() == musicOff)
					{
						music.stop();
					}
					else{
						music.loop(Clip.LOOP_CONTINUOUSLY);
						music.start();
					}
					repaint();
				}
			});
			JPanel eastPanel = new JPanel();
			eastPanel.setOpaque(false);
			eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
			eastPanel.add(musicButton);
			add(eastPanel, BorderLayout.EAST);
		}	
	}

	/*
	 *  Set image as the background with the specified style
	 */
	public BackgroundPanel(Image image, int style)
	{
		setImage( image );
		setStyle( style );
		setLayout( new BorderLayout() );
	}

	/*
	 *  Set image as the backround with the specified style and alignment
	 */
	public BackgroundPanel(Image image, int style, float alignmentX, float alignmentY)
	{
		setImage( image );
		setStyle( style );
		setImageAlignmentX( alignmentX );
		setImageAlignmentY( alignmentY );
		setLayout( new BorderLayout() );
	}

	/*
	 *  Use the Paint interface to paint a background
	 */
	public BackgroundPanel(Paint painter)
	{
		setPaint( painter );
		setLayout( new BorderLayout() );
	}

	/*
	 *	Set the image used as the background
	 */
	public void setImage(Image image)
	{
		this.image = image;
		repaint();
	}

	/*
	 *	Set the style used to paint the background image
	 */
	public void setStyle(int style)
	{
		this.style = style;
		repaint();
	}

	/*
	 *	Set the Paint object used to paint the background
	 */
	public void setPaint(Paint painter)
	{
		this.painter = painter;
		repaint();
	}

	/*
	 *  Specify the horizontal alignment of the image when using ACTUAL style
	 */
	public void setImageAlignmentX(float alignmentX)
	{
		this.alignmentX = alignmentX > 1.0f ? 1.0f : alignmentX < 0.0f ? 0.0f : alignmentX;
		repaint();
	}

	/*
	 *  Specify the horizontal alignment of the image when using ACTUAL style
	 */
	public void setImageAlignmentY(float alignmentY)
	{
		this.alignmentY = alignmentY > 1.0f ? 1.0f : alignmentY < 0.0f ? 0.0f : alignmentY;
		repaint();
	}

	/*
	 *  Override method so we can make the component transparent
	 */
	public void add(JComponent component)
	{
		add(component, null);
	}

	/*
	 *  Override to provide a preferred size equal to the image size
	 */
	@Override
	public Dimension getPreferredSize()
	{
		if (image == null)
			return super.getPreferredSize();
		else
			return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	/*
	 *  Override method so we can make the component transparent
	 */
	public void add(JComponent component, Object constraints)
	{
		if (isTransparentAdd)
		{
			makeComponentTransparent(component);
		}

		super.add(component, constraints);
	}

	/*
	 *  Controls whether components added to this panel should automatically
	 *  be made transparent. That is, setOpaque(false) will be invoked.
	 *  The default is set to true.
	 */
	public void setTransparentAdd(boolean isTransparentAdd)
	{
		this.isTransparentAdd = isTransparentAdd;
	}

	/*
	 *	Try to make the component transparent.
	 *  For components that use renderers, like JTable, you will also need to
	 *  change the renderer to be transparent. An easy way to do this it to
	 *  set the background of the table to a Color using an alpha value of 0.
	 */
	private void makeComponentTransparent(JComponent component)
	{
		component.setOpaque( false );

		if (component instanceof JScrollPane)
		{
			JScrollPane scrollPane = (JScrollPane)component;
			JViewport viewport = scrollPane.getViewport();
			viewport.setOpaque( false );
			Component c = viewport.getView();

			if (c instanceof JComponent)
			{
				((JComponent)c).setOpaque( false );
			}
		}
	}

	/*
	 *  Add custom painting
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//  Invoke the painter for the background

		if (painter != null)
		{
			Dimension d = getSize();
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(painter);
			g2.fill( new Rectangle(0, 0, d.width, d.height) );
		}

		//  Draw the image

		if (image == null ) return;

		switch (style)
		{
			case SCALED :
				drawScaled(g);
				break;

			case TILED  :
				drawTiled(g);
				break;

			case ACTUAL :
				drawActual(g);
				break;

			default:
            	drawScaled(g);
		}
	}

	/*
	 *  Custom painting code for drawing a SCALED image as the background
	 */
	private void drawScaled(Graphics g)
	{
		Dimension d = getSize();
		g.drawImage(image, 0, 0, d.width, d.height, null);
	}

	/*
	 *  Custom painting code for drawing TILED images as the background
	 */
	private void drawTiled(Graphics g)
	{
		   Dimension d = getSize();
		   int width = image.getWidth( null );
		   int height = image.getHeight( null );

		   for (int x = 0; x < d.width; x += width)
		   {
			   for (int y = 0; y < d.height; y += height)
			   {
				   g.drawImage( image, x, y, null, null );
			   }
		   }
	}

	/*
	 *  Custom painting code for drawing the ACTUAL image as the background.
	 *  The image is positioned in the panel based on the horizontal and
	 *  vertical alignments specified.
	 */
	private void drawActual(Graphics g)
	{
		Dimension d = getSize();
		Insets insets = getInsets();
		int width = d.width - insets.left - insets.right;
		int height = d.height - insets.top - insets.left;
		float x = (width - image.getWidth(null)) * alignmentX;
		float y = (height - image.getHeight(null)) * alignmentY;
		g.drawImage(image, (int)x + insets.left, (int)y + insets.top, this);
	}
	
}