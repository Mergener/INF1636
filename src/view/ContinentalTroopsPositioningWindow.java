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
		
		// Make previous window uninteractable until this window gets closed		
		previousWindow.getFrame().setEnabled(false);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
                previousWindow.getFrame().setEnabled(true);
            }
		});
				
		// Generate a combo box that contains all possible options of soldier counts
		// to be placed in that specific territory. This number is simply equal to the player's
		// amount of unspent continental soldiers in the specified territory's continent.
		// Since the number of available continental soldiers in the specified territory's continent
		// for that player can be zero, we need to check for those cases and handle them by 
		// showing a descriptive message for the player and not generating the rest of the window.
		int available = getAvailableTroopsCount();
		if (available == 0) {
			JLabel label = new JLabel(String.format("Você não possui tropas continentais em %s (continente de %s).", 
					controller.getWarGame().getTerritoryContinentName(territoryName),
					territoryName));
			
			JButton button = new JButton("Ok");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					close();
				}
			});
			
			frame.add(label);
			frame.add(button);
			
			frame.setLayout(new FlowLayout());
			
			button.setSize(new Dimension(100, 20));
			frame.pack();
			frame.revalidate();
			
			return;
		}
		
		Integer[] options = new Integer[available];
		for (int i = 0; i < available; ++i) {
			options[i] = i + 1;
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
