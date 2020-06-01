package view;

import javax.swing.JFrame;

public class MainWindow extends Window {
	@Override
	public void onStart() {
		JFrame frame = getFrame();
		
		frame.setTitle("War");
		frame.setSize(1280, 720);
		
		setCurrentView(new MainMenuView(this));
	}
}
