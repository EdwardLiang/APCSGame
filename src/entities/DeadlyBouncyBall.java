package entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class DeadlyBouncyBall extends BouncyBall {
	public DeadlyBouncyBall(float posX, float posY, int radius, Color color) {
		super(posX, posY, radius, color);
		((Circle) node).setFill(Color.RED);
	}
	
	public static DeadlyBouncyBall parse(String[] frag) {
		return new DeadlyBouncyBall(Float.parseFloat(frag[1]),
				Float.parseFloat(frag[2]), Integer.parseInt(frag[5]),
				Color.web(frag[6]));
	}
}
