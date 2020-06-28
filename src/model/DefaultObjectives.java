package model;

import java.util.ArrayList;
import java.util.List;

public class DefaultObjectives {

	/*
	 * Generates and returns a list of pre-defined objectives associated with a given world.
	 */
	public static List<IObjective> getAllDefaultObjectives(World world) {
		ArrayList<IObjective> objectives = new ArrayList<IObjective>();
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Brazil"),
				world.findTerritory("Argentina"),
				world.findTerritory("Kazakhstan"),
				world.findTerritory("Calgary"),
				world.findTerritory("Greenland"),
				world.findTerritory("Alaska"),
				world.findTerritory("Vancouver"),
				world.findTerritory("Quebec"),
				world.findTerritory("Texas"),
				world.findTerritory("New York")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Algeria"),
				world.findTerritory("Egypt"),
				world.findTerritory("Nigeria"),
				world.findTerritory("Somalia"),
				world.findTerritory("Angola"),
				world.findTerritory("France"),
				world.findTerritory("Spain"),
				world.findTerritory("Italy"),
				world.findTerritory("United Kingdom"),
				world.findTerritory("Romania")
		}));		

		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Turkey"),
				world.findTerritory("Latvia"),
				world.findTerritory("Estonia"),
				world.findTerritory("Syria"),
				world.findTerritory("Iraq"),
				world.findTerritory("Saudi Arabia"),
				world.findTerritory("Pakistan"),
				world.findTerritory("Iran"),
				world.findTerritory("Sweden"),
				world.findTerritory("Poland")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Russia"),
				world.findTerritory("Kazakhstan"),
				world.findTerritory("Mongolia"),
				world.findTerritory("North Korea"),
				world.findTerritory("South Korea"),
				world.findTerritory("Bangladesh"),
				world.findTerritory("Pakistan"),
				world.findTerritory("India"),
				world.findTerritory("Indonesia"),
				world.findTerritory("Australia")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Siberia"),
				world.findTerritory("Alaska"),
				world.findTerritory("Vancouver"),
				world.findTerritory("Greenland"),
				world.findTerritory("Quebec"),
				world.findTerritory("Mexico"),
				world.findTerritory("Venezuela"),
				world.findTerritory("Brazil"),
				world.findTerritory("Peru"),
				world.findTerritory("Argentina")
		}));
		
		objectives.add(new SoldierCountInLocationObjective(20, new Territory[] {
				world.findTerritory("New York"),
				world.findTerritory("Japan")
		}));
		
		objectives.add(new SoldierCountInLocationObjective(50, new Territory[] {
				world.findTerritory("Estonia"),
				world.findTerritory("Latvia"),
				world.findTerritory("Russia"),
				world.findTerritory("Siberia"),
				world.findTerritory("Turkey"),
				world.findTerritory("Kazakhstan"),
				world.findTerritory("Mongolia"),
				world.findTerritory("Japan"),
				world.findTerritory("China"),
				world.findTerritory("South Korea"),
				world.findTerritory("North Korea"),
				world.findTerritory("Thailand"),
				world.findTerritory("Bangladesh"),
				world.findTerritory("Pakistan"),
				world.findTerritory("India"),
				world.findTerritory("Iran"),
				world.findTerritory("Iraq"),
				world.findTerritory("Syria"),
				world.findTerritory("Jordania"),
				world.findTerritory("Saudi Arabia")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Asia"),
				world.findContinent("South America")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Oceania"),
				world.findContinent("North America")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Asia"),
				world.findContinent("Europe")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Oceania"),
				world.findContinent("Europe")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Oceania"),
				world.findContinent("Europe")
		}));
		
		objectives.add(new CompositeObjective(new IObjective[] {
				new ConquerEntireContinentsObjective(new Continent[] {
						world.findContinent("South America"),
						world.findContinent("Europe")
				}),
				new SoldierCountInLocationObjective(20, new Territory[] {
						world.findTerritory("France")
				})
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("North America"),
				world.findContinent("Africa")
		}));
		
		objectives.add(new ConquerEntireContinentsObjective(new Continent[] {
				world.findContinent("Asia"),
				world.findContinent("Africa")
		}));	
		
		return objectives;
	}
	
	private DefaultObjectives() {
	}

}
