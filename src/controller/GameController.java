package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import exceptions.BadPlayerColorUsage;
import exceptions.GameAlreadyStarted;
import exceptions.IllegalCardsTrade;
import exceptions.IllegalMigration;
import exceptions.IllegalPlayEnd;
import exceptions.InvalidAttack;
import exceptions.InvalidContinentalSoldierExpenditure;
import exceptions.InvalidGlobalSoldierExpenditure;
import exceptions.NotEnoughPlayers;
import exceptions.PlayerNotFound;
import listeners.IAttackListener;
import listeners.ICurrentPlayerChangeListener;
import listeners.IGameStateChangeListener;
import model.WarGame;
import shared.AttackSummary;
import shared.PlayerColor;

/**
 * Keeps track of the current state of a war game, including each players' turn, and manipulates it. 
 */
public class GameController implements Serializable {

	private static final long serialVersionUID = -248882901782572751L;

	private ArrayList<PlayerColor> players = new ArrayList<PlayerColor>();
	
	private int currentPlayerIndex;
	public PlayerColor getCurrentPlayerColor() {
		return players.get(currentPlayerIndex);
	}
	
	private void setCurrentPlayerIndex(int index) {
		if (index == currentPlayerIndex) {
			return;
		}
		
		this.currentPlayerIndex = index;
		if (iCurrentPlayerChangeListeners != null) {
			for (int i = 0; i < iCurrentPlayerChangeListeners.size(); ++i) {
				iCurrentPlayerChangeListeners.get(i).onCurrentPlayerChanged(getCurrentPlayerColor());
			}		
		}
	}
	
	private GameState gameState;
	public GameState getGameState() {
		return gameState;
	}
	
	private void setGameState(GameState newState) {
		this.gameState = newState;
		
		if (iGameStateChangeListeners != null) {
			for (int i = 0; i < iGameStateChangeListeners.size(); ++i) {
				iGameStateChangeListeners.get(i).onGameStateChanged(newState);
			}
		}
	}
	
	private transient ArrayList<IGameStateChangeListener> iGameStateChangeListeners = new ArrayList<IGameStateChangeListener>();
	public void addGameStateChangeListener(IGameStateChangeListener l) {
		if (iGameStateChangeListeners == null) {
			iGameStateChangeListeners = new ArrayList<IGameStateChangeListener>();
		}
		
		iGameStateChangeListeners.add(l);
	}
	
	public void removeGameStateChangeListener(IGameStateChangeListener l) {
		if (iGameStateChangeListeners == null) {
			iGameStateChangeListeners = new ArrayList<IGameStateChangeListener>();
		}
		iGameStateChangeListeners.remove(l);
	}
	
	private transient ArrayList<ICurrentPlayerChangeListener> iCurrentPlayerChangeListeners = new ArrayList<ICurrentPlayerChangeListener>(); 
	public void addCurrentPlayerChangeListener(ICurrentPlayerChangeListener l) {
		if (iCurrentPlayerChangeListeners == null) {
			iCurrentPlayerChangeListeners = new ArrayList<ICurrentPlayerChangeListener>();
		}
		iCurrentPlayerChangeListeners.add(l);
	}
	
	public void removeCurrentPlayerChangeListener(ICurrentPlayerChangeListener l) {
		if (iCurrentPlayerChangeListeners == null) {
			iCurrentPlayerChangeListeners = new ArrayList<ICurrentPlayerChangeListener>();
		}
		iCurrentPlayerChangeListeners.remove(l);
	}

	private WarGame game;
	public WarGame getWarGame() {
		return game;
	}
	
	public void registerPlayer(String name, PlayerColor color) throws BadPlayerColorUsage, GameAlreadyStarted {
		game.registerPlayer(name, color);
		players.add(color);
	}
	
