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
		int amtOwned = 0; // Number of target territories that have already been owned.
		
		for (Continent c : w.getContinents()) {
			for (Territory t1 : c.getTerritories()) {
				for (Territory t2 : targets) {
					if (t1 == t2) {
						amtOwned++;
					}
				}
			}
		}
		
		return amtOwned == targets.length;
	}

	public DominateTerritoriesObjective(Territory[] targets) {
		this.targets = new Territory[targets.length];
		
		for (int i = 0; i < targets.length; ++i) {
			this.targets[i] = targets[i];
		}
	}
	
	@Override
	public boolean isSuitableForPlayer(Player p) {
		return true;
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
