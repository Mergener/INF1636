package shared;

public class AttackEventArgs {
	public final PlayerColor attackerColor;
	public final PlayerColor victimColor; 
	public final String sourceTerritoryName; 
	public final String targetTerritoryName;
	public final int[] attackDices;
	public final int[] defenseDices;
	
	public AttackEventArgs(PlayerColor attackerColor, PlayerColor victimColor, String sourceTerritoryName, String targetTerritoryName, int[] attackDices, int[] defenseDices) {
		this.attackerColor = attackerColor;
		this.victimColor = victimColor;
		this.sourceTerritoryName = sourceTerritoryName;
		this.targetTerritoryName = targetTerritoryName;
		this.attackDices = attackDices;
		this.defenseDices = defenseDices;
	}
}
