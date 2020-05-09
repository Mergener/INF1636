package model;

import java.awt.Color;

class World {

	private Continent[] continents;
	public Continent[] getContinents() {
		return continents;
	}
	
	private int territoryCount;
	
	private World() {
		continents = new Continent[2];
		
		// South america:	
		Territory brazil = new Territory("Brazil");
		Territory argentina = new Territory("Argentina");
		Territory colombia = new Territory("Colombia");
		Territory chile = new Territory("Chile");
		
		brazil.addNeighbour(argentina);
		brazil.addNeighbour(chile);
		brazil.addNeighbour(colombia);
		argentina.addNeighbour(chile);
		chile.addNeighbour(colombia);
		
		continents[0] = new Continent("South America", new Territory[] { brazil, argentina, colombia, chile }, 4, Color.GREEN);
	
		// Oceania:
		Territory australia = new Territory("Australia");
		Territory newGuinea = new Territory("New Guinea");
		Territory borneo = new Territory("Borneo");
		Territory sumatra = new Territory("Sumatra");
		
		australia.addNeighbour(borneo);
		australia.addNeighbour(sumatra);
		australia.addNeighbour(newGuinea);
		borneo.addNeighbour(newGuinea);
		
		continents[1] = new Continent("Oceania", new Territory[] { australia, newGuinea, borneo, sumatra }, 2, Color.RED);
		
		territoryCount = 8;
	}
	
	public int getTerritoryCount() {
		return territoryCount;
	}

	private static World instance = new World();
	public static World getInstance() {
		return instance;
	}
	
	public static void restartWorld() {
		instance = new World();
	}
}
