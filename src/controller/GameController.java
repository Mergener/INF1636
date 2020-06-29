package controller;

import java.util.ArrayList;

import exceptions.InvalidAttack;
import exceptions.PlayerNotFound;
import listeners.GameContextChangeListener;
import model.WarGame;
import shared.AttackSummary;
import shared.GameState;
import shared.PlayerColor;

public class GameController implements GameContextChangeListener {

	private WarGame game;
	private GameState gameState;
	private ArrayList<PlayerColor> allPlayers = new ArrayList<PlayerColor>();
	private PlayerColor currentPlayerColor;
	
	public PlayerColor getCurrentPlayerColor() {
		return currentPlayerColor;
	}
	
	public AttackSummary requestAttack(String sourceTerritory, String targetTerritory) throws InvalidAttack {
		try {
			AttackSummary summary = game.performAttack(currentPlayerColor, sourceTerritory, targetTerritory);
			return summary;
		} catch (PlayerNotFound ex) {
			// If this exception is ever thrown here, this means that we have encountered a logic error.
			return null;
		}
	}
	
	public void finishPlayerTurn() {
		
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
