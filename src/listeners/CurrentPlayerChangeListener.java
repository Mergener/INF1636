package listeners;

import shared.PlayerColor;

public interface CurrentPlayerChangeListener {
	public void onCurrentPlayerChanged(PlayerColor newPlayerColor);
}
