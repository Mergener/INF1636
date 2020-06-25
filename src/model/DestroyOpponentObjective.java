package model;

public class DestroyOpponentObjective implements IObjective{

	private Player opponent;
	
	public DestroyOpponentObjective(Player opponent) {
		this.opponent = opponent;
	}
	
	@Override
	public String getDescription() {
		return String.format("Completely destroy all %s player's soldiers.", opponent.getColor().toString());
	}

	@Override
	public boolean isComplete(Player p, World world) {
		assert(p != opponent):
			"DestroyOpponentObjective: Target cannot be the same as the objective owner.";
		return opponent.getSoldierCount() <= 0;
	}
	
	@Override
	public boolean isSuitableForPlayer(Player p) {
		return p != opponent;
	}

}
