package view;

import javax.swing.*;

import controller.GameController;

import java.awt.*;

public class MainWindow extends Window {
	
	
	@Override
	public void onStart() {		
		JFrame frame = getFrame();
		
		frame.setTitle("War");			
		
		setCurrentView(new MainMenuView(this));
		
		frame.revalidate();
		frame.pack();
	}
	
	public MainWindow() {
	}
}
