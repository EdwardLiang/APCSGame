package entities;

import org.jbox2d.common.Vec2;

import infrastructure.Util;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class Marker {
	public Node node;
	protected float xPPos;
	protected float yPPos;

	public Marker(float xPPos, float yPPos) {
		this.xPPos = xPPos;
		this.yPPos = yPPos;
		node = create();
	}

	public Vec2 pCoord() {
		return new Vec2(xPPos, yPPos);
	}

	public Vec2 coord() {
		return new Vec2(Util.toPosX(xPPos), Util.toPosY(yPPos));
	}

	private Node create() {
		Circle ball = new Circle(8);
		ball.setLayoutX(xPPos);
		ball.setLayoutY(yPPos);
		return ball;
	}
}
