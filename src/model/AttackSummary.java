package model;

import java.util.Arrays;

/*
 * Holds data about an attack context and outcome.
 */
public class AttackSummary {
	
	public final int[] defenseDices;
	public final int[] attackDices;
	
	public int getAttackCount() {
		return attackDices.length;
	}
	
	public int getDefenseCount() {
		return defenseDices.length;
	}
	
	private int defenseLoss;
	/**
	 *  Returns the number of fallen defending troops.
	 */
	public int getDefenseLoss() {
		return defenseLoss;
	}
	
	private int attackLoss;
	/**
	 *  Returns the number of fallen attacking troops. 
	 */
	public int getAttackLoss() {
		return attackLoss;
	}

	/**
	 *  The attacking territory.
	 */
	public final Territory sourceTerritory;
	
	/**
	 *  The attacked territory.
	 */
	public final Territory targetTerritory;
	
	private boolean territoryTaken;
	
	/**
	 *  Was the target territory succesfully taken by the attacking troops? 
	 */
	public boolean territoryWasTaken() {
		return territoryTaken;
	}
	
	private void calculateOutcome() {
		int j = defenseDices.length - 1;
		int i = attackDices.length - 1;
		
		while (i >= 0 && j >= 0) {
			if (attackDices[i] > defenseDices[i]) {
				defenseLoss++;
			} else {
				attackLoss++;
			}
			
			j--; i--;
		}
		
		territoryTaken = defenseLoss == targetTerritory.getSoldierCount();
	}
	
	/**
	 * @param sourceTerritory The territory performing the attack. The state in which this territory should be passed as a parameter is
	 * the state it held prior to the attack.
	 * @param targetTerritory The territory being attacked. The state in which this territory should be passed as a parameter is
	 * the state it held prior to the attack.
	 * @param defenseDices The defense dices. Expected to be sorted from smallest to largest value.
	 * @param attackDices The attack dices. Expected to be sorted from smallest to largest value.
	 */
	public AttackSummary(Territory sourceTerritory, Territory targetTerritory, int[] defenseDices, int[] attackDices) {
		this.sourceTerritory = sourceTerritory;
		this.targetTerritory = targetTerritory;
		this.defenseDices = defenseDices;
		this.attackDices = attackDices;
		
		calculateOutcome();
	}

}
