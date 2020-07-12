package shared;

import java.util.Random;

public enum Geometry {
	Triangle,
	Square,
	Circle,
	
	Joker;
	
	public boolean isSame(Geometry other) {
		if (this == Joker || other == Joker) {
			return true;
		}
		
		return this == other;
	}
	
	public boolean isNotSame(Geometry other) {
		if (this == Joker || other == Joker) {
			return true;
		}

		return this != other;
	}
	
	public static Geometry getRandomExceptJoker() {
		
		Random random = new Random();
		int rand = random.nextInt(4);
		
		switch (rand) {
		default:
		case 0:
			return Geometry.Triangle;
			
		case 1:
			return Geometry.Square;
			
		case 2:
			return Geometry.Circle;
		}
	}
}