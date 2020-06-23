package model;

public class SoldierCountObjective implements IObjective {

	private int soldierCount;
	
	public SoldierCountObjective(int soldierCount) {
		this.soldierCount = soldierCount;
	}

	@Override
	public String getDescription() {
		return String.format("Obtain at least %d soldiers.", soldierCount);
	}

	@Override
	public boolean isComplete(Player p, World world) {
		return p.getSoldierCount() >= soldierCount;
	}

}
