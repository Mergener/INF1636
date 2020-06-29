package model;

public class DestroyOpponentObjective implements IObjective{

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
		return opponent.getSoldierCount() <= 0;
	}
	
	@Override
	public boolean isSuitableForPlayer(Player p) {
		return p != opponent;
	}

}
