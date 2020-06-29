package model;

import java.awt.Image;

import shared.Geometry;

class TerritoryCard {
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
	
	public TerritoryCard(String description, Geometry geometry, Territory territory) {
		this.description = description;
		this.geometry = geometry;
		this.territory = territory;
	}
}
