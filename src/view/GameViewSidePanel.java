package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import listeners.GameStateChangeListener;
import shared.GameState;

public class GameViewSidePanel extends JPanel implements GameStateChangeListener {

	private static final long serialVersionUID = 4007259948559007014L;
	
	private JLabel currentPlayerLabel;
	public JLabel getCurrentPlayerLabel() {
		return currentPlayerLabel;
	}
	
	private JLabel contextLabel;
	public JLabel getContextLabel() {
		return contextLabel;
	}
	
	private JButton objectivesButton;
	public JButton getObjectivesButton() {
		return objectivesButton;
	}
	
	private JButton attackButton;
	public JButton getAttackButton() {
		return attackButton;
	}
		
	private JButton cancelButton;
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	private JButton tradeCardsButton;
	public JButton getTradeCardsButton() {
		return tradeCardsButton;
	}
	
	private JButton finishTurnButton;
	public JButton getFinishTurnButton() {
		return finishTurnButton;
	}
	
	private JButton positionGlobalTroopsButton;
	public JButton getPositionGlobalTroopsButton() {
		return positionGlobalTroopsButton;
	}
	
	private JButton positionContinentalTroopsButton;
	public JButton getPositionContinentalTroopsButton() {
		return positionContinentalTroopsButton;
	}

	/**
	 * Controls whether the action mode is enabled or not.
	 * 
	 * When action mode is enabled, all buttons are disabled except for cancel button.
	 * When action mode is disabled, all buttons are enabled except for cancel button.
	 * 
	 */
	public void setActionModeEnabled(boolean enabled) {
		objectivesButton.setEnabled(!enabled);
		attackButton.setEnabled(!enabled);
		tradeCardsButton.setEnabled(!enabled);
		finishTurnButton.setEnabled(!enabled);
		positionGlobalTroopsButton.setEnabled(!enabled);
		positionContinentalTroopsButton.setEnabled(!enabled);
		
		cancelButton.setEnabled(enabled);
	}
	
	private void generate() {
		setLayout(new GridLayout(10, 1));

		currentPlayerLabel = new JLabel();
		add(currentPlayerLabel);
		
		contextLabel = new JLabel();
		add(contextLabel);
		
		objectivesButton = new JButton("Ver objetivos");
		add(objectivesButton);
		
		positionGlobalTroopsButton = new JButton("Posicionar exércitos globais");
		add(positionGlobalTroopsButton);
		
		positionContinentalTroopsButton = new JButton("Posicionar exércitos continentais");
		add(positionContinentalTroopsButton);
		
		attackButton = new JButton("Atacar");
		add(attackButton);
		
		tradeCardsButton = new JButton("Troca de cartas");
		add(tradeCardsButton);
		
		finishTurnButton = new JButton("Finalizar Turno");
		add(finishTurnButton);
		
		cancelButton = new JButton("Cancelar");
		add(cancelButton);
		
		contextLabel.setText("");
		setActionModeEnabled(false);
	}
	
	public GameViewSidePanel() {
		generate();
	}

	@Override
	public void onGameStateChanged(GameState newCtx) {
		switch (newCtx) {
		case ArmyDistribution:
			getAttackButton().setEnabled(false);
			getPositionContinentalTroopsButton().setEnabled(true);
			break;
			
		case PlayerAction:
			getAttackButton().setEnabled(true);
			getPositionContinentalTroopsButton().setEnabled(false);
			break;
			
		default:
			break;
		}		
	}
}

class FinishButtonHandler {
	public void onFinish() {
	}
}