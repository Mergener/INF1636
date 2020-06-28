package model;

public class ConquerEntireContinentsObjective implements IObjective {

	private Continent[] continents;
	
	public ConquerEntireContinentsObjective(Continent[] continents) {
		this.continents = continents;
	}
	
	@Override
	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Conquer all following continents:");
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

}
