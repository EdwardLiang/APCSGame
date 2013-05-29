package Game;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Utility {
	final static String delim = ";";

	// Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
	public static double toPixelPosX(double posX) {
		double x = WIDTH * posX / 100.0;
		return x;
	}

	// Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
	public static double toPosX(double posX) {
		double x = (posX * 100.0 * 1.0) / WIDTH;
		return x;
	}

	// Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static double toPixelPosY(double posY) {
		double y = HEIGHT - (1.0 * HEIGHT) * posY / 100.0;
		return y;
	}

	// Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
	public static double toPosY(double posY) {
		double y = 100.0 - ((posY * 100 * 1.0) / HEIGHT);
		return y;
	}

	// Convert a JBox2D width to pixel width
	public static double toPixelWidth(double width) {
		return WIDTH * width / 100.0;
	}

	// Convert a JBox2D height to pixel height
	public static double toPixelHeight(double height) {
		return HEIGHT * height / 100.0;
	}

	public static double toWidth(double width) {
		return (width * 100.0) / WIDTH;
	}

	public static double toHeight(double height) {
		return (height * 100.0) / HEIGHT;
	}

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	// Inspiration/Credit from:
	// http://thisiswhatiknowabout.blogspot.com/2011/12/jbox2d-with-javafx-write-your-first.html

	// Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
	public static float toPixelPosX(float posX) {
		float x = WIDTH * posX / 100.0f;
		return x;
	}

	// Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
	public static float toPosX(float posX) {
		float x = (posX * 100.0f * 1.0f) / WIDTH;
		return x;
	}

	// Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static float toPixelPosY(float posY) {
		float y = HEIGHT - (1.0f * HEIGHT) * posY / 100.0f;
		return y;
	}

	// Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
	public static float toPosY(float posY) {
		float y = 100.0f - ((posY * 100 * 1.0f) / HEIGHT);
		return y;
	}

	// Convert a JBox2D width to pixel width
	public static float toPixelWidth(float width) {
		return WIDTH * width / 100.0f;
	}

	// Convert a JBox2D height to pixel height
	public static float toPixelHeight(float height) {
		return HEIGHT * height / 100.0f;
	}

	public static float toWidth(float width) {
		return (width * 100.0f) / WIDTH;
	}

	public static float toHeight(float height) {
		return (height * 100.0f) / HEIGHT;
	}
	
	public static String[] fragment(String str){
		return str.split("[;]");
	}

	public static Color parseColor(String str) {
		switch (str) {
		case "BLUE":
			return Color.BLUE;
		case "RED":
			return Color.RED;
		default:
			return Color.AQUA;
		}

	}

	public static int codeEnt(String choice) {
		switch (choice) {
		case "ball":
		case "Ball":
		case "BouncyBall":
		case "bouncyball":
		case "Bouncyball":
			return 1;
		case "wall":
		case "Wall":
			return 2;
		case "projectile":
		case "Projectile":
			return 3;
		case "creature":
		case "Creature":
			return 4;
		default:
			throw new IllegalArgumentException("Wrong Entity type");
		}
	}
}
