package model;

public class CompositeObjective implements IObjective {

	private static final long serialVersionUID = -7365761880160300661L;
	private IObjective[] objectives;
	
	public CompositeObjective(IObjective[] objectives) {
		this.objectives = objectives;
	}
	
	@Override
	public String getDescription() {

		StringBuilder sb = new StringBuilder();
		
		sb.append("Complete os seguintes objetivos:\n");
		for (IObjective o : objectives) {
			sb.append(o.getDescription() + "\n");
		}
		
		return sb.toString();
	}

	@Override
	public boolean isComplete(Player p, World world) {
		for(int i = 0; i < objectives.length; ++i) {
			if (objectives[i].isComplete(p, world) != true) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean isSuitableForPlayer(Player p) {
		for(int i = 0; i < objectives.length; ++i) {
			if (objectives[i].isSuitableForPlayer(p) == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isAchievable(Player p) {
		for(int i = 0; i < objectives.length; ++i) {
			if (objectives[i].isAchievable(p) == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public IObjective getFallbackObjective(Player p) {
		for (int i = 0; i < objectives.length; ++i) {
			if (objectives[i].isAchievable(p) == false) {
				return objectives[i].getFallbackObjective(p);
			}
		}
		return null;
	}

}
