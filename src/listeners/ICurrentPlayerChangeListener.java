package listeners;

import shared.PlayerColor;

public interface ICurrentPlayerChangeListener {
	public void onCurrentPlayerChanged(PlayerColor newPlayerColor);
}
