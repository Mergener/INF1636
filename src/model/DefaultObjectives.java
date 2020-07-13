package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefaultObjectives {

	private static Player getRandomPlayerFromMatch(Match m) {
		List<Player> players = m.getPlayers();
		
		Random random = new Random();
		
		return players.get(random.nextInt(players.size()));
	}
	
	/*
	 * Generates and returns a list of pre-defined objectives associated with a given world.
	 */
	public static List<IObjective> getAllDefaultObjectives(Match match) {
		World world = match.getWorld();
		
		ArrayList<IObjective> objectives = new ArrayList<IObjective>();
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Brasil"),
				world.findTerritory("Argentina"),
				world.findTerritory("Cazaquistao"),
				world.findTerritory("Calgary"),
				world.findTerritory("Groenlandia"),
				world.findTerritory("Alasca"),
				world.findTerritory("Vancouver"),
				world.findTerritory("Quebec"),
				world.findTerritory("Texas"),
				world.findTerritory("Nova Iorque")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Argelia"),
				world.findTerritory("Egito"),
				world.findTerritory("Nigeria"),
				world.findTerritory("Somalia"),
				world.findTerritory("Angola"),
				world.findTerritory("Franca"),
				world.findTerritory("Espanha"),
				world.findTerritory("Italia"),
				world.findTerritory("Reino Unido"),
				world.findTerritory("Romenia")
		}));		

		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Turquia"),
				world.findTerritory("Letonia"),
				world.findTerritory("Estonia"),
				world.findTerritory("Siria"),
				world.findTerritory("Iraque"),
				world.findTerritory("Arabia Saudita"),
				world.findTerritory("Paquistao"),
				world.findTerritory("Ira"),
				world.findTerritory("Suecia"),
				world.findTerritory("Polonia")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Russia"),
				world.findTerritory("Cazaquistao"),
				world.findTerritory("Mongolia"),
				world.findTerritory("Coreia do Norte"),
				world.findTerritory("Coreia do Sul"),
				world.findTerritory("Bangladesh"),
				world.findTerritory("Paquistao"),
				world.findTerritory("India"),
				world.findTerritory("Indonesia"),
				world.findTerritory("Australia")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Siberia"),
				world.findTerritory("Alasca"),
				world.findTerritory("Vancouver"),
				world.findTerritory("Groenlandia"),
				world.findTerritory("Quebec"),
				world.findTerritory("Mexico"),
				world.findTerritory("Venezuela"),
				world.findTerritory("Brasil"),
				world.findTerritory("Peru"),
				world.findTerritory("Argentina")
		}));
		
		objectives.add(new SoldierCountInLocationObjective(20, new Territory[] {
				world.findTerritory("Nova Iorque"),
				world.findTerritory("Japao")
		}));
		
		objectives.add(new SoldierCountInLocationObjective(50, new Territory[] {
				world.findTerritory("Estonia"),
				world.findTerritory("Letonia"),
				world.findTerritory("Russia"),
				world.findTerritory("Siberia"),
				world.findTerritory("Turquia"),
				world.findTerritory("Cazaquistao"),
				world.findTerritory("Mongolia"),
				world.findTerritory("Japao"),
				world.findTerritory("China"),
				world.findTerritory("Coreia do Sul"),
				world.findTerritory("Coreia do Norte"),
				world.findTerritory("Tailandia"),
				world.findTerritory("Bangladesh"),
				world.findTerritory("Paquistao"),
				world.findTerritory("India"),
				world.findTerritory("Ira"),
				world.findTerritory("Iraque"),
				world.findTerritory("Siria"),
				world.findTerritory("Jordania"),
				world.findTerritory("Arabia Saudita")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Asia"),
				world.findContinent("America do Sul")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Oceania"),
				world.findContinent("America do Norte")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Asia"),
				world.findContinent("Europa")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Oceania"),
				world.findContinent("Europa")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Oceania"),
				world.findContinent("Europa")
		}));
		
		objectives.add(new CompositeObjective(new IObjective[] {
				new ConquerEntireContinentsObjective(new Continent[] {
						world.findContinent("America do Sul"),
						world.findContinent("Europa")
				}),
				new SoldierCountInLocationObjective(20, new Territory[] {
						world.findTerritory("Franca")
				})
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("America do Norte"),
				world.findContinent("Africa")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Asia"),
				world.findContinent("Africa")
		}));	
		
		objectives.add(new DestroyOpponentObjective(getRandomPlayerFromMatch(match)));
		
		return objectives;
	}
	
	private DefaultObjectives() {
	}

}
