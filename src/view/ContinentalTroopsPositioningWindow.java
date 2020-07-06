package view;

import javax.swing.*;

import controller.GameController;
import exceptions.InvalidContinentalSoldierExpenditure;
import exceptions.PlayerNotFound;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.WarGame;
import shared.PlayerColor;

public class ContinentalTroopsPositioningWindow extends Window {

	private GameController controller;
	private Window previousWindow;
	private String territoryName;
	
	private int getAvailableTroopsCount() {
		PlayerColor p = controller.getCurrentPlayerColor();
		try {
			return controller.getWarGame().getPlayerUnspentContinentalSoldierCount(p, controller.getWarGame().getTerritoryContinentName(territoryName));
		} catch (PlayerNotFound e) {
			// Ignore - player is fetched from the controller.
			return 0;
		}
	}
		
	public ContinentalTroopsPositioningWindow(String territoryName, GameController controller, Window previousWindow) {
		this.territoryName = territoryName;
		this.controller = controller;
		this.previousWindow = previousWindow;
	}

	@Override
	protected void onClose() {
		
	}
	
	@Override
	protected void onStart() {		
		JFrame frame = getFrame();
		
		frame.setLocationRelativeTo(null);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2, 1));
		
		previousWindow.getFrame().setEnabled(false);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
                previousWindow.getFrame().setEnabled(true);
            }
		});
				
		int available = getAvailableTroopsCount();
		Integer[] options = new Integer[available + 1];
		for (int i = 0; i <= available; ++i) {
			options[i] = i;
		}
		
		JComboBox<Integer> troopsCountComboBox = new JComboBox<Integer>(options);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JLabel label = new JLabel(String.format("Selecione o número de exércitos para posicionar em %s:", territoryName));
		
		JButton button = new JButton("Confirmar");
		
		button.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent ev) {
				int count = (Integer)troopsCountComboBox.getSelectedItem();
				if (count != 0) {
					try {
						controller.spendContinentalSoldiers(territoryName, count);
						close();
					} catch (InvalidContinentalSoldierExpenditure e) {
						JOptionPane.showMessageDialog(getFrame(), String.format("Não foi possível fazer posicionamento das tropas continentais: %s", e.getMessage()));
					}	
				} else {
					close();
				}
			}
		});
		
		panel.add(troopsCountComboBox);
		panel.add(button);
		contentPane.add(label);
		contentPane.add(panel);
		
		frame.setResizable(false);
		
		frame.setSize(400, 200);
		
		frame.revalidate();
	}
}
