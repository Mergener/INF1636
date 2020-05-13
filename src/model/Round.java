package model;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class Round {
	private Queue<Player> playerQueue = new LinkedList<Player>();
	private Hashtable<Continent, Integer> bonusContinentSoldiers = new Hashtable<Continent, Integer>();
	
	
	public Player getCurrentPlayer() {
		return playerQueue.peek();
	}
	
	// The following function distributes soldiers to players according to
	// half of the territories they possess. It also considers bonus soldiers
	// if the player possesses an entire continent.
	public void distributeSoldiers(World world) {
		Continent temp = null;
		Player p = getCurrentPlayer();
		boolean b = false;

		p.addSoldiers((int) p.getTerritoryCount()/2); 
		
		for (Continent c : world.getContinents()) {
			b = p.hasContinent(c);
			temp = c;
		}
		if(b == true) {
			bonusContinentSoldiers.replace(temp,temp.getValue());
		}
	}
	// The following function distributes soldiers through card trades.
	private void tradeCards() {
		Player p = getCurrentPlayer();
		ArrayList<TerritoryCard> cards = p.getCardList();
		Geometry g = cards.get(0).getGeometry();
		boolean b = true;
		for(int i = 1; i < cards.size(); ++i) {
			if(cards.get(i).getGeometry() != g) {
				
			}
		}
		
	}
	
	public void positionSoldiersInTerritory(Territory t,int n) throws Exception {
		if(t.getOwner() != getCurrentPlayer()) {
			throw new Exception("Invalid Territory. You must be the owner to perform this action.");
		}
		
		t.addSoldiers(n);
	}
	
	public void positionBonusSoldiersInTerritory(Territory t,int n) throws Exception {
		int lim = bonusContinentSoldiers.get(t.getContinent());
		if(n > lim) {
			throw new Exception("There are not enough soldiers to perform this action.");
		}
		bonusContinentSoldiers.replace(t.getContinent(),lim -n);
		t.addSoldiers(n);
	}
	
	public void addContinentSoldiers(int n,Continent c) {
		int temp = bonusContinentSoldiers.get(c);
		bonusContinentSoldiers.replace(c, temp + n);
	}
	
}
