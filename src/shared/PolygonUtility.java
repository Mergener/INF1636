package shared;

public class PolygonUtility {

	private PolygonUtility() {
	}
    
    public static boolean pointInsidePolygon(Point[] polygon, Point p) {
    	boolean ret = false;
    	int nVert = polygon.length;
    	
    	for (int i = 0, j = nVert-1; i < nVert; j = i++) {
    		if (((polygon[i].y > p.y) != (polygon[j].y > p.y)) &&
				(p.x < (polygon[j].x - polygon[i].x) * (p.y-polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x)) {
    			ret = !ret;
    		}
    	}
    	
    	return ret;
    } 
}
