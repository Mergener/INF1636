package listeners;

import controller.GameState;

public interface IGameStateChangeListener {
	public void onGameStateChanged(GameState newState);
}
