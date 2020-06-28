package model;

interface IObjective {
	public String getDescription();
	public boolean isSuitableForPlayer(Player p);
	public boolean isComplete(Player p, World world);
}
