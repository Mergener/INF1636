package model;

public class CompositeObjective implements IObjective {

	private IObjective[] objectives;
	
	public CompositeObjective(IObjective[] objectives) {
		this.objectives = objectives;
	}
	
	@Override
	public String getDescription() {

		StringBuilder sb = new StringBuilder();
		
		sb.append("Complete the following objectives:\n");
		for (IObjective o : objectives) {
			sb.append(o.getDescription() + "\n");
		}
		
		return null;
	}

	@Override
	public boolean isComplete(Player p, World world) {
		for(int i = 0; i < objectives.length; ++i) {
			if(objectives[i].isComplete(p, world) != true) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean isSuitableForPlayer(Player p) {
		for(int i = 0; i < objectives.length; ++i) {
			if(objectives[i].isSuitableForPlayer(p) == false) {
				return false;
			}
		}
		return true;
	}

}
