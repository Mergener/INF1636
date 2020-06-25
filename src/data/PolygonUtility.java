package data;

public class PolygonUtility {

	private PolygonUtility() {
	}

    private static boolean onSegment(Point p, Point q, Point r) { 
        if (q.x <= Math.max(p.x, r.x) && 
            q.x >= Math.min(p.x, r.x) && 
            q.y <= Math.max(p.y, r.y) && 
            q.y >= Math.min(p.y, r.y)) { 
            return true; 
        } 
        return false; 
    } 
  
    private static float orientation(Point p, Point q, Point r) { 
        float val = (q.y - p.y) * (r.x - q.x) 
                - (q.x - p.x) * (r.y - q.y); 
  
        if (val == 0)  { 
            return 0;
        } 
        return (val > 0) ? 1 : 2; 
    } 
  
    private static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) { 
        float o1 = orientation(p1, q1, p2); 
        float o2 = orientation(p1, q1, q2); 
        float o3 = orientation(p2, q2, p1); 
        float o4 = orientation(p2, q2, q1); 
  
        if (o1 != o2 && o3 != o4) { 
            return true; 
        } 
        if (o1 == 0 && onSegment(p1, p2, q1)) { 
            return true; 
        } 
        if (o2 == 0 && onSegment(p1, q2, q1)) { 
            return true; 
        } 
        if (o3 == 0 && onSegment(p2, p1, q2)) { 
            return true; 
        } 
        if (o4 == 0 && onSegment(p2, q1, q2)) { 
            return true; 
        } 
  
        return false;  
    } 
  
    public static boolean pointInsidePolygon(Point[] polygon, Point p) { 
    	int n = polygon.length;
        if (n < 3) { 
            return false; 
        } 
  
        Point extreme = new Point(Float.POSITIVE_INFINITY, p.y); 
  
        int count = 0, i = 0; 
        do { 
            int next = (i + 1) % n; 
  
            if (doIntersect(polygon[i], polygon[next], p, extreme)) { 
                if (orientation(polygon[i], p, polygon[next]) == 0) { 
                    return onSegment(polygon[i], p, polygon[next]); 
                } 
  
                count++; 
            } 
            i = next; 
        } while (i != 0); 
  
        return (count % 2 == 1); 
    } 
}
