package model;

public class DestroyOpponentObjective implements IObjective {

	private static final long serialVersionUID = 7890879586240812523L;
	private Player opponent;
	
	public DestroyOpponentObjective(Player opponent) {
		this.opponent = opponent;
	}
	
	@Override
	public String getDescription() {
		return String.format("Destrua completamente todas as tropas de %s. Caso você seja desta cor ou esse oponente foi destruído por outra pessoa, seu objetivo passa a ser conquistar 24 territórios.", opponent.getColor().toString());
	}

	@Override
	public boolean isComplete(Player p, World world) {
		return opponent.getTerritoryCount() <= 0 && opponent.getNemesis() == p;
	}

	@Override
	public boolean isAchievable(Player p) {
		return opponent.getTerritoryCount() > 0 || opponent.getNemesis() == p;
	}

	@Override
	public IObjective getFallbackObjective(Player p) {
		return new DominateTerritoryCountObjective(24);
	}

}
