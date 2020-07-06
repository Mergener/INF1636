package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public abstract class Window {
	private JFrame frame = new JFrame();
	public JFrame getFrame() {
		return frame;
	}
	
	public Window() {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				onClose();
			}
		});
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
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		if (currentView != null) {
			currentView.onEnter(previousView);
		}
	}
	
	public void start() {
		frame.setVisible(true);
		onStart();
	}
	
	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	protected abstract void onStart();
	
	protected void onClose() {
	}
}
