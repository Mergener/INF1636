package model;

public abstract class MatchState {

	private Match match;
	
	public Match getMatch() {
		return match;
	}
	
	public MatchState(Match match) {
		this.match = match;
	}
	
	protected void onBegin() {
	}
	
	protected void onFinish() {
	}
}
