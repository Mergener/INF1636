package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

public class LayeredImageDrawer extends JPanel {
	
	private ArrayList<BufferedImage> layers = new ArrayList<BufferedImage>();
	
	private int highestWidth = 0;
	private int highestHeight = 0;
	
	public LayeredImageDrawer() {
	}
	
	public void addImageLayer(BufferedImage image) {
		highestWidth = Math.max(highestWidth, image.getWidth());
		highestHeight = Math.max(highestHeight, image.getHeight());
		
		layers.add(image);
		setPreferredSize(new Dimension(highestWidth, highestHeight));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        
		for (int i = 0; i < layers.size(); ++i) {
			BufferedImage image = layers.get(i);
			g.drawImage(image, 0, 0, this);
		}
		
		g.finalize();
	}
}
