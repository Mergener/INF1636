package model;

public class ConquerEntireContinentsObjective implements IObjective {

	private static final long serialVersionUID = -1712075768539090545L;
	
	private Continent[] continents;
	
	public ConquerEntireContinentsObjective(Continent[] continents) {
		this.continents = continents;
	}
	
	@Override
	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Conquiste os seguintes continentes:");
		for (Continent c : continents) {
			sb.append("\n\t" + c.getName());
		}
		
		return sb.toString();
	}

	@Override
	public boolean isComplete(Player p, World world) {
		for(Continent c : continents) {
			if (!(p.hasEntireContinent(c))){
				return false;
			}
		}
		return true;
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
