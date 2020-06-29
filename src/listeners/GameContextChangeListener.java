package listeners;

import shared.GameState;

public interface GameContextChangeListener {
	public void onGameStateChanged(GameState newState);
}
