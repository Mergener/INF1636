package model;

import java.awt.Color;

class World {

	private Continent[] continents;
	public Continent[] getContinents() {
		return continents;
	}
	
	private World() {
	}
	
	public static World generateDefaultWorld() {
		World world = new World();
		
		world.continents = new Continent[5];
		
		// South america:	
		Territory brazil = new Territory("Brazil", 0.28f, 0.58f);
		Territory argentina = new Territory("Argentina", 0.26f, 0.71f);
		Territory peru = new Territory("Peru", 0.23f, 0.64f);
		Territory venezuela = new Territory("Venezuela", 0.19f, 0.56f);
		
		brazil.addNeighbour(argentina);
		brazil.addNeighbour(peru);
		brazil.addNeighbour(venezuela);
		peru.addNeighbour(argentina);
		peru.addNeighbour(venezuela);
		
		world.continents[0] = new Continent("South America", new Territory[] {
				brazil, argentina,
				peru, venezuela
		}, 2, new Color(0, 104, 58));
				
		// North America:
		Territory alaska = new Territory("Alaska", 0.08f, 0.2f);
		Territory calgary = new Territory("Calgary", 0.17f, 0.18f);
		Territory greenland = new Territory("Greenland", 0.31f, 0.15f);
		Territory vancouver = new Territory("Vancouver", 0.14f, 0.15f);
		Territory california = new Territory("California", 0.13f, 0.38f);
		Territory texas = new Territory("Texas", 0.16f, 0.34f);
		Territory newYork = new Territory("New York", 0.19f, 0.35f);
		Territory mexico = new Territory("Mexico", 0.13f, 0.46f);
		Territory quebec = new Territory("Quebec", 0.26f, 0.26f);
		
		mexico.addNeighbour(venezuela);
		mexico.addNeighbour(texas);
		mexico.addNeighbour(california);
		texas.addNeighbour(california);
		texas.addNeighbour(newYork);
		texas.addNeighbour(vancouver);
		texas.addNeighbour(quebec);
		california.addNeighbour(vancouver);
		newYork.addNeighbour(quebec);
		vancouver.addNeighbour(alaska);
		vancouver.addNeighbour(quebec);
		vancouver.addNeighbour(calgary);
		calgary.addNeighbour(alaska);
		calgary.addNeighbour(greenland);
		quebec.addNeighbour(greenland);

		world.continents[1] = new Continent("North America", new Territory[] {
				alaska, calgary, greenland,
				vancouver, quebec,
				california, texas, newYork,
				mexico
		}, 5, new Color(238, 64, 54));
		
		// Europe:
		Territory uk = new Territory("United Kingdom", 0.45f, 0.23f);
		Territory france = new Territory("France", 0.48f, 0.32f);
		Territory spain = new Territory("Spain", 0.44f, 0.37f);
		Territory italy = new Territory("Italy", 0.52f, 0.31f);
		Territory romania = new Territory("Romania", 0.57f, 0.31f);
		Territory ukraine = new Territory("Ukraine", 0.59f, 0.31f);
		Territory poland = new Territory("Poland", 0.56f, 0.27f);
		Territory sweden = new Territory("Sweden", 0.53f, 0.17f);
		
		uk.addNeighbour(greenland);
		uk.addNeighbour(france);
		spain.addNeighbour(france);
		france.addNeighbour(italy);
		france.addNeighbour(sweden);
		italy.addNeighbour(romania);
		italy.addNeighbour(poland);
		italy.addNeighbour(sweden);
		poland.addNeighbour(romania);
		poland.addNeighbour(ukraine);
		romania.addNeighbour(ukraine);
		
		world.continents[2] = new Continent("Europe", new Territory[] {
				uk, sweden,
				spain, france, italy,
				poland, romania, ukraine
		}, 5, new Color(43, 56, 143));
		
		// Asia:
		Territory estonia = new Territory("Estonia", 0.66f, 0.22f);
		Territory russia = new Territory("Russia", 0.76f, 0.21f);
		Territory siberia = new Territory("Siberia", 0.87f, 0.19f);
		Territory letonia = new Territory("Letonia", 0.64f, 0.25f);
		Territory kazakhstan = new Territory("Kazakhstan", 0.81f, 0.19f);
		Territory turkey = new Territory("Turkey", 0.7f, 0.32f);
		Territory mongolia = new Territory("Mongolia", 0.84f, 0.33f);
		Territory japan = new Territory("Japan", 0.93f, 0.36f);
		Territory syria = new Territory("Syria", 0.65f, 0.38f);
		Territory pakistan = new Territory("Pakistan", 0.73f, 0.42f);
		Territory china = new Territory("China", 0.77f, 0.38f);
		Territory northKorea = new Territory("Coreia do Norte", 0.84f, 0.4f);
		Territory southKorea = new Territory("Coreia do Sul", 0.84f, 0.43f);
		Territory iraq = new Territory("Iraque", 0.66f, 0.49f);
		Territory iran = new Territory("Irã", 0.7f, 0.45f);
		Territory india = new Territory("Índia", 0.78f, 0.5f);
		Territory bangladesh = new Territory("Bangladesh", 0.82f, 0.5f);
		Territory thailand = new Territory("Tailândia", 0.87f, 0.48f);
		Territory saudiArabia = new Territory("Arábia Saudita", 0.66f, 0.52f);
		Territory jordania = new Territory("Jordânia", 0.61f, 0.45f);
				
		siberia.addNeighbour(alaska);
		estonia.addNeighbour(sweden);
		estonia.addNeighbour(letonia);
		estonia.addNeighbour(russia);
		letonia.addNeighbour(poland);
		letonia.addNeighbour(ukraine);
		letonia.addNeighbour(russia);
		letonia.addNeighbour(kazakhstan);
		letonia.addNeighbour(turkey);
		letonia.addNeighbour(sweden);
		russia.addNeighbour(kazakhstan);
		russia.addNeighbour(siberia);
		kazakhstan.addNeighbour(siberia);
		kazakhstan.addNeighbour(mongolia);
		kazakhstan.addNeighbour(china);
		kazakhstan.addNeighbour(turkey);
		kazakhstan.addNeighbour(japan);
		turkey.addNeighbour(ukraine);
		turkey.addNeighbour(china);
		turkey.addNeighbour(pakistan);
		turkey.addNeighbour(syria);
		china.addNeighbour(mongolia);
		china.addNeighbour(northKorea);
		china.addNeighbour(southKorea);
		china.addNeighbour(pakistan);
		mongolia.addNeighbour(japan);
		japan.addNeighbour(northKorea);
		northKorea.addNeighbour(southKorea);
		southKorea.addNeighbour(india);
		southKorea.addNeighbour(bangladesh);
		southKorea.addNeighbour(thailand);
		thailand.addNeighbour(bangladesh);
		bangladesh.addNeighbour(india);
		india.addNeighbour(pakistan);
		pakistan.addNeighbour(syria);
		pakistan.addNeighbour(iran);
		iran.addNeighbour(iraq);
		iran.addNeighbour(syria);
		iraq.addNeighbour(syria);
		iraq.addNeighbour(jordania);
		iraq.addNeighbour(saudiArabia);
		syria.addNeighbour(jordania);
		jordania.addNeighbour(saudiArabia);

		world.continents[3] = new Continent("Ásia", new Territory[] {
				estonia, russia, siberia,
				letonia, kazakhstan,
				turkey, mongolia, japan,
				syria, pakistan,
				china, northKorea, southKorea,
				iraq, iran, india, bangladesh,
				thailand, saudiArabia,
				jordania
		}, 7, new Color(246, 146, 30));
		
		// Africa:
		Territory algeria = new Territory("Argélia", 0.45f, 0.49f);
		Territory egypt = new Territory("Egito", 0.55f, 0.51f);
		Territory nigeria = new Territory("Nigéria", 0.49f, 0.57f);
		Territory somalia = new Territory("Somália", 0.60f, 0.58f);
		Territory angola = new Territory("Angola", 0.54f, 0.66f);
		Territory southAfrica = new Territory("África do Sul", 0.56f, 0.74f);
		
		algeria.addNeighbour(italy);
		algeria.addNeighbour(spain);
		algeria.addNeighbour(egypt);
		algeria.addNeighbour(nigeria);
		nigeria.addNeighbour(brazil);
		nigeria.addNeighbour(egypt);
		nigeria.addNeighbour(somalia);
		nigeria.addNeighbour(angola);
		egypt.addNeighbour(romania);
		egypt.addNeighbour(somalia);
		egypt.addNeighbour(jordania);
		somalia.addNeighbour(saudiArabia);
		somalia.addNeighbour(angola);
		somalia.addNeighbour(southAfrica);
		angola.addNeighbour(southAfrica);

		world.continents[4] = new Continent("África", new Territory[] {
				algeria, egypt,
				nigeria, somalia,
				angola, southAfrica
		}, 3, new Color(101, 45, 144));
		
		// Oceania:
		Territory indonesia = new Territory("Indonésia", 0.87f, 0.66f);
		Territory perth = new Territory("Perth", 0.78f, 0.77f);
		Territory australia = new Territory("Austrália", 0.85f, 0.79f);
		Territory newZealand = new Territory("Nova Zelândia", 0.9f, 0.84f);
		
		indonesia.addNeighbour(india);
		indonesia.addNeighbour(bangladesh);
		indonesia.addNeighbour(australia);
		indonesia.addNeighbour(newZealand);
		newZealand.addNeighbour(australia);
		australia.addNeighbour(perth);

		world.continents[5] = new Continent("Oceania", new Territory[] {
				indonesia, newZealand,
				australia
		}, 2, new Color(38, 169, 224));
				
		return world;
	}
	
	public int getTerritoryCount() {
		int count = 0;
		for (int i = 0; i < continents.length; ++i) {
			count += continents[i].getTerritoryCount();
		}
		return count;
	}
	
	public Territory findTerritory(String name) {
		for (Continent c : continents) {
			Territory t = c.findTerritory(name);
			if (t != null) {
				return t;
			}
		}
		return null;
	}
	
	public Continent findContinent(String name) {
		for (Continent c : continents) {
			if (c.getName() == name) {
				return c;
			}
		}
		return null;
	}
}
