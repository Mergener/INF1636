package model;

import java.util.Collection;
import java.awt.Color;
import java.io.Serializable;
import java.util.Hashtable;

class Continent implements Serializable {
	
	private static final long serialVersionUID = 9154620184130697744L;
	
	private String name;
	private Color color;
	private int value;
	
	private Hashtable<String, Territory> territories = new Hashtable<String, Territory>();
		
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getTerritoryCount() {
		return territories.size();
	}
	
	public Collection<Territory> getTerritories() {
		return territories.values();
	}
	
	public Territory findTerritory(String name) {
		return territories.get(name);
	}
	
	public Continent(String name, Territory[] territories, int value, Color color) {
		this.name = name;
		this.value = value;
		this.color = color;
		
		for (Territory t : territories) {
			this.territories.put(t.getName(), t);
			t.setContinent(this);
		}
	}
}
