package view;

import javax.swing.*;

import controller.GameController;
import exceptions.IllegalMigration;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TroopMigrationWindow extends Window {

	private GameController controller;
	private Window previousWindow;
	private String sourceTerritoryName;
	private String targetTerritoryName;
			
	public TroopMigrationWindow(String sourceTerritoryName, String targetTerritoryName, GameController controller, Window previousWindow) {
		this.sourceTerritoryName = sourceTerritoryName;
		this.targetTerritoryName = targetTerritoryName;
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
		
		int available = controller.getWarGame().getMaximumEligibleMigrationTroopsCount(sourceTerritoryName);
		Integer[] options = new Integer[available];
		for (int i = 0; i < available; ++i) {
			options[i] = i + 1;
		}
		
		JComboBox<Integer> troopsCountComboBox = new JComboBox<Integer>(options);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JLabel label = new JLabel(String.format("Selecione o número de exércitos para migrar para %s:", targetTerritoryName));
		
		JButton button = new JButton("Confirmar");
		
		button.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent ev) {
				int count = (Integer)troopsCountComboBox.getSelectedItem();
				if (count != 0) {
					try {
						controller.performMigration(sourceTerritoryName, targetTerritoryName, count);
						close();
					} catch (IllegalMigration e) {
						JOptionPane.showMessageDialog(getFrame(), String.format("Não foi possível fazer migração das tropas: %s", e.getMessage()));
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
