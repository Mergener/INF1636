package controller;

import java.util.ArrayList;
import java.util.Random;

import exceptions.BadPlayerColorUsage;
import exceptions.GameAlreadyStarted;
import exceptions.InvalidAttack;
import exceptions.InvalidContinentalSoldierExpenditure;
import exceptions.NotEnoughPlayers;
import exceptions.PlayerNotFound;
import listeners.CurrentPlayerChangeListener;
import listeners.GameStateChangeListener;
import model.WarGame;
import shared.AttackSummary;
import shared.GameState;
import shared.PlayerColor;

/**
 * Keeps track of the current state of a war game, including each players' turn, and manipulates it. 
 */
public class GameController {

	private WarGame game;
	private ArrayList<PlayerColor> players = new ArrayList<PlayerColor>();
	
	private GameState gameState;
	private ArrayList<GameStateChangeListener> gameStateChangeListeners = new ArrayList<GameStateChangeListener>();
	
	private int currentPlayerIndex;

	public PlayerColor getCurrentPlayerColor() {
		return players.get(currentPlayerIndex);
	}
	
	private void setCurrentPlayerIndex(int index) {
		if (index == currentPlayerIndex) {
			return;
		}
		
		this.currentPlayerIndex = index;
		for (int i = 0; i < currentPlayerChangeListeners.size(); ++i) {
			currentPlayerChangeListeners.get(i).onCurrentPlayerChanged(getCurrentPlayerColor());
		}		
	}
	
	private ArrayList<CurrentPlayerChangeListener> currentPlayerChangeListeners = new ArrayList<CurrentPlayerChangeListener>(); 
	public void addCurrentPlayerChangeListener(CurrentPlayerChangeListener l) {
		currentPlayerChangeListeners.add(l);
	}
	
	public void removeCurrentPlayerChangeListener(CurrentPlayerChangeListener l) {
		currentPlayerChangeListeners.remove(l);
	}
	
	public WarGame getWarGame() {
		return game;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	private void setGameState(GameState newState) {
		this.gameState = newState;
		
		for (int i = 0; i < gameStateChangeListeners.size(); ++i) {
			gameStateChangeListeners.get(i).onGameStateChanged(newState);
		}
	}
	
	public void registerPlayer(String name, PlayerColor color) throws BadPlayerColorUsage, GameAlreadyStarted {
		game.registerPlayer(name, color);
		players.add(color);
	}
	
	public void unregisterAllPlayers() throws GameAlreadyStarted {
		game.unregisterAllPlayers();
	}
	
	public void addGameStateChangeListener(GameStateChangeListener l) {
		gameStateChangeListeners.add(l);
	}
	
	public void removeGameStateChangeListener(GameStateChangeListener l) {
		gameStateChangeListeners.remove(l);
	}
	
	/**
	 * Randomly sorts players array.
	 */
	private void randomizePlayersArray() {
		Random random = new Random();
		
		for (int i = 0; i < players.size(); ++i) {
			int rand = random.nextInt(players.size());
			
			PlayerColor p = players.get(i);
			players.set(i, players.get(rand));
			players.set(rand, p);
		}
	}
	
	/**
	 * Triggers an attack from a territory to another.
	 * 
	 * The source territory must be owned by the current player and have more than 1 troop placed in it.
	 * The target territory must be connected to the source territory and cannot be owned by the same
	 * player that owns the source territory.
	 * 
	 * @param sourceTerritory The source territory.
	 * @param targetTerritory The target territory.
	 * @return A detailed summary of the attack outcome. @see AttackSummary for more details.
	 * @throws InvalidAttack Thrown if any of the conditions described above isn't met.
	 */
	public AttackSummary requestAttack(String sourceTerritory, String targetTerritory) throws InvalidAttack {
		try {
			AttackSummary summary = game.performAttack(getCurrentPlayerColor(), sourceTerritory, targetTerritory);
			return summary;
		} catch (PlayerNotFound ex) {
			// Ignore
			return null;
		}
	}
	
	/**
	 * Spends the current player's continental soldiers in the specified territory.
	 * 
	 * @param territoryName The territory to spend soldiers on.
	 * @param soldierCount The amount of soldiers to be spent.
	 * @return The remaining amount of continental soldiers for the player in that continent.
	 * @throws InvalidContinentalSoldierExpenditure Thrown if the player either doesn't own the specified territory or the requested
	 * amount of soldiers surpass the number of unspent continental soldiers the current player currently has.
	 */
	public int spendContinentalSoldiers(String territoryName, int soldierCount) throws InvalidContinentalSoldierExpenditure {
		try {			
			game.spendPlayerContinentalSoldiers(getCurrentPlayerColor(), territoryName, soldierCount);
			
			return game.getPlayerUnspentContinentalSoldierCount(getCurrentPlayerColor(), game.getTerritoryContinentName(territoryName));
		} catch (PlayerNotFound e) {
			// Ignore
			return 0;
		}
	}
	
	/**
	 * Starts the game. At least 3 and at most 6 players must be registered before
	 * this gets called.
	 * 
	 * Once the game is started, new players cannot be registered anymore.
	 * This method will fire currentPlayerChange and gameStateChange events.
	 *  
	 * @throws NotEnoughPlayers Not enough players have been registered.
	 */
	public void startGame() throws NotEnoughPlayers {
		game.start();
		randomizePlayersArray();
		setCurrentPlayerIndex(0);
		setGameState(GameState.ArmyDistribution);
	}
	
	/**
	 * Finishes the current player's turn. This might trigger a game state change.
	 */
	public void finishPlayerTurn() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		
		if (currentPlayerIndex == 0) {
			switch (gameState) {
			
			case ArmyDistribution:
				setGameState(GameState.PlayerAction);
				break;

			case PlayerAction:
				setGameState(GameState.ArmyDistribution);				
				game.terminateRound();
				break;
				
			default:
				break;				
			}
		}
	}
	
	public GameController() {
		this.game = new WarGame();
		this.gameState = GameState.Nothing;		
	}
}
