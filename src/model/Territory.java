package model;

import java.io.Serializable;
import java.util.ArrayList;

import listeners.ITerritoryListener;
import shared.Point;

class Territory implements Serializable {
	
	private static final long serialVersionUID = -4924926236018475090L;
	
	private String name;
	private Player owner = null;
	private Continent continent;
	
	private int soldierCount = 0;
	
	private ArrayList<Territory> neighbours = new ArrayList<Territory>();
	
	private transient ArrayList<ITerritoryListener> listeners;
	public void addListener(ITerritoryListener l) {
		if (listeners == null) {
			listeners = new ArrayList<ITerritoryListener>();
		}
		
		listeners.add(l);
	}
	public void removeTerritoryListener(ITerritoryListener l) {
		if (listeners == null) {
			listeners = new ArrayList<ITerritoryListener>();
		}
		
		listeners.remove(l);
	}
	
	private Point center;
	public Point getCenter() {
		return center;
	}
	
	private Point[] vertices;
	public Point[] getVertices() {
		return vertices;
	}
	
	public String getName() {
		return name;
	}
	
	public Continent getContinent() {
		return continent;
	}
	
	public void setContinent(Continent c) {
		continent = c;
	}
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player newOwner) {
		if (newOwner != null && newOwner != owner) {
			owner = newOwner;
			
			if (owner != null) {
				owner.transferTerritory(this, newOwner);
			} else {
				newOwner.addTerritory(this);
			}			
			
			if (listeners != null) {
				for (int i = 0; i < listeners.size(); ++i) {
					listeners.get(i).onTerritoryOwnershipChange(name, newOwner.getColor());
				}
			}
		}
	}
	
	public int getSoldierCount() {
		return soldierCount;
	}
	
	private void setSoldierCount(int count) {
		soldierCount = Math.max(0, count);
		
		if (listeners != null) {
			for (int i = 0; i < listeners.size(); ++i) {
				listeners.get(i).onTerritorySoldierCountChange(getName(), soldierCount);
			}
		}
	}
	
	public void addSoldiers(int count) {
		setSoldierCount(getSoldierCount() + count);
	}
	
	public void removeSoldiers(int count) {
		setSoldierCount(getSoldierCount() - count);
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
	
	public Territory(String name, Point center, Point[] vertices) {		
		this.name = name;
		this.vertices = vertices;
		this.center = center;
	}
}
