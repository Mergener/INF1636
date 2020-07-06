package view;

import java.util.Hashtable;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import listeners.TerritoryListener;
import model.WarGame;
import shared.PlayerColor;
import shared.Point;
import shared.PolygonUtility;

public class WorldMap implements TerritoryListener {

	private int mapResX;
	private int mapResY;
	
	private WarGame warGame;
	private LayeredImageDrawer layeredImageDrawer;
		
	private class TerritoryMap {
		public final Point[] vertices;
		public final String territoryName;
		private JLabel bgLabel; // Drop shadow label
		private JLabel fgLabel; // The actual label
		
		private String htmlColor = "\"white\"";
		private int soldierCount = 0;
		
		private void updateLabelText() {
			String innerText = String.format("%s<br>(%d)", territoryName, soldierCount);
						
			fgLabel.setText(String.format("<html><div align=\"center\"><font color=" + htmlColor + ">%s</font></div></html>", innerText));		
			bgLabel.setText(String.format("<html><div align=\"center\"><font color=\"black\">%s</font></div></html>", innerText));
			
			Dimension dim = bgLabel.getPreferredSize();
			fgLabel.setBounds(fgLabel.getX(), fgLabel.getY(), (int)dim.getWidth(), (int)dim.getHeight());
			bgLabel.setBounds(bgLabel.getX(), bgLabel.getY(), (int)dim.getWidth(), (int)dim.getHeight());			
		}
		
		public void updateOwnerColor(PlayerColor color) {
			htmlColor = color.getHTMLHexString();
						
			updateLabelText();
		}
		
		public void updateSoldierCount(int newSoldierCount) {
			soldierCount = newSoldierCount;
			
			updateLabelText();
		}
				
		public TerritoryMap(String territoryName, Point[] vertices, JLabel fgLabel, JLabel bgLabel) {
			this.territoryName = territoryName;
			this.vertices = vertices;			
			this.fgLabel = fgLabel;	
			this.bgLabel = bgLabel;			
			
			updateLabelText();
		}
	}
	private Hashtable<String, TerritoryMap> territoriesMaps = new Hashtable<String, TerritoryMap>();	
		
	private void setupTerritoryMaps(List<String> territoryNames) {
		for (String s : territoryNames) {			
			shared.Point center = warGame.getTerritoryCenter(s);
			
			int x = (int)(center.x * mapResX);
			int y = (int)(center.y * mapResY);
			
			JLabel l1 = new JLabel();
			JLabel l2 = new JLabel();
			
			l1.setLocation(x, y);
			l2.setLocation(x - 1, y + 1);
			
			layeredImageDrawer.add(l1);
			layeredImageDrawer.add(l2); 
			
			TerritoryMap m = new TerritoryMap(s, warGame.getTerritoryVertices(s), l1, l2);			
			territoriesMaps.put(s, m);
			m.updateOwnerColor(warGame.getTerritoryOwnerColor(s));
			m.updateSoldierCount(warGame.getTerritorySoldierCount(s));
						
			l1.setVisible(true);
			l2.setVisible(true);
			
			warGame.addTerritoryListener(s, this);
		}
		
		layeredImageDrawer.setPreferredSize(new Dimension(mapResX, mapResY));
		
		layeredImageDrawer.revalidate();
	}
	
	public void addMouseListener(MouseListener l) {
		layeredImageDrawer.addMouseListener(l);
	}

	public String getTerritoryNameInPoint(Point p) {
		Point point = new Point((float)p.x / mapResX, (float)p.y / mapResY);
		
		for (TerritoryMap t : territoriesMaps.values()) {			
			if (PolygonUtility.pointInsidePolygon(t.vertices, point)) {
				return t.territoryName;
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
	
	/**
	 * Generates a world map based on top of the specified game's current context.
	 */
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
		setupTerritoryMaps(territoryNames);
		
		this.layeredImageDrawer.setLayout(null);
	}

	@Override
	public void onTerritoryOwnershipChange(String territoryName, PlayerColor newOwnerColor) {
		territoriesMaps.get(territoryName).updateOwnerColor(newOwnerColor);
	}

	@Override
	public void onTerritorySoldierCountChange(String territoryName, int newSoldierCount) {
		territoriesMaps.get(territoryName).updateSoldierCount(newSoldierCount);
	}
}
