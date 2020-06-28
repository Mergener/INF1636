package exceptions;

public class NotEnoughPlayers extends Exception {

	public NotEnoughPlayers() {
		super();
	}
	
	public NotEnoughPlayers(String msg) {
		super(msg);
	}

}
