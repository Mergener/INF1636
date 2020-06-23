package model;

import java.awt.Image;

class TerritoryCard implements ICard {
	private String description;
	private Geometry geometry;
	private Image image;
	private Territory territory;
	
	public String getDescription() {
		return description;
	}
	
	public Geometry getGeometry() {
		return geometry;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Territory getTerritory() {
		return territory;
	}
}
