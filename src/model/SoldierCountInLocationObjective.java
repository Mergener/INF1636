package model;

public class SoldierCountInLocationObjective implements IObjective {

	private int soldierCount;
	private Territory[] territories;
	
	public SoldierCountInLocationObjective(int soldierCount, Territory[] territories) {
		this.soldierCount = soldierCount;
		this.territories = territories;
	}

	@Override
	public String getDescription() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Obtain at least %d soldiers in the following territories: ",soldierCount));
		for (Territory t : territories) {
			sb.append("\n\t" + t.getName());
		}
		
		return sb.toString();
	}

	@Override
	public boolean isComplete(Player p, World world) {
		
		int sum = 0;
		
		for(int i = 0; i < territories.length; ++i) {
			if(territories[i].getOwner() == p) {
				sum += territories[i].getSoldierCount();
			}
		}
		
		return sum >= soldierCount;
		
	}
	
	@Override
	public boolean isSuitableForPlayer(Player p) {
		return true;
	}

}
