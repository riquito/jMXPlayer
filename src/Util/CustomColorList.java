package src.Util;

import java.awt.Color;

public class CustomColorList {
	private static int index = 0;
	private static int alpha = 51;

	/*
	 * Color.BLUE, Color.CYAN, Color.WHITE, Color.GREEN, Color.MAGENTA,
	 * Color.ORANGE, Color.PINK, Color.YELLOW, Color.RED, Color.BLUE.darker(),
	 * Color.CYAN.darker(), Color.WHITE.darker(), Color.GREEN.darker(),
	 * Color.MAGENTA.darker(), Color.ORANGE.darker(), Color.PINK.darker(),
	 * Color.YELLOW.darker(), Color.RED.darker()
	 */

	private static Color[] colorList = { Color.decode("#FF9900"), Color.decode("#FFFF00"), Color.decode("#FF3300"),
			Color.decode("#CCFF00"), Color.decode("#CC9900"), Color.decode("#99FF00"), Color.decode("#999900"),
			Color.decode("#FF3366"), Color.decode("#FF0099"), Color.decode("#FF99CC"), Color.decode("#9933CC"),
			Color.decode("#33FFCC"), Color.decode("#3333CC"), Color.decode("#FFCCFF"), Color.decode("#CC99FF"),
			Color.decode("#3399FF"), Color.decode("#9966FF"), Color.decode("#3300CC"), Color.decode("#FFFFCC") };
	
	public static Color getColor() {
		Color returnedColor = new Color(colorList[index].getRed(), colorList[index].getGreen(), colorList[index].getBlue(), alpha);
		
		if (index == colorList.length) {
			index = 0;
		} else {
			index = index + 1;
		}
		
		return returnedColor;
	}
	
	public static int getAlpha() {
		return alpha;
	}
}
