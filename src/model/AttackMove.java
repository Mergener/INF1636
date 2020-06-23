package model;

import java.util.Random;

class AttackMove extends PlayerMove {

	public final Territory source;
	public final Territory target;
	
	public AttackSummary perform(int soldierCount) throws Exception {
		if (!source.isNeighbourOf(target)) {
			throw new Exception("Player tried attacking territory from a non connected source territory!");
		}
		if (source.getOwner() != player) {
			throw new Exception("Player tried attacking territory from territory not owned by them!");
		}
		if (target.getOwner() == player) {
			throw new Exception("Player tried attacking their own territory!");
		}
		if (soldierCount >= source.getSoldierCount()) {
			throw new Exception("Player tried attacking from territory without enough troops to do so!");
		}
		if (soldierCount < 1) {
			throw new Exception("Cannot attack territory using less than one soldier!");
		}
		if (soldierCount > 3) {
			throw new Exception("Cannot attack territory using more than three soldiers!");
		}
		
		// Attack is valid, perform RNG to generate the results.
		int[] attackDices = Dice.roll(soldierCount);
		int[] defenseDices = Dice.roll(Math.min(3, target.getSoldierCount()));
		
		// Generates the attack summary object. Note that this object must be generated before any changes 
		// caused by this attack take place.
		AttackSummary summary = new AttackSummary(source, target, defenseDices, attackDices);
		
		// Since the attack summary was generated, we can apply now the changes to the territories.
		target.removeSoldiers(summary.getDefenseLoss());

		if (summary.territoryWasTaken()) {
			player.addTerritory(target);
		}
		
		return summary;
	}
	
	public AttackMove(Player p, Territory source, Territory target) {
		super(p);
		
		this.target = target;
		this.source = source;
	}

}
