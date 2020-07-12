package view;

import javax.swing.*;

import controller.GameController;

import java.awt.*;

public class MainWindow extends Window {
	
	private GameController gameController;
	
	@Override
	public void onStart() {		
		JFrame frame = getFrame();
		
		frame.setTitle("War");
		frame.setPreferredSize(new Dimension(1280, 720));
		frame.setLocationRelativeTo(null);			
		
		setCurrentView(new PlayerRegistrationView(this, gameController));
		
		frame.revalidate();
		frame.pack();
	}
	
	public MainWindow(GameController gc) {
		this.gameController = gc;
	}
}
