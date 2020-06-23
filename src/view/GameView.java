package view;

import model.WarGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameView extends View {

	private WarGame warGame;
	
	@Override
	protected void onEnter(View previousView) {
		try {
			warGame.start();
			
			JFrame frame = getWindow().getFrame();
			Container contentPane = frame.getContentPane();
			
			LayeredImageDrawer imgDrawer = new LayeredImageDrawer();
			
			BufferedImage fg = ImageIO.read(getClass().getResource("/images/war_tabuleiro_mapa copy.png"));
			BufferedImage bg = ImageIO.read(getClass().getResource("/images/war_tabuleiro_fundo.png"));
			imgDrawer.addImageLayer(bg);
			imgDrawer.addImageLayer(fg);
						
			contentPane.add(imgDrawer);
			
			JButton objectivesButton = new JButton("View Objectives");
			objectivesButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {					
					PlayerObjectivesWindow playerObjectivesWindow = new PlayerObjectivesWindow(warGame, getWindow());
					
					playerObjectivesWindow.start();
				}
			});
			
			contentPane.add(objectivesButton);
			
			contentPane.revalidate();
			contentPane.repaint();
			
			frame.pack();
			
		} catch (IOException ex) {
			System.err.println("Error loading game background image.");
		}
	}

	@Override
	protected void onExit(View nextView) {
	}
	
	public GameView(Window window, WarGame game) {
		super(window);
		this.warGame = game;
	}
}
