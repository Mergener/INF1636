package listeners;

import shared.GameContext;

public interface GameContextChangeListener {
	public void onGameContextChanged(GameContext newCtx);
}
