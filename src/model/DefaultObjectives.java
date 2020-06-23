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
				world.findTerritory("Colombia"),
				world.findTerritory("Borneo")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Brazil"),
				world.findTerritory("Argentina"),
				world.findTerritory("Borneo")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Argentina"),
				world.findTerritory("Colombia"),
				world.findTerritory("Borneo")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Australia"),
				world.findTerritory("Colombia"),
				world.findTerritory("Borneo")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Australia"),
				world.findTerritory("Colombia"),
				world.findTerritory("Brazil")
		}));
		
		objectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Australia"),
				world.findTerritory("Sumatra"),
				world.findTerritory("Brazil")
		}));
		
		objectives.add(new SoldierCountObjective(100));
		
		
		return objectives;
	}
	
	private DefaultObjectives() {
	}

}
