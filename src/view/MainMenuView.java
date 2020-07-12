package view;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class MainMenuView extends View {

	private JFormattedTextField playerCountBox;

	@Override
	protected void onEnter(View previousView) {
		JFrame frame = getWindow().getFrame();
		Container contentPane = frame.getContentPane();
			
		playerCountBox = new JFormattedTextField(NumberFormat.getNumberInstance());
		
		contentPane.add(playerCountBox);
	}

	@Override
	protected void onExit(View nextView) {		
	}

	public MainMenuView(Window window) {
		super(window);
	}
}