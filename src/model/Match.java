package model;

import exceptions.NotEnoughPlayers;
import exceptions.InvalidAttack;
import listeners.AttackListener;
import shared.AttackSummary;
import shared.Dice;
import shared.Geometry;

import java.util.Collections;
import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

class Match {
	
	/* @brief Array that contains all players in the match. 
	 * 
	 * @remarks The order of players within this array matches the order
	 * that each player will play.
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private World world;
	
	private boolean started;
	public boolean hasStarted() {
		return started;
	}
		
	public World getWorld() {
		return world;
	}
	
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	public Match(List<Player> players, World world) throws NotEnoughPlayers {
		if (players.size() < 3 || players.size() > 6) {
			throw new NotEnoughPlayers("N�mero de jogadores inv�lido. Deve ser entre 3 e 6 jogadores.");
		}
		
		this.world = world;
		generateTerritoryCards();
		
		for (Player p : players) {
			this.players.add(p);
		}
	}
	
	private void giveRandomObjectivesToPlayers(List<IObjective> availableObjectives) {
		ArrayList<IObjective> objectives = new ArrayList<IObjective>(availableObjectives);
		Random random = new Random();
		List<Player> players = getPlayers();
		
		for (int i = 0; i < players.size(); ++i) {
			int rand = random.nextInt(objectives.size());
			players.get(i).setObjective(objectives.get(rand));
			objectives.remove(rand);
		}
	}

	private static final int JOKER_CARDS_COUNT = 2;
	
	private ArrayList<TerritoryCard> allCards = new ArrayList<TerritoryCard>();
	private Deque<TerritoryCard> unclaimedTerritoryCards = new LinkedList<TerritoryCard>();
	
	public void addTerritoryCard(TerritoryCard c) {
		unclaimedTerritoryCards.add(c);
	}
	
	public TerritoryCard getCardById(int id) {
		return allCards.get(id);
	}
	
	private void generateTerritoryCards() {
		for (Continent c : world.getContinents()) {
			for (Territory t : c.getTerritories()) {				
				TerritoryCard card = new TerritoryCard(allCards.size(), t.getName(), Geometry.getRandomExceptJoker(), t);				
				unclaimedTerritoryCards.addLast(card);
				allCards.add(card);
			}
		}
		
		for (int i = 0; i < JOKER_CARDS_COUNT; ++i) {
			TerritoryCard card = new TerritoryCard(allCards.size(), "Carta coringa.", Geometry.Joker, null);
			unclaimedTerritoryCards.addLast(card);
			allCards.add(card);
		}
	}
		
	private void giveRandomTerritoriesToPlayers() {
		List<Player> players = getPlayers();
		
		// The following hashtables links players to the number of territories they can still receive during
		// this phase of territory distribution.
		Hashtable<Player, Integer> playersTerritoryCount = new Hashtable<Player, Integer>();
				
		// The following array list contains all players who are still receiving territories during this phase of
		// territory distribution.
		ArrayList<Player> sortingPlayers = new ArrayList<Player>(players);
		Random random = new Random();
		
		int territoriesPerPlayer = world.getTerritoryCount() / players.size();
		int remainder = world.getTerritoryCount() % players.size();
		
		// The number of territories in the world might not be a multiple of the number of players in the match. 
		// Thus, considering the fact that all territories must have an owner, the remainder 'r' of the division is
		// distributed among the first 'r' players. This might not be the fairmost solution, but it is chosen since
		// this is the approach taken by the official War game.
		for (int i = 0; i < players.size(); ++i) {
			int amt = territoriesPerPlayer;
			
			if (remainder > 0) {
				amt++;
				remainder--;
			}
			
			playersTerritoryCount.put(players.get(i), amt);
		}
		
		// For every territory in the world, set its owner to a random player that is still
		// eligible for receiving more territories.
		for (Continent c : world.getContinents()) {
			for (Territory t : c.getTerritories()) {
				int rand = random.nextInt(sortingPlayers.size());
				
				Player p = sortingPlayers.get(rand);
				int val = playersTerritoryCount.get(p);
				
				if (val == 1) {
					// If val == 1, this is the last territory this player is getting.
					sortingPlayers.remove(rand);
				} else {
					playersTerritoryCount.replace(p, val - 1);
				}
				
				p.addTerritory(t);
				t.addSoldiers(1);
			}
		}
	}
	
	private ArrayList<AttackListener> attackListeners = new ArrayList<AttackListener>();
	
	public void addAttackListener(AttackListener l) {
		attackListeners.add(l);
	}
	
	public AttackSummary performAttack(Player player, Territory source, Territory target) throws InvalidAttack {
		if (!player.ownsTerritory(source)) {
			throw new InvalidAttack("Jogador tentou atacar partindo de territ�rio que n�o o pertence!");
		}
		if (!source.isNeighbourOf(target)) {
			throw new InvalidAttack("Jogador tentou atacar territ�rio n�o conectado � origem do ataque!");
		}
		if (target.getOwner() == player) {
			throw new InvalidAttack("Jogador tentou atacar o pr�prio territ�rio!");
		}
		int soldierCount = source.getSoldierCount() -1;
		if (soldierCount < 1) {
			throw new InvalidAttack("N�o � poss�vel realizar ataque vindo de territ�rio com apenas uma tropa.");
		}
		if (soldierCount > 3) {
			throw new InvalidAttack("N�o � poss�vel utilizar mais de tr�s soldados em um ataque!");
		}
		
		// Attack is valid, perform RNG to generate the results.
		int[] attackDices = Dice.roll(soldierCount);
		int[] defenseDices = Dice.roll(Math.min(3, target.getSoldierCount()));
		
		int j = defenseDices.length - 1;
		int i = attackDices.length - 1;
		
		int attackLoss = 0;
		int defenseLoss = 0;
		
		// Calculates the attack outcome.
		while (i >= 0 && j >= 0) {
			if (attackDices[i] > defenseDices[i]) {
				defenseLoss++;
			} else {
				attackLoss++;
			}
			
			j--; i--;
		}
		
		boolean territoryTaken = defenseLoss == target.getSoldierCount();
		
		// Generates the attack summary object.
		AttackSummary summary = new AttackSummary(source.getName(), target.getName(), defenseDices, attackDices, defenseLoss, attackLoss, territoryTaken);
		
		// Notifies all listeners.
		for (i = 0; i < attackListeners.size(); ++i) {
			attackListeners.get(i).onAttackPerformed(summary);
		}
		
		// Since the attack summary was generated, we can apply now the changes to the territories.
		target.removeSoldiers(summary.getDefenseLoss());

		if (territoryTaken) {
			player.addTerritory(target);

			// Player has taken the territory, add a new territory card for them.	
			player.addTerritoryCard(unclaimedTerritoryCards.pollFirst());
		}		 
		
		return summary;
	}
	
	private int tradeCounter = 0;
	
	private static int[] tradeBonusAmount = new int[] {
			4, 6, 8, 10, 12, 15
	};
	
	/**
	 * Returns true if the specified trio of cards is either made of three distinct cards or three
	 * equal cards. 
	 */
	private static boolean evaluateCardTrioBonus(TerritoryCard c1, TerritoryCard c2, TerritoryCard c3) {		
        if (c1.getGeometry() != c2.getGeometry() && c1.getGeometry() != c3.getGeometry()) {
            return true;
        }
        if (c1.getGeometry() == c2.getGeometry() && c1.getGeometry() == c3.getGeometry()) {
            return true;
        }
        return false;
    }
	
