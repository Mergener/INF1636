package model;

import java.io.Serializable;

interface IObjective extends Serializable {
	public String getDescription();
	public boolean isSuitableForPlayer(Player p);
	public boolean isComplete(Player p, World world);
}
