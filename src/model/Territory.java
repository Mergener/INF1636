package model;

import java.util.ArrayList;

class Territory {
	
	private String name;
	private Player owner = null;
	private Continent continent;
	
	private int soldierCount = 0;
	
	private ArrayList<Territory> neighbours = new ArrayList<Territory>();
	
	public String getName() {
		return name;
	}
	
	public Continent getContinent(){
		return continent;
	}
	
	public void setContinent(Continent c) {
		continent = c;
	}
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public int getSoldierCount() {
		return soldierCount;
	}
	
	public void addSoldiers(int count) {
		soldierCount += count;
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
