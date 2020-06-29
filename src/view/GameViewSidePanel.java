package view;

import java.awt.GridLayout;

import javax.swing.*;

import listeners.GameContextChangeListener;
import model.WarGame;
import shared.GameState;

public class GameViewSidePanel extends JPanel implements GameContextChangeListener {

	private static final long serialVersionUID = 4007259948559007014L;
	
	private GameView gameView;
		
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
		
		cancelButton.setEnabled(enabled);
	}
	
	private void generate() {
		setLayout(new GridLayout(10, 1));
		
		contextLabel = new JLabel();
		add(contextLabel);
		
		objectivesButton = new JButton("Ver objetivos");
		add(objectivesButton);
		
		attackButton = new JButton("Atacar");
		add(attackButton);
		
		cancelButton = new JButton("Cancelar");
		add(cancelButton);
		
		setActionModeEnabled(false);
	}
	
	public GameViewSidePanel(GameView gameView) {
		this.gameView = gameView;
		generate();
	}

	@Override
	public void onGameStateChanged(GameState newCtx) {
		switch (newCtx) {
		case ArmyDistribution:
			getAttackButton().setEnabled(false);
			break;
			
		case PlayerAction:
			getAttackButton().setEnabled(true);
			break;
		}		
	}
}