	public void performCardsTrade(Player player, TerritoryCard card1, TerritoryCard card2, TerritoryCard card3) {
		if (evaluateCardTrioBonus(card1, card2, card3)) {			
			if (player.ownsTerritory(card1.getTerritory())) {
				card1.getTerritory().addSoldiers(2);
			}
			if (player.ownsTerritory(card2.getTerritory())) {
				card2.getTerritory().addSoldiers(2);
			}
			if (player.ownsTerritory(card3.getTerritory())) {
				card3.getTerritory().addSoldiers(2);
			}
			
			int bonus;
			
			if (tradeCounter >= tradeBonusAmount.length) {
				bonus = tradeBonusAmount[tradeBonusAmount.length - 1] + (tradeCounter - tradeBonusAmount.length + 1) * 5;
			} else {
				bonus = tradeBonusAmount[tradeCounter];
			}	
			
			player.addSoldiers(bonus);

			tradeCounter++;
		}
	}
	
	public void terminateRound() {
		Continent[] continents = world.getContinents();
		
		for (int i = 0; i < players.size(); ++i) {
			for (int j = 0; j < continents.length; ++j) {
				Player p = players.get(i);
				Continent c = continents[j];
				
				if (p.hasEntireContinent(c)) {
					p.addContinentalSoldiers(c, c.getValue());
				}
			}
		}
	}
	
	public void start() {		
		giveRandomObjectivesToPlayers(DefaultObjectives.getAllDefaultObjectives(world));
		giveRandomTerritoriesToPlayers();
		
		for(int i = 0; i < players.size(); ++i) {
			players.get(i).addTerritoryCard(getCardById(0));
			players.get(i).addTerritoryCard(getCardById(1));
			players.get(i).addTerritoryCard(getCardById(2));
		}
		
		started = true;
				
		for (int i = 0; i < matchStartListeners.size(); ++i) {
			matchStartListeners.get(i).onMatchStart(this);
		}
	}	
	
	private static ArrayList<MatchStartListener> matchStartListeners = new ArrayList<MatchStartListener>();
	
	public static void addMatchStartListener(MatchStartListener listener) {
		matchStartListeners.add(listener);
	}
	
	public static void removeMatchStartListener(MatchStartListener listener) {
		matchStartListeners.remove(listener);
	}
}
