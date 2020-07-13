package model;

public class SoldierCountInLocationObjective implements IObjective {

	private static final long serialVersionUID = 3360580213391709855L;
	
	private int soldierCount;
	private Territory[] territories;
	
	public SoldierCountInLocationObjective(int soldierCount, Territory[] territories) {
		this.soldierCount = soldierCount;
		this.territories = territories;
	}

	@Override
	public String getDescription() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Obter pelo menos %d tropas espalhadas pelos seguintes territ�rios: ",soldierCount));
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

	@Override
	public boolean isAchievable(Player p) {
		return true;
	}

	@Override
	public IObjective getFallbackObjective(Player p) {
		return null;
	}

}
