package shared;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;

public enum PlayerColor implements Serializable {
	Red,
	Green,
	Blue,
	Black,
	Yellow,
	White,
	_ColorCount;
	
	private static HashMap<String, PlayerColor> colorNamesToColors;
	
	public Color getRGBColor() {
		switch (this) {
		case Red:
			return new Color(255, 40, 40);
		case Green:
			return new Color(40, 255, 40);
		case Blue:
			return new Color(40, 40, 255);
		case Yellow:
			return new Color(255, 255, 40);
		case White:
			return new Color(240, 240, 240);
		case Black:
		default:
			return new Color(20, 20, 20);
		}
	}
	
	public String getName() {
		switch (this) {
		case Red:
			return "Vermelho";
		case Green:
			return "Verde";
		case Blue:
			return "Azul";
		case Yellow:
			return "Amarelo";
		case White:
			return "Branco";
		case Black:
		default:
			return "Preto";
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public String getHTMLHexString() {
		Color rgb = getRGBColor();
		
		String ret = String.format("#%x%x%x", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
		
		return ret;
	}
	
	public static PlayerColor getColorByName(String name) {
		if (colorNamesToColors == null) {
			colorNamesToColors = new HashMap<String, PlayerColor>();

			colorNamesToColors.put("vermelho", PlayerColor.Red);
			colorNamesToColors.put("azul", PlayerColor.Blue);
			colorNamesToColors.put("verde", PlayerColor.Green);
			colorNamesToColors.put("preto", PlayerColor.Black);
			colorNamesToColors.put("amarelo", PlayerColor.Yellow);
			colorNamesToColors.put("branco", PlayerColor.White);
		}
		
		return colorNamesToColors.get(name.toLowerCase());
	}
}
