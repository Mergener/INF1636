package view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends Window {
	@Override
	public void onStart() {		
		JFrame frame = getFrame();
		
		frame.setTitle("War");
		frame.setPreferredSize(new Dimension(1280, 720));
		frame.setLocationRelativeTo(null);			
		
		setCurrentView(new PlayerRegistrationView(this));
		
		frame.getContentPane().setLayout(new FlowLayout());	
		frame.revalidate();
		frame.pack();
	}
}
