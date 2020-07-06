package model;

import shared.Geometry;

class TerritoryCard {
	private int id;
	private String description;
	private Geometry geometry;
	private Territory territory;
	
	public String getDescription() {
		return description;
	}
	
	public Geometry getGeometry() {
		return geometry;
	}
	
	public Territory getTerritory() {
		return territory;
	}
	
	public int getId() {
		return id;
	}
	
	public TerritoryCard(int id, String description, Geometry geometry, Territory territory) {
		this.id = id;
		this.description = description;
		this.geometry = geometry;
		this.territory = territory;
	}
}
