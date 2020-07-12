package controller;

public enum GameState {
	
	/**
	 * Armies are currently being distributed throughout each player's territories.
	 */
	ArmyDistribution,
	
	/**
	 * Players are currently deciding whether they should attack other players.
	 */
	Combat,
	
	/**
	 * Players are repositioning their troops across the map.
	 */
	ArmyMovement,
	
	Nothing
	
}