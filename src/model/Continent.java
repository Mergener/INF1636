package model;

import java.awt.Color;

class Continent {
	
	private String name;
	private Territory[] territories;
	private Color color;
	private int value;
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getValue() {
		return value;
	}
	
	public Territory[] getTerritories() {
		return territories;
	}
	
	public Continent(String name, Territory[] territories, int value, Color color) {
		this.name = name;
		this.territories = territories;
		this.value = value;
		this.color = color;
	}
}
