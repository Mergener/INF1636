package model;

public class DominateTerritoryCountObjective implements IObjective {
	
	private static final long serialVersionUID = -5141126519137604464L;
	private int territoryCount;

	public DominateTerritoryCountObjective(int count) {
		this.territoryCount = count;
	}

	@Override
	public String getDescription() {
		return String.format("Domine %d territórios ao total.", territoryCount);
	}

	@Override
	public boolean isComplete(Player p, World world) {
		return p.getTerritoryCount() == territoryCount;
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
