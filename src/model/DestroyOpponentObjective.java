package model;

public class DestroyOpponentObjective implements IObjective{

	private static final long serialVersionUID = 7890879586240812523L;
	private Player opponent;
	
	public DestroyOpponentObjective(Player opponent) {
		this.opponent = opponent;
	}
	
	@Override
	public String getDescription() {
		return String.format("Destrua completamente todas as tropas de %s", opponent.getColor().toString());
	}

	@Override
	public boolean isComplete(Player p, World world) {
		assert(p != opponent):
			"DestroyOpponentObjective: Alvo não pode ser o mesmo que o dono do objetivo.";
		
		return opponent.getTerritoryCount() <= 0 && opponent.getNemesis() == p;
	}
	
	@Override
	public boolean isSuitableForPlayer(Player p) {
		return p != opponent;
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
