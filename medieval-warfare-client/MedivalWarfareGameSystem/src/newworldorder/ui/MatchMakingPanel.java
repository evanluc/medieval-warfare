package newworldorder.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MatchMakingPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainView aMainView;
	public MatchMakingPanel(MainView pMainView){
		super();
		aMainView = pMainView;
		setLayout( new BorderLayout() );
		JPanel northPanel = new JPanel();
		JLabel jlabel = new JLabel("Searching For A Game...");
	    jlabel.setFont(new Font("Calibri",1,60));
	    northPanel.add(jlabel);
	    northPanel.setOpaque(false);
	    add(northPanel, BorderLayout.NORTH);
		
		JButton back = new JButton("Back");
		back.setBackground(new Color(220, 20, 60));
	    back.setForeground(Color.WHITE);
	    back.setFocusPainted(false);
	    back.setFont(new Font("Calibri", Font.BOLD, 20));
		back.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				aMainView.setJoinFromMatchMaking();
			}	
		});
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));	
        southPanel.add(back);
		southPanel.setOpaque(false);
		add(southPanel, BorderLayout.SOUTH);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);
		LoadingPane aLoadingPane = new LoadingPane(aMainView);
		centerPanel.add(aLoadingPane);
		add(centerPanel,BorderLayout.CENTER);
	    setOpaque(false);
	}
	
	public class LoadingPane extends JPanel {

        private Timer paintTimer;
        private float cycle;
        private int count=0;
        private boolean invert = false;
        private MainView aMainView;

        public LoadingPane(MainView pMainView) {
        	super();
        	aMainView = pMainView;
        	setOpaque(false);
            paintTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cycle += 0.02f;
                    if (cycle > 1f) {
                    	count++;
                        cycle = 0f;
                        invert = !invert;
                    }
                    if(count ==10){
                    	setRuning(false);
                    	aMainView.setMatched();
                    }
                    repaint();
                }
            });
            paintTimer.setRepeats(true);
            setRuning(true);
        }

        public void setRuning(boolean running) {
            if (running) {
                paintTimer.start();
            } else {
                paintTimer.stop();
            }
        }

        public boolean isRunning() {
            return paintTimer.isRunning();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 100);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isRunning()) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

                int width = getWidth() - 1;
                int height = getHeight() - 1;
                int radius = Math.min(width, height);

                int x = (width - radius) / 2;
                int y = (height - radius) / 2;
                int start = 0;
                int extent = Math.round(cycle * 360f);

                if (invert) {
                    start = extent;
                    extent = 360 - extent;
                }

                g2d.setColor(Color.BLUE);
                g2d.fill(new Arc2D.Float(x, y, radius, radius, start, extent, Arc2D.PIE));
                g2d.setColor(Color.WHITE);
                g2d.draw(new Arc2D.Float(x, y, radius, radius, start, extent, Arc2D.PIE));
                g2d.setFont(new Font("Calibri", Font.PLAIN, getWidth()/15)); 
                g2d.drawString("Searching...",getWidth()/3+getWidth()/50, getHeight()/2-(getHeight()/10));
                g2d.dispose();
            }
        }
    }
}
