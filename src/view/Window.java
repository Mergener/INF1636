package view;

import javax.swing.JFrame;

public abstract class Window {
	private JFrame frame = new JFrame();
	public JFrame getFrame() {
		return frame;
	}
	
	private View currentView;
	public View getCurrentView() {
		return currentView;
	}
	
	public void setCurrentView(View v) {
		if (currentView != null) {
			currentView.onExit(v);
		}
		
		View previousView = currentView;
		currentView = v;
		
		if (currentView != null) {
			currentView.onEnter(previousView);
		}
	}
	
	public void start() {
		frame.setVisible(true);
		onStart();
	}
	
	protected abstract void onStart();
}