	public void unregisterAllPlayers() throws GameAlreadyStarted {
		game.unregisterAllPlayers();
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
	 * @return True if the current player must perform cards trade.
	 */
	public boolean cardsTradeRequired() {
		try {
			return game.getTerritoryCardsIdsOwnedByPlayer(getCurrentPlayerColor()).length >= 5;
		} catch (PlayerNotFound e) {
			// Ignore
			e.printStackTrace();
			return false;
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
			if (cardsTradeRequired()) {
				throw new InvalidAttack("Não é possível realizar ataque: ainda é necessário realizar uma troca de cartas.");
			}
			
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
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Spends the current player's global soldiers in the specified territory.
	 * 
	 * @param territoryName The territory to spend soldiers on.
	 * @param soldierCount The amount of soldiers to be spent.
	 * @return The remaining amount of global soldiers for the player.
	 * @throws InvalidGlobalSoldierExpenditure Thrown if the player either doesn't own the specified territory or the requested
	 * amount of soldiers surpass the number of unspent global soldiers the current player has.
	 */
	public int spendGlobalSoldiers(String territoryName, int soldierCount) throws InvalidGlobalSoldierExpenditure  {
		try {
			game.spendPlayerGlobalSoldiers(getCurrentPlayerColor(), territoryName, soldierCount);
			
			return game.getPlayerUnspentGlobalSoldierCount(getCurrentPlayerColor());
		} catch (PlayerNotFound e) {
			// Ignore
			e.printStackTrace();
			return 0;
		}
	}
	
	public void performCardTrade(int card1, int card2, int card3) throws IllegalCardsTrade {
		try {
			game.performCardsTrade(getCurrentPlayerColor(), card1, card2, card3);
		} catch (PlayerNotFound e) {
			// Ignore
			e.printStackTrace();
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
	 * Performs migration of soldier from a source territory to a target territory.
	 * 
	 * @param srcTerritory The source territory.
	 * @param targetTerritory The target territory.
	 * @param amount The amount of soldiers to migrate.
	 * @throws IllegalMigration If the migration cannot be done. Possible reasons are the two territories have
	 * different owners or the amount of soldiers surpass the amount of soldiers that can migrate to that specified territory.
	 */
	public void performMigration(String srcTerritory, String targetTerritory, int amount) throws IllegalMigration {
		game.performMigration(srcTerritory, targetTerritory, amount);
	}
	
	/**
	 * Finishes the current player's play. This might trigger a game state change and/or a current player change.
	 * @throws IllegalPlayEnd There are still pending actions to be performed by the current player before the current
	 * play can be finished.
	 */
	public void finishCurrentPlay() throws IllegalPlayEnd {
		String errMsg = "Não é possível finalizar a jogada:";
		boolean err = false;
		
		try {			
			switch (gameState) {
			
			case ArmyDistribution:
				// Can only go to the next turn if all the player's continental and global soldiers have been positioned.
				String[] continents = game.getAllContinentsNames();
				int count;
				
				for (int i = 0; i < continents.length; ++i) {
					count = game.getPlayerUnspentContinentalSoldierCount(getCurrentPlayerColor(), continents[i]); 
					if (count != 0) {
						err = true;
						errMsg += String.format("\n\tJogador não posicionou %d soldados continentais em %s", count, continents[i]);
					}
				}
				
				count = game.getPlayerUnspentGlobalSoldierCount(getCurrentPlayerColor());
				if (count != 0) {
					err = true;
					errMsg += String.format("\n\tJogador não posicionou %d soldados globais.", count);
				}
				
				if (err) {
					throw new IllegalPlayEnd(errMsg);
				} else {
					setGameState(GameState.Combat);
				}				
				
				break;
				
			case Combat:
				if (cardsTradeRequired()) {
					errMsg += "O jogador possui 5 cartas de território, é necessário realizar uma troca de cartas. Aperte o botão 'Ver cartas' para realizar a troca";
					throw new IllegalPlayEnd(errMsg);
				}
				
				setGameState(GameState.ArmyMovement);
				break;
				
			case ArmyMovement:
				int newPlayerIndex = (currentPlayerIndex + 1) % players.size();
				setCurrentPlayerIndex(newPlayerIndex);
				
				if (newPlayerIndex == 0) {
					game.terminateRound();
				}
				
				setGameState(GameState.ArmyDistribution);
				break;
				
			default:
				break;
			}
		} catch (PlayerNotFound e) {
			// Ignore
			e.printStackTrace();
		}
	}
	
	public void saveToStream(OutputStream stream) throws IOException {
		ObjectOutputStream objStream = new ObjectOutputStream(stream);
		
		try {
			objStream.writeObject(this);
		} catch (IOException ex) {
			throw ex;
		} finally {
			objStream.close();
		}		
	}
	
	public static GameController loadGameFromStream(InputStream stream) throws IOException, ClassNotFoundException {
		ObjectInputStream objStream = new ObjectInputStream(stream);
		
		try {
			GameController controller = (GameController)objStream.readObject();
			return controller;
		} catch(Exception ex) {
			throw ex;
		} finally {
			objStream.close();
		}
	}
	
	public GameController() {
		this.game = new WarGame();
		this.gameState = GameState.Nothing;		
	}

	public void addAttackListener(IAttackListener attackListener) {
		game.addAttackListener(attackListener);		
	}
}
