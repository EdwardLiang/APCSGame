package entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class DeadlyBouncyBall extends BouncyBall {
	public DeadlyBouncyBall(float posX, float posY, int radius, Color color) {
		super(posX, posY, radius, color);
		((Polygon) node).setFill(Color.RED);
	}
}
