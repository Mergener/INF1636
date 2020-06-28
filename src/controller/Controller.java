package controller;

import model.WarGame;

public class Controller {
	private WarGame warGame;
	protected WarGame getGame() {
		return warGame;
	}
	
	public Controller(WarGame game) {
		this.warGame = game;
	}
}
