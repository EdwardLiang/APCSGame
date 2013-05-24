public class Utility {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

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
}
