package model;

public class DominateTerritoryCountObjective implements IObjective {
	
	private int territoryCount;

	public DominateTerritoryCountObjective(int count) {
		this.territoryCount = count;
	}

	@Override
	public String getDescription() {
		return String.format("Domine %d territórios ao total.", territoryCount);
	}

	@Override
	public boolean isSuitableForPlayer(Player p) {
		return true;
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
