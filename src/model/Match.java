package model;

import exceptions.NotEnoughPlayers;
import exceptions.IllegalCardsTrade;
import exceptions.IllegalMigration;
import exceptions.InvalidAttack;
import listeners.IAttackListener;
import listeners.IVictoryListener;
import shared.AttackSummary;
import shared.Dice;
import shared.Geometry;
import shared.PlayerColor;

import java.util.Collections;
import java.util.List;
import java.util.Hashtable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

class Match implements Serializable {
	
	private static final long serialVersionUID = 6693775283474164375L;

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
	
	private void randomizeUnclaimedCardsList() {
		Random random = new Random();
		
		ArrayList<TerritoryCard> arr = new ArrayList<TerritoryCard>(allCards);
		
		while (!arr.isEmpty()) {
			int idx = random.nextInt(arr.size());
			
			unclaimedTerritoryCards.add(arr.get(idx));
			arr.remove(idx);
		}
	}
	
	private void generateTerritoryCards() {
		
		for (int i = 0; i < JOKER_CARDS_COUNT; ++i) {
			TerritoryCard card = new TerritoryCard(allCards.size(), "Carta coringa.", Geometry.Joker, null);
			unclaimedTerritoryCards.addLast(card);
			allCards.add(card);
		}
		
		TerritoryCard southAfricaCard = new TerritoryCard(allCards.size(), "Africa do Sul", Geometry.Triangle,world.findTerritory("Africa do Sul"));
		allCards.add(southAfricaCard);
		
		TerritoryCard angolaCard = new TerritoryCard(allCards.size(), "Angola", Geometry.Square, world.findTerritory("Angola"));
		allCards.add(angolaCard);
		
		TerritoryCard argeliaCard = new TerritoryCard(allCards.size(), "Argelia", Geometry.Circle, world.findTerritory("Argelia"));
		allCards.add(argeliaCard);
		
		TerritoryCard egyptCard = new TerritoryCard(allCards.size(), "Egito", Geometry.Triangle, world.findTerritory("Egito"));
		allCards.add(egyptCard);
		
		TerritoryCard nigeriaCard = new TerritoryCard(allCards.size(), "Nigeria", Geometry.Circle, world.findTerritory("Nigeria"));
		allCards.add(nigeriaCard);
		
		TerritoryCard somaliaCard = new TerritoryCard(allCards.size(), "Somalia", Geometry.Square, world.findTerritory("Somalia"));
		allCards.add(somaliaCard);
		
		TerritoryCard alascaCard = new TerritoryCard(allCards.size(), "Alasca", Geometry.Triangle, world.findTerritory("Alasca"));
		allCards.add(alascaCard);
		
		TerritoryCard calgaryCard = new TerritoryCard(allCards.size(), "Calgary", Geometry.Circle, world.findTerritory("Calgary"));
		allCards.add(calgaryCard);
		
		TerritoryCard californiaCard = new TerritoryCard(allCards.size(), "California", Geometry.Square, world.findTerritory("California"));
		allCards.add(californiaCard);
		
		TerritoryCard greenlandcard = new TerritoryCard(allCards.size(), "Groenlandia", Geometry.Circle, world.findTerritory("Groenlandia"));
		allCards.add(greenlandcard);
		
		TerritoryCard mexicoCard = new TerritoryCard(allCards.size(), "Mexico", Geometry.Square, world.findTerritory("Mexico"));
		allCards.add(mexicoCard);
		
		TerritoryCard newYorkCard = new TerritoryCard(allCards.size(), "Nova Iorque", Geometry.Square, world.findTerritory("Nova Iorque"));
		allCards.add(newYorkCard);
		
		TerritoryCard quebecCard = new TerritoryCard(allCards.size(), "Quebec", Geometry.Circle, world.findTerritory("Quebec"));
		allCards.add(quebecCard);
		
		TerritoryCard texasCard = new TerritoryCard(allCards.size(), "Texas", Geometry.Triangle, world.findTerritory("Texas"));
		allCards.add(texasCard);
		
		TerritoryCard vancouverCard = new TerritoryCard(allCards.size(), "Vancouver", Geometry.Triangle, world.findTerritory("Vancouver"));
		allCards.add(vancouverCard);
		
		TerritoryCard saudiArabiaCard = new TerritoryCard(allCards.size(), "Arabia Saudita", Geometry.Circle, world.findTerritory("Arabia Saudita"));
		allCards.add(saudiArabiaCard);
		
		TerritoryCard bangladeshCard = new TerritoryCard(allCards.size(), "Bangladesh", Geometry.Circle, world.findTerritory("Bangladesh"));
		allCards.add(bangladeshCard);
		
		TerritoryCard khazakstanCard = new TerritoryCard(allCards.size(), "Cazaquistao", Geometry.Circle, world.findTerritory("Cazaquistao"));
		allCards.add(khazakstanCard);
		
		TerritoryCard chinaCard = new TerritoryCard(allCards.size(), "China", Geometry.Square, world.findTerritory("China"));
		allCards.add(chinaCard);
		
		TerritoryCard northKoreaCard = new TerritoryCard(allCards.size(), "Coreia do Norte", Geometry.Square, world.findTerritory("Coreia do Norte"));
		allCards.add(northKoreaCard);
		
		TerritoryCard southKoreacard = new TerritoryCard(allCards.size(), "Coreia do Sul", Geometry.Triangle, world.findTerritory("Coreia do Sul"));
		allCards.add(southKoreacard);
		
		TerritoryCard estoniaCard = new TerritoryCard(allCards.size(), "Estonia", Geometry.Circle, world.findTerritory("Estonia"));
		allCards.add(estoniaCard);
		
		TerritoryCard indiaCard = new TerritoryCard(allCards.size(), "India", Geometry.Triangle, world.findTerritory("India"));
		allCards.add(indiaCard);
		
		TerritoryCard iranCard = new TerritoryCard(allCards.size(), "Ira", Geometry.Square, world.findTerritory("Ira"));
		allCards.add(iranCard);
		
		TerritoryCard iraqCard = new TerritoryCard(allCards.size(), "Iraque", Geometry.Triangle, world.findTerritory("Iraque"));
		allCards.add(iraqCard);
		
		TerritoryCard japanCard = new TerritoryCard(allCards.size(), "Japao", Geometry.Circle, world.findTerritory("Japao"));
		allCards.add(japanCard);
		
		TerritoryCard jordaniaCard = new TerritoryCard(allCards.size(), "Jordania", Geometry.Square, world.findTerritory("Jordania"));
		allCards.add(jordaniaCard);
		
		TerritoryCard latviaCard = new TerritoryCard(allCards.size(), "Letonia", Geometry.Square, world.findTerritory("Letonia"));
		allCards.add(latviaCard);
		
		TerritoryCard mongoliaCard = new TerritoryCard(allCards.size(), "Mongolia", Geometry.Triangle, world.findTerritory("Mongolia"));
		allCards.add(mongoliaCard);
		
		TerritoryCard pakistanCard = new TerritoryCard(allCards.size(), "Paquistao", Geometry.Circle, world.findTerritory("Paquistao"));
		allCards.add(pakistanCard);
		
		TerritoryCard russiaCard = new TerritoryCard(allCards.size(), "Russia", Geometry.Triangle, world.findTerritory("Russia"));
		allCards.add(russiaCard);
		
		TerritoryCard siberiaCard = new TerritoryCard(allCards.size(), "Siberia", Geometry.Square, world.findTerritory("Siberia"));
		allCards.add(siberiaCard);
		
		TerritoryCard siriaCard = new TerritoryCard(allCards.size(), "Siria", Geometry.Square, world.findTerritory("Siria"));
		allCards.add(siriaCard);
		
		TerritoryCard thailandCard = new TerritoryCard(allCards.size(), "Tailandia", Geometry.Triangle, world.findTerritory("Tailandia"));
		allCards.add(thailandCard);
		
		TerritoryCard turkeyCard = new TerritoryCard(allCards.size(), "Turquia", Geometry.Triangle, world.findTerritory("Turquia"));
		allCards.add(turkeyCard);
		
		TerritoryCard argentinaCard = new TerritoryCard(allCards.size(), "Argentina", Geometry.Square, world.findTerritory("Argentina"));
		allCards.add(argentinaCard);
		
		TerritoryCard brasilCard = new TerritoryCard(allCards.size(), "Brasil", Geometry.Circle, world.findTerritory("Brasil"));
		allCards.add(brasilCard);
		
		TerritoryCard peruCard = new TerritoryCard(allCards.size(), "Peru", Geometry.Triangle, world.findTerritory("Peru"));
		allCards.add(peruCard);
		
		TerritoryCard venezuelaCard = new TerritoryCard(allCards.size(), "Venezuela", Geometry.Triangle, world.findTerritory("Venezuela"));
		allCards.add(venezuelaCard);
		
		TerritoryCard spainCard = new TerritoryCard(allCards.size(), "Espanha", Geometry.Circle, world.findTerritory("Espanha"));
		allCards.add(spainCard);
		
		TerritoryCard franceCard = new TerritoryCard(allCards.size(), "Franca", Geometry.Triangle, world.findTerritory("Franca"));
		allCards.add(franceCard);
		
		TerritoryCard italyCard = new TerritoryCard(allCards.size(), "Italia", Geometry.Square, world.findTerritory("Italia"));
		allCards.add(italyCard);
		
		TerritoryCard polandCard = new TerritoryCard(allCards.size(), "Polonia", Geometry.Triangle, world.findTerritory("Polonia"));
		allCards.add(polandCard);
		
		TerritoryCard unitedKingdomCard = new TerritoryCard(allCards.size(), "Reino Unido", Geometry.Circle, world.findTerritory("Reino Unido"));
		allCards.add(unitedKingdomCard);
		
		TerritoryCard romaniacard = new TerritoryCard(allCards.size(), "Romenia", Geometry.Triangle, world.findTerritory("Romenia"));
		allCards.add(romaniacard);
		
		TerritoryCard swedenCard = new TerritoryCard(allCards.size(), "Suecia", Geometry.Square, world.findTerritory("Suecia"));
		allCards.add(swedenCard);
		
		TerritoryCard ukraineCard = new TerritoryCard(allCards.size(), "Ucrania", Geometry.Circle, world.findTerritory("Ucrania"));
		allCards.add(ukraineCard);
		
		TerritoryCard australiaCard = new TerritoryCard(allCards.size(), "Australia", Geometry.Triangle, world.findTerritory("Australia"));
		allCards.add(australiaCard);
		
		TerritoryCard indonesiaCard = new TerritoryCard(allCards.size(), "Indonesia", Geometry.Triangle, world.findTerritory("Indonesia"));
		allCards.add(indonesiaCard);
		
		TerritoryCard newZelandCard = new TerritoryCard(allCards.size(), "Nova Zelandia", Geometry.Square, world.findTerritory("Nova Zelandia"));
		allCards.add(newZelandCard);
		
		TerritoryCard perthCard = new TerritoryCard(allCards.size(), "Perth", Geometry.Circle, world.findTerritory("Perth"));				
		allCards.add(perthCard);
		
		randomizeUnclaimedCardsList();
		
	}
	
