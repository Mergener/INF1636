package exceptions;

public class IllegalMigration extends Exception {

	private static final long serialVersionUID = -1274571709455530328L;

	public IllegalMigration() {
	}

	public IllegalMigration(String msg) {
		super(msg);
	}

}
