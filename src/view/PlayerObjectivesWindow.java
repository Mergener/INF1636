package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import data.PlayerColor;
import model.WarGame;

public class PlayerObjectivesWindow extends Window {

	private WarGame game;
	private Window previousWindow;
	
	private JComboBox<PlayerColor> colorsComboBox;
	
	public PlayerObjectivesWindow(WarGame game, Window previousWindow) {
		this.game = game;
		this.previousWindow = previousWindow;
	}

	@Override
	protected void onStart() {
		PlayerColor[] playerColors = game.getRegisteredPlayersColors();
		
		JFrame frame = getFrame();
		
		frame.setLocationRelativeTo(null);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		colorsComboBox = new JComboBox<PlayerColor>(playerColors);		
		contentPane.add(colorsComboBox);
		
		previousWindow.getFrame().setEnabled(false);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
                previousWindow.getFrame().setEnabled(true);
            }
		});
		
		JButton button = new JButton("View");
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				PlayerColor color = (PlayerColor)colorsComboBox.getSelectedItem();
				
				try {
					JOptionPane.showMessageDialog(getFrame(), String.format("%s", game.getPlayerObjectiveDescription(color)));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(getFrame(), "An error occurred when trying to display player objective.");
				}
			}
		});
		
		contentPane.add(button);
		
		frame.setResizable(false);
		
		frame.setSize(200, 75);
		
		frame.revalidate();
	}
}