	private void distributeGlobalSoldiersToPlayers() {
		for (int i = 0; i < players.size(); ++i) {
			Player p = players.get(i);
			int amt = p.getTerritoryCount() / 2;
			
			p.addGlobalSoldiers(amt);
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
	
	private transient ArrayList<IAttackListener> iAttackListeners = new ArrayList<IAttackListener>();
	
	public void addAttackListener(IAttackListener l) {
		if (iAttackListeners == null) {
			iAttackListeners = new ArrayList<IAttackListener>();
		}
		
		iAttackListeners.add(l);
	}
	
	private transient ArrayList<IVictoryListener> victoryListeners = new ArrayList<IVictoryListener>();
	
	public void addVictoryListener(IVictoryListener l) {
		if (victoryListeners == null) {
			victoryListeners = new ArrayList<IVictoryListener>();
		}
		
		victoryListeners.add(l);
	}
	
	/**
	 * Players can only be awarded with new territory cards at their first territory conquest in every round.
	 * This list keeps track of the players that have already gained territory cards in the current round.
	 * It must be cleared every new round.
	 */
	private ArrayList<Player> playersThatClaimedTerritories = new ArrayList<Player>();
	
	public void checkForVictorsAndInachievableObjectives() {
		for (Player p : players) {
			IObjective o = p.getObjective();
			
			if (!o.isAchievable(p)) {
				p.setObjective(o.getFallbackObjective(p));
			}
			
			if (o.isComplete(p, getWorld())) {
				if (victoryListeners != null) {					
					for (int i = 0; i < victoryListeners.size(); ++i) {
						victoryListeners.get(i).onVictory(p.getColor());
					}
				}
				return;
			}
		}
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
		int soldierCount = Math.min(source.getSoldierCount() - 1, 3);
		if (soldierCount < 1) {
			throw new InvalidAttack("N�o � poss�vel realizar ataque vindo de territ�rio com apenas uma tropa.");
		}
		
		// Attack is valid, perform RNG to generate the results.
		int[] attackDices = Dice.roll(soldierCount);
		int[] defenseDices = Dice.roll(Math.min(3, target.getSoldierCount()));
		
		int i = attackDices.length - 1;
		int j = defenseDices.length - 1;
		
		int attackLoss = 0;
		int defenseLoss = 0;
		
		// Calculates the attack outcome.
		while (i >= 0 && j >= 0) {
			if (attackDices[i] > defenseDices[j]) {
				defenseLoss++;
			} else {
				attackLoss++;
			}
			
			j--; i--;
		}
		
		boolean territoryTaken = defenseLoss == target.getSoldierCount();

		// Since the attack summary was generated, we can apply now the changes to the territories.
		target.removeSoldiers(defenseLoss);
		source.removeSoldiers(attackLoss);
		
		Player opponent = target.getOwner();
		boolean isTargetEliminated = false;
		PlayerColor opponentColor = null;
		
		if (territoryTaken) {			
			int transferSoldierCount = source.getSoldierCount() - 1;
			source.removeSoldiers(transferSoldierCount);
			target.addSoldiers(transferSoldierCount);
			
			opponent.removeTerritory(target);
			player.addTerritory(target);
			
			isTargetEliminated = (opponent.getTerritoryCount() == 0);
			if (isTargetEliminated) {
				// Transfer cards to eliminator
				List<TerritoryCard> opponentCards = opponent.getTerritoryCardList();
				
				for (TerritoryCard c : opponentCards) {
					player.addTerritoryCard(c);
					opponent.removeTerritoryCard(c);
				}
				
				opponent.setNemesis(player);
			}
			
			opponentColor = opponent.getColor();
			
			// Player has taken the territory, grant them a new territory card if they haven't claimed one in the
			// current round.	
			if (!playersThatClaimedTerritories.contains(player)) {
				playersThatClaimedTerritories.add(player);
				player.addTerritoryCard(unclaimedTerritoryCards.pollFirst());
			}
		}	
		
		// Generates the attack summary object.
		AttackSummary summary = new AttackSummary(source.getName(), target.getName(), defenseDices, attackDices, defenseLoss, attackLoss, territoryTaken,isTargetEliminated,opponentColor);
		
		// Notifies all attack listeners.
		if (iAttackListeners != null) {
			for (i = 0; i < iAttackListeners.size(); ++i) {
				iAttackListeners.get(i).onAttackPerformed(summary);
			}
		}
				
		checkForVictorsAndInachievableObjectives();
		
		return summary;
	}
	
	private int tradeCounter = 0;
	
	private static int[] tradeBonusAmount = new int[] {
			4, 6, 8, 10, 12, 15
	};
	
	/**
	 * Returns true if the specified trio of cards is either made of three distinct cards or three
	 * equal card geometries. Takes in consideration the special case of joker geometries. 
	 */
	private static boolean evaluateCardTrioBonus(TerritoryCard c1, TerritoryCard c2, TerritoryCard c3) {
        if (c1.getGeometry().isNotSame(c2.getGeometry()) && c1.getGeometry().isNotSame(c3.getGeometry())) {
            return true;
        }
        if (c1.getGeometry().isSame(c2.getGeometry()) && c1.getGeometry().isSame(c3.getGeometry())) {
            return true;
        }
        return false;
    }
	
	public void performCardsTrade(Player player, TerritoryCard card1, TerritoryCard card2, TerritoryCard card3) throws IllegalCardsTrade {
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
			
			player.addGlobalSoldiers(bonus);
			
			player.removeTerritoryCard(card1);
			player.removeTerritoryCard(card2);
			player.removeTerritoryCard(card3);
			
			unclaimedTerritoryCards.addLast(card1);
			unclaimedTerritoryCards.addLast(card2);
			unclaimedTerritoryCards.addLast(card3);

			tradeCounter++;
		} else {
			throw new IllegalCardsTrade("N�o � poss�vel realizar a troca de cartas - a geometria das cartas n�o � compat�vel para a troca. Para que seja, � necess�rio " +
					"que todas as cartas possuam diferentes geometrias ou todas possuam a mesma geometria. Cartas coringas s�o compat�veis com qualquer geometria.");
		}
	}
	
	public int getMaximumEligibleMigrationTroopsCount(Territory territory) {
		if (migratedCounts == null) {
			// Generate hashtable if absent
			migratedCounts = new Hashtable<Territory, Integer>();
			
			Continent[] continents = world.getContinents();
			for (Continent c : continents) {
				Collection<Territory> territories = c.getTerritories();
				for (Territory t : territories) {
					migratedCounts.put(t, 0);
				}
			}
		}
		return Math.max(territory.getSoldierCount() - migratedCounts.get(territory) - 1, 0);
	}

	private Hashtable<Territory, Integer> migratedCounts = null;
	public void performMigration(Territory source, Territory target, int amount) throws IllegalMigration {
		int maxMigrationCount = getMaximumEligibleMigrationTroopsCount(source);
		
		if (source.getOwner() != target.getOwner()) {
			throw new IllegalMigration("S� � poss�vel fazer migra��o entre dois territ�rios possu�dos por voc�.");
		} else if (amount <= 0) {
			return;
		} else if (amount > maxMigrationCount) {
			throw new IllegalMigration(String.format("S� � poss�vel migrar at� %d ex�rcitos vindos de %s. (voc� tentou %d)", maxMigrationCount, source.getName(), amount));
		} else if (!source.isNeighbourOf(target)) {
			throw new IllegalMigration(String.format("%s e %s n�o s�o vizinhos - migra��o � imposs�vel!", source.getName(), target.getName()));
		} else {
			Integer migratedCount = migratedCounts.get(target);
			migratedCounts.put(target, migratedCount + amount);
			target.addSoldiers(amount);
			source.removeSoldiers(amount);
			checkForVictorsAndInachievableObjectives();
		}
	}
	
	private void distributeContinentalSoldiersToPlayers() {
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
	
	public void terminateRound() {
		distributeContinentalSoldiersToPlayers();		
		distributeGlobalSoldiersToPlayers();
		
		playersThatClaimedTerritories.clear();
		
		if (migratedCounts != null) {		
			for (Territory t : migratedCounts.keySet()) {
				migratedCounts.put(t, 0);
			}
		}
	}
	
	public void start() {		
		giveRandomObjectivesToPlayers(DefaultObjectives.getAllDefaultObjectives(this));
		giveRandomTerritoriesToPlayers();
		distributeGlobalSoldiersToPlayers();
		distributeContinentalSoldiersToPlayers();
		checkForVictorsAndInachievableObjectives();
		
		started = true;
	}	
}
