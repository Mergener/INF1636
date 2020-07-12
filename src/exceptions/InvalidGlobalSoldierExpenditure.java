package exceptions;

public class InvalidGlobalSoldierExpenditure extends Exception {

	private static final long serialVersionUID = 4724399637469001538L;

	public InvalidGlobalSoldierExpenditure(String msg) {
		super(msg);
	}
	
	public InvalidGlobalSoldierExpenditure() {
	}

}
