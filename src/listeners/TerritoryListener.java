package listeners;

import shared.PlayerColor;

public interface TerritoryListener {
	public void onTerritoryOwnershipChange(String territoryName, PlayerColor newOwnerColor);
	public void onTerritorySoldierCountChange(String territoryName, int newTerritoryCount);
}
