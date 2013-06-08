package utils;

import org.jbox2d.common.Vec2;

public class Util {
	// Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
	public static float toPPosX(float posX) {
		float x = WIDTH * posX / 100.0f;
		return x;
	}

	// Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
	public static float toPosX(float posX) {
		float x = (posX * 100.0f * 1.0f) / WIDTH;
		return x;
	}

	// Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static float toPPosY(float posY) {
		float y = HEIGHT - (1.0f * HEIGHT) * posY / 100.0f;
		return y;
	}

	// Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
	public static float toPosY(float posY) {
		float y = 100.0f - ((posY * 100 * 1.0f) / HEIGHT);
		return y;
	}

	// Convert a JBox2D width to pixel width
	public static float toPWidth(float width) {
		return WIDTH * width / 100.0f;
	}

	// Convert a JBox2D height to pixel height
	public static float toPHeight(float height) {
		return HEIGHT * height / 100.0f;
	}

	public static float toWidth(float width) {
		return (width * 100.0f) / WIDTH;
	}

	public static float toHeight(float height) {
		return (height * 100.0f) / HEIGHT;
	}

	// Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
	public static double toPPosX(double posX) {
		double x = WIDTH * posX / 100.0;
		return x;
	}

	// Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
	public static double toPosX(double posX) {
		double x = (posX * 100.0 * 1.0) / WIDTH;
		return x;
	}

	// Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static double toPPosY(double posY) {
		double y = HEIGHT - (1.0 * HEIGHT) * posY / 100.0;
		return y;
	}

	// Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
	public static double toPosY(double posY) {
		double y = 100.0 - ((posY * 100 * 1.0) / HEIGHT);
		return y;
	}

	// Convert a JBox2D width to pixel width
	public static double toPWidth(double width) {
		return WIDTH * width / 100.0;
	}

	// Convert a JBox2D height to pixel height
	public static double toPHeight(double height) {
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
	public static Vec2[] toPoints(Vec2[] pPoints){
		Vec2[] points = new Vec2[pPoints.length];
		for (int a = 0; a < pPoints.length; a++) {
			points[a] = toPoint(pPoints[a]);
		}
		return points;
	}
	public static Vec2 toPPoint(Vec2 point) {
		return new Vec2(Util.toPPosX(point.x), Util.toPPosY(point.y));
	}

	public static Vec2 toPoint(Vec2 pPoint) {
		return new Vec2(Util.toPosX(pPoint.x), Util.toPosY(pPoint.y));
	}


}
