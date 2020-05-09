package model;

interface IObjective {
	public String getDescription();
	public boolean isComplete(Player p, World world);
}
