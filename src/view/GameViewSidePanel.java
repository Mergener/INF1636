package view;

import java.awt.GridLayout;

import javax.swing.*;

import listeners.GameContextChangeListener;
import model.WarGame;
import shared.GameContext;

public class GameViewSidePanel extends JPanel implements GameContextChangeListener {

	private static final long serialVersionUID = 4007259948559007014L;
	
	private WarGame game;
		
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
	
	private void generate() {
		setLayout(new GridLayout(10, 1));
		objectivesButton = new JButton("View Objectives");
		add(objectivesButton);
	}
	
	public GameViewSidePanel(WarGame game) {
		this.game = game;
		generate();
	}

	@Override
	public void onGameContextChanged(GameContext newCtx) {
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
