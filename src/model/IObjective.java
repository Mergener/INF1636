package model;

import java.io.Serializable;

interface IObjective extends Serializable {
	
	/**
	 * The objective's description.
	 */
	public String getDescription();
	
	/**
	 * Has this objective been completed for the specified player in the
	 * specified world?
	 */
	public boolean isComplete(Player p, World world);
	
	/**
	 * Can this objective still be completed by the specified player?
	 */
	public boolean isAchievable(Player p);
	
	/**
	 * In case this objective is not achievable by a player, this method should return
	 * a fallback objective that is achievable for the specified player.
	 * 
	 * Note 1: this method does not need to add the generated objective to the player's objective
	 * list.
	 * 
	 * Note 2: it is acceptable for this method to return null if the objective is always achievable.
	 */
	public IObjective getFallbackObjective(Player p);
	
}
