package view;

import java.util.ArrayList;
import javax.swing.*;

import exceptions.BadPlayerColorUsage;
import exceptions.GameAlreadyStarted;
import model.WarGame;
import shared.PlayerColor;

import java.awt.*;
import java.awt.event.*;

public class PlayerRegistrationView extends View {
	private static final String[] PLAYER_COLORS =  {"Azul", "Vermelho", "Amarelo", "Verde", "Preto", "Branco"};
	
	private static final int MAX_PLAYER_COUNT = 6;
	private static final int MIN_PLAYER_COUNT = 3;
	
	private int requestedPlayerCount = MIN_PLAYER_COUNT;
	
	private WarGame warGame;
	
	private class PlayerSlot {
		public JPanel panel;
		public JLabel label;
		public JComboBox colorComboBox;
		public JTextField nameTextField;
	}
	
	private ArrayList<PlayerSlot> playerSlots = new ArrayList<PlayerSlot>();
	
	private void generatePlayerCountComboBox(Container c) {
		String[] options = new String[MAX_PLAYER_COUNT - MIN_PLAYER_COUNT + 1];
		
		for (int i = 0; i < options.length; ++i) {
			options[i] = String.format("%d", i + MIN_PLAYER_COUNT);
		}

		JLabel label = new JLabel("Insira o número de jogadores:");
		
		JComboBox playerCountComboBox = new JComboBox(options);
		playerCountComboBox.setSelectedIndex(requestedPlayerCount - MIN_PLAYER_COUNT);
		
		playerCountComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					int index = playerCountComboBox.getSelectedIndex();
					
					requestedPlayerCount = index + MIN_PLAYER_COUNT;
	
					refreshView();
				}
			}
		});
		
		c.add(label);
		c.add(playerCountComboBox);
	}
	
	private void clearPlayerSlotsPanels(Container c) {
		playerSlots.clear();
	}
	
	private void generatePlayerSlotsPanels(Container c) {
		for (int i = 0; i < requestedPlayerCount; ++i) {
			PlayerSlot slot = new PlayerSlot();
			
			slot.panel = new JPanel();
			slot.label = new JLabel();
			slot.colorComboBox = new JComboBox(PLAYER_COLORS);
			slot.nameTextField = new JTextField();
					
			slot.nameTextField.setPreferredSize(new Dimension(150, 20));
			slot.label.setText(String.format("Informações do jogador %d : ", i + 1));
					
			slot.panel.add(slot.label);
			slot.panel.add(slot.colorComboBox);
			slot.panel.add(slot.nameTextField);
			
			c.add(slot.panel);		
			 
			playerSlots.add(slot);
		}
	}
	
	private void generateProcceedButton(Container c) {
		JButton button = new JButton("Prosseguir");
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				warGame = new WarGame();
				
				// Register all players to the war game:
				try {
					for (int i = 0; i < playerSlots.size(); ++i) {
						PlayerSlot slot = playerSlots.get(i);
						
						String playerName = slot.nameTextField.getText();
						PlayerColor playerColor = PlayerColor.getColorByName((String)slot.colorComboBox.getSelectedItem());
						
						System.out.printf("Jogador %d: %s (cor %s)\n", i + 1, playerName, playerColor.toString());
						
						warGame.registerPlayer(playerName, playerColor);						
					}
				} catch (BadPlayerColorUsage e) {
					JOptionPane.showMessageDialog(getWindow().getFrame(), "Não pode haver mais de um jogador utilizando a mesma cor.");
					return;
				} catch (GameAlreadyStarted e) {
					// Nothing to be done
				}
				
				getWindow().setCurrentView(new GameView(getWindow(), warGame));
			}
		});
		
		c.add(button);
	}
	
	private void refreshView() {
		Container contentPane = getWindow().getFrame().getContentPane();
		
		contentPane.removeAll();
		
		clearPlayerSlotsPanels(contentPane);
		
		generatePlayerCountComboBox(contentPane);
		generatePlayerSlotsPanels(contentPane);
		generateProcceedButton(contentPane);
		
		contentPane.revalidate();
		contentPane.repaint();
	}

	@Override
	protected void onEnter(View previousView) {
		refreshView();
	}

	@Override
	protected void onExit(View nextView) {
	}

	public PlayerRegistrationView(Window window) {
		super(window);
	}
}
