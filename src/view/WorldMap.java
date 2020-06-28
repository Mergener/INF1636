package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JLabel;

import model.WarGame;
import shared.Point;
import shared.PolygonUtility;

public class WorldMap {

	private int mapResX;
	private int mapResY;
	
	private WarGame warGame;
	private LayeredImageDrawer layeredImageDrawer;
	
	private class TerritoryVertices {
		public final Point[] vertices;
		public final String name;
		
		public TerritoryVertices(String name, Point[] vertices) {
			this.name = name;
			this.vertices = vertices;
		}
	}
	private ArrayList<TerritoryVertices> territoriesVertices = new ArrayList<TerritoryVertices>();	
	
	private void fetchTerritoryVertices(List<String> territoryNames) {
		for (String s : territoryNames) {
			territoriesVertices.add(new TerritoryVertices(s, warGame.getTerritoryVertices(s)));
		}
	}
	
	private void generateTerritoryLabels(List<String> territoryNames) {
		for (String s : territoryNames) {
			shared.Point center = warGame.getTerritoryCenter(s);
			
			int x = (int)(center.x * mapResX);
			int y = (int)(center.y * mapResY);
			
			JLabel l1 = new JLabel(s);
			//JLabel l2 = new JLabel(s);
			
			layeredImageDrawer.add(l1);
			//layeredImageDrawer.add(l2);
						
			Dimension dim = l1.getPreferredSize();
			l1.setBounds(x, y, (int)dim.getWidth(), (int)dim.getHeight());
			//l2.setBounds(x - 1, y + 1, (int)dim.getWidth(), (int)dim.getHeight());
						
			l1.setVisible(true);
			//l2.setVisible(true);
		}
		
		layeredImageDrawer.setPreferredSize(new Dimension(mapResX, mapResY));
		
		layeredImageDrawer.revalidate();
	}
	
	public void addMouseListener(MouseListener l) {
		layeredImageDrawer.addMouseListener(l);
	}

	public String getTerritoryNameInPoint(Point p) {
		Point point = new Point((float)p.x / mapResX, (float)p.y / mapResY);
		
		for (int i = 0; i < territoriesVertices.size(); ++i) {
			TerritoryVertices t = territoriesVertices.get(i);
			
			if (PolygonUtility.pointInsidePolygon(t.vertices, point)) {
				return t.name;
			}
		}
		return null;
	}
	
	public void attachTo(Container container) {
		
		container.add(layeredImageDrawer);
		container.revalidate();
	}
	
	public void deattachFrom(Container container) {
		container.remove(layeredImageDrawer);
	}
	
	public WorldMap(WarGame game) throws IOException {
		this.warGame = game;
		this.layeredImageDrawer = new LayeredImageDrawer();
				
		BufferedImage fg = ImageIO.read(getClass().getResource(game.getWorldMapForegroundPath()));
		BufferedImage bg = ImageIO.read(getClass().getResource(game.getWorldMapBackgroundPath()));
		
		this.mapResX = fg.getWidth();
		this.mapResY = fg.getHeight();
		
		layeredImageDrawer.addImageLayer(bg);
		layeredImageDrawer.addImageLayer(fg);
		
		List<String> territoryNames = warGame.getAllTerritoryNames();
		generateTerritoryLabels(territoryNames);
		fetchTerritoryVertices(territoryNames);
		
		this.layeredImageDrawer.setLayout(null);
	}
}
