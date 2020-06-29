package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.WarGame;
import shared.PlayerColor;

public class PlayerObjectivesWindow extends Window {

	private WarGame game;
	private Window previousWindow;
	
	private JComboBox colorsComboBox;
	
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
		
		colorsComboBox = new JComboBox(playerColors);		
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
					
					StringBuilder sb = new StringBuilder();
					sb.append("Um erro ocorreu ao tentar exibir o objetivos dos players:\n");
					sb.append(ex.getClass().getName());
					StackTraceElement[] stack = ex.getStackTrace();
					
					for (int i = 0; i < stack.length; ++i) {
						sb.append("\n");
						sb.append(stack[i].toString());
					}
					
					sb.append(ex.getStackTrace());
					JOptionPane.showMessageDialog(getFrame(), sb.toString());
				}
			}
		});
		
		contentPane.add(button);
		
		frame.setResizable(false);
		
		frame.setSize(200, 75);
		
		frame.revalidate();
	}
}
