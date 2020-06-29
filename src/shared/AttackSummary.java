package shared;

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
	public final String sourceTerritoryName;
	
	/**
	 *  The attacked territory.
	 */
	public final String targetTerritoryName;
	
	private boolean territoryTaken;
	
	/**
	 *  Was the target territory succesfully taken by the attacking troops? 
	 */
	public boolean territoryWasTaken() {
		return territoryTaken;
	}
	
	/**
	 * @param sourceTerritory The territory performing the attack. The state in which this territory should be passed as a parameter is
	 * the state it held prior to the attack.
	 * @param targetTerritory The territory being attacked. The state in which this territory should be passed as a parameter is
	 * the state it held prior to the attack.
	 * @param defenseDices The defense dices. Expected to be sorted from smallest to largest value.
	 * @param attackDices The attack dices. Expected to be sorted from smallest to largest value.
	 */
	public AttackSummary(String sourceTerritoryName, String targetTerritoryName, int[] defenseDices, int[] attackDices, int defenseLoss, int attackLoss, boolean territoryWasTaken) {
		this.sourceTerritoryName = sourceTerritoryName;
		this.targetTerritoryName = targetTerritoryName;
		this.defenseDices = defenseDices;
		this.attackDices = attackDices;
		this.attackLoss = attackLoss;
		this.defenseLoss = defenseLoss;
		this.territoryTaken = territoryWasTaken;
	}

}
