package data;

import java.awt.Color;
import java.util.HashMap;

public enum PlayerColor {
	Red,
	Green,
	Blue,
	Black,
	Yellow,
	White;
	
	private static HashMap<String, PlayerColor> colorNamesToColors;
	
	public Color getRGBColor() {
		switch (this) {
		case Red:
			return Color.RED;
		case Green:
			return Color.GREEN;
		case Blue:
			return Color.BLUE;
		case Yellow:
			return Color.YELLOW;
		case White:
			return Color.WHITE;
		case Black:
		default:
			return Color.BLACK;
		}
	}
	
	public static PlayerColor getColorByName(String name) {
		if (colorNamesToColors == null) {
			colorNamesToColors = new HashMap<String, PlayerColor>();

			colorNamesToColors.put("red", PlayerColor.Red);
			colorNamesToColors.put("blue", PlayerColor.Blue);
			colorNamesToColors.put("green", PlayerColor.Green);
			colorNamesToColors.put("black", PlayerColor.Black);
			colorNamesToColors.put("yellow", PlayerColor.Yellow);
			colorNamesToColors.put("white", PlayerColor.White);
		}
		
		return colorNamesToColors.get(name.toLowerCase());
	}
}
