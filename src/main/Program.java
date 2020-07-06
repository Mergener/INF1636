package main;

import controller.GameController;
import view.MainWindow;

public class Program {

	public static void main(String[] args) {
		GameController controller = new GameController();
		
		MainWindow mainWindow = new MainWindow(controller);
		mainWindow.start();
	}
	
}
