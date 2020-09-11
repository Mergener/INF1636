package model;

class DominateTerritoriesObjective implements IObjective {
		
	private static final long serialVersionUID = -4045729534881191040L;
	
	private Territory[] targets;
	
	@Override
	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Domine os seguintes territórios:\n");
		for (Territory t : targets) {
			sb.append("\t" + t.getName() + "\n");
		}
		
		return sb.toString();
	}

	@Override
	public boolean isComplete(Player p, World w) {
		for (Territory t : targets) {
			if (t.getOwner() != p) {
				return false;
			}
		}

		return true;
	}

	public DominateTerritoriesObjective(Territory[] targets) {
		this.targets = new Territory[targets.length];
		
		for (int i = 0; i < targets.length; ++i) {
			this.targets[i] = targets[i];
		}
	}
	
	@Override
	public boolean isAchievable(Player p) {
		return true;
	}

	@Override
	public IObjective getFallbackObjective(Player p) {
		return null;
	}
}
