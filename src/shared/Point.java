package shared;

import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = 7857885385922265185L;
	
	public final float x;
	public final float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
