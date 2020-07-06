package listeners;

import shared.GameState;

public interface GameStateChangeListener {
	public void onGameStateChanged(GameState newState);
}
