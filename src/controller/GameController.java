package controller;

import exceptions.InvalidAttack;
import listeners.GameContextChangeListener;
import model.WarGame;
import shared.AttackSummary;
import shared.GameState;

public class GameController implements GameContextChangeListener {

	private WarGame game;
	private GameState gameState;
	
	public AttackSummary requestAttack(String sourceTerritory, String targetTerritory) throws InvalidAttack {
		return game.performAttack(sourceTerritory, targetTerritory);
	}
	
	public GameController(WarGame game) {
		this.game = game;
		this.gameState = game.getCurrentState();
	}

	@Override
	public void onGameStateChanged(GameState newState) {
		gameState = newState;	
	}
}
