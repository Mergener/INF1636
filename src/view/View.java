package view;

public abstract class View {
	private Window window;
	public Window getWindow() {
		return window;
	}
	
	public View(Window window) {
		this.window = window;
	}
	
	protected void setCurrentView(View v) {
		window.setCurrentView(v);
	}
	
	protected abstract void onEnter(View previousView);
	protected abstract void onExit(View nextView);
}
