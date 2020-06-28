package view;

import model.WarGame;
import shared.Point;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import exceptions.NotEnoughPlayers;
import java.io.IOException;

public class GameView extends View {

	private WarGame warGame;
	private WorldMap worldMap;
	private GameViewSidePanel sidePanel;
	
	@Override
	protected void onEnter(View previousView) {
		try {
			warGame.start();
			
			JFrame frame = getWindow().getFrame();
			Container contentPane = frame.getContentPane();
			
			frame.setLayout(new FlowLayout());
			
			sidePanel = new GameViewSidePanel(warGame);
			
			worldMap = new WorldMap(warGame);
			
			worldMap.attachTo(contentPane);
			worldMap.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String tName = worldMap.getTerritoryNameInPoint(new Point(e.getX(), e.getY()));
					
					if (tName != null) {
						onMapTerritoryClicked(tName);
					}
				}
			});
			
			sidePanel.getObjectivesButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {					
					PlayerObjectivesWindow playerObjectivesWindow = new PlayerObjectivesWindow(warGame, getWindow());
					
					playerObjectivesWindow.start();
				}
			});
						
			contentPane.add(sidePanel);
			
			frame.revalidate();
			frame.repaint();
			
			frame.pack();			
			
		} catch (IOException ex) {
			System.err.println("Error loading game background image.");
			ex.printStackTrace();
			
		} catch (NotEnoughPlayers e) {
			// Nothing to be done
		}
	}
	
	protected void onMapTerritoryClicked(String territoryName) {
		
	}

	@Override
	protected void onExit(View nextView) {
	}
	
	public GameView(Window window, WarGame game) {
		super(window);
		this.warGame = game;
	}
}
