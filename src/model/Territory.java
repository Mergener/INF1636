package model;

import java.util.ArrayList;

class Territory {
	
	private String name;
	private Player owner = null;
	
	private int smallSoldierCount = 0;
	private int largeSoldierCount = 0;
	
	private ArrayList<Territory> neighbours = new ArrayList<Territory>();
	
	public String getName() {
		return name;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public int getSoldierCount() {
		return SoldierType.LARGE.getValue() * largeSoldierCount + 
			   SoldierType.SMALL.getValue() * smallSoldierCount;
	}
	
	public void addSoldiers(SoldierType s, int count) {
		if (s == SoldierType.LARGE) {
			largeSoldierCount += count;
		} else {
			smallSoldierCount += count;
		}
	}
	
	void addNeighbour(Territory t) {
		if (neighbours.contains(t)) {
			return;
		}
		
		this.neighbours.add(t);
		t.neighbours.add(this);
	}
	
	public boolean isNeighbourOf(Territory t) {
		for (int i = 0; i < neighbours.size(); ++i) {
			if (neighbours.get(i) == t) {
				return true;
			}
		}
		return false;
	}
	
	public Territory(String name) {
		this.name = name;
	}
}
