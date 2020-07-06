package exceptions;

public class NotEnoughPlayers extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5678658364191614061L;

	public NotEnoughPlayers() {
		super();
	}
	
	public NotEnoughPlayers(String msg) {
		super(msg);
	}

}
