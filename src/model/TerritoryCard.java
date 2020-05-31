package model;

import java.awt.Image;

public class TerritoryCard implements ICard {
	private String description;
	private Geometry geometry;
	private Image image;
	
	public String getDescription() {
		return description;
	}
	
	public Geometry getGeometry() {
		return geometry;
	}
	
	public Image getImage() {
		return image;
	}
}
