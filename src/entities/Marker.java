package entities;

import org.jbox2d.common.Vec2;

import infrastructure.App;
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

	public void setVisible(boolean bool) {
		if (bool == true) {
			if (App.root.getChildren().contains(node) == true) {
				App.root.getChildren().remove(node);
				App.root.getChildren().add(node);
			} else
				App.root.getChildren().add(node);
		} else if (App.root.getChildren().contains(node) == true)
			App.root.getChildren().remove(this);
	}

	private Node create() {
		Circle ball = new Circle(8);
		ball.setLayoutX(xPPos);
		ball.setLayoutY(yPPos);
		return ball;
	}
}
