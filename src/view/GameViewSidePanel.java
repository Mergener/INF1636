package view;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

import controller.GameController;
import controller.GameState;
import exceptions.PlayerNotFound;
import listeners.ICurrentPlayerChangeListener;
import listeners.IGameStateChangeListener;
import shared.PlayerColor;

public class GameViewSidePanel extends JPanel implements IGameStateChangeListener, ICurrentPlayerChangeListener {

	private static final long serialVersionUID = 4007259948559007014L;
	
	private GameController controller;
	
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
	
	private JButton moveTroopsButton;
	public JButton getMoveTroopsButton() {
		return moveTroopsButton;
	}
	
	private JButton saveGameButton;
	public JButton getSaveGameButton() {
		return saveGameButton;
	}
	
	private ArrayList<JButton> disabledButtonsOnActionMode = new ArrayList<JButton>();

	/**
	 * Controls whether the action mode is enabled or not.
	 * 
	 * When action mode is enabled, all buttons are disabled except for cancel button.
	 * When action mode is disabled, all buttons that were disabled when the action mode was initialized
	 * become enabled again, and the cancel buttons gets disabled.
	 * 
	 */
	public void setActionModeEnabled(boolean enabled) {		
		if (enabled) {		
			if (objectivesButton.isEnabled()) {
				objectivesButton.setEnabled(false);
				disabledButtonsOnActionMode.add(objectivesButton);
			}
			if (attackButton.isEnabled()) {
				attackButton.setEnabled(false);
				disabledButtonsOnActionMode.add(attackButton);
			}
			if (tradeCardsButton.isEnabled()) {
				tradeCardsButton.setEnabled(false);
				disabledButtonsOnActionMode.add(tradeCardsButton);
			}
			if (finishTurnButton.isEnabled()) {
				finishTurnButton.setEnabled(false);
				disabledButtonsOnActionMode.add(finishTurnButton);
			}
			if (positionContinentalTroopsButton.isEnabled()) {
				positionContinentalTroopsButton.setEnabled(false);
				disabledButtonsOnActionMode.add(positionContinentalTroopsButton);
			}
			if (positionGlobalTroopsButton.isEnabled()) {
				positionGlobalTroopsButton.setEnabled(false);
				disabledButtonsOnActionMode.add(positionGlobalTroopsButton);
			}
			
			cancelButton.setEnabled(true);
		} else {
			for (int i = 0; i < disabledButtonsOnActionMode.size(); ++i) {
				disabledButtonsOnActionMode.get(i).setEnabled(true);
			}
			
			cancelButton.setEnabled(false);
			
			disabledButtonsOnActionMode.clear();
		}
	}
	
	private void generate() {
		setLayout(new GridLayout(12, 1));

		currentPlayerLabel = new JLabel();
		add(currentPlayerLabel);
		
		contextLabel = new JLabel();
		add(contextLabel);
		
		objectivesButton = new JButton("Ver objetivos");
		add(objectivesButton);

		tradeCardsButton = new JButton("Ver cartas");
		add(tradeCardsButton);
		
		positionGlobalTroopsButton = new JButton("Posicionar exércitos globais");
		add(positionGlobalTroopsButton);
		positionGlobalTroopsButton.setEnabled(false);
		
		positionContinentalTroopsButton = new JButton("Posicionar exércitos continentais");
		add(positionContinentalTroopsButton);
		positionContinentalTroopsButton.setEnabled(false);
		
		attackButton = new JButton("Atacar");
		add(attackButton);
		attackButton.setEnabled(false);
		
		moveTroopsButton = new JButton("Mover tropas");
		add(moveTroopsButton);
		moveTroopsButton.setEnabled(false);
		
		finishTurnButton = new JButton("Finalizar Jogada");
		add(finishTurnButton);
		
		cancelButton = new JButton("Cancelar");
		add(cancelButton);
		
		saveGameButton = new JButton("Salvar jogo");
		add(saveGameButton);
		
		contextLabel.setText("");
		setActionModeEnabled(false);
	}
	
	public GameViewSidePanel(GameController controller) {
		this.controller = controller;
		
		generate();

		controller.addCurrentPlayerChangeListener(this);
		controller.addGameStateChangeListener(this);
		
		onGameStateChanged(controller.getGameState());
		onCurrentPlayerChanged(controller.getCurrentPlayerColor());
	}
	
	private GameState previousState = null;

	@Override
	public void onGameStateChanged(GameState newCtx) {	
		if (newCtx == previousState) {
			return;
		}
		
		// A new game state has taken place, activate correct buttons accordingly.
		switch (newCtx) {
		case ArmyDistribution:
			getPositionContinentalTroopsButton().setEnabled(true);
			getPositionGlobalTroopsButton().setEnabled(true);
			break;
			
		case Combat:
			getAttackButton().setEnabled(true);
			break;
			
		case ArmyMovement:
			getMoveTroopsButton().setEnabled(true);
			break;
			
		default:
			break;
		}	
		
		// For the game state that has just finished, deactivate its buttons accordingly.
		if (previousState != null) {
			switch (previousState) {				
			case ArmyDistribution:
				getPositionContinentalTroopsButton().setEnabled(false);
				getPositionGlobalTroopsButton().setEnabled(false);				
				break;
				
			case Combat:
				getAttackButton().setEnabled(false);
				break;
				
			case ArmyMovement:
				getMoveTroopsButton().setEnabled(false);
				break;
				
			default:
				break;
			}
		}
		
		previousState = newCtx;	
	}
	

	@Override
	public void onCurrentPlayerChanged(PlayerColor newPlayerColor) {
		try {
			getCurrentPlayerLabel().setText(String.format("Vez de: %s (%s)", controller.getWarGame().getPlayerName(newPlayerColor), newPlayerColor.toString()));
		} catch (PlayerNotFound e) {
			// Ignore
			e.printStackTrace();
		}
	}
}