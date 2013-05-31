package entities;

import infrastructure.Parse;
import infrastructure.Utility;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BouncyBall extends Entity {
	private int radius;
	private Color color;

	public BouncyBall(float posX, float posY, int radius, Color color) {
		super(posX, posY);
		this.radius = radius;
		this.color = color;
	}

	@Override
	protected void create() {
		node = createNode();
		bd = createBD();
		ps = createShape();
		fd = createFD();
		node.setLayoutX(Utility.toPixelPosX(xPos));
		node.setLayoutY(Utility.toPixelPosY(yPos));
	}

	@Override
	protected Node createNode() {
		Circle ball = new Circle(radius);
		ball.setLayoutX(Utility.toPixelPosX(xPos));
		ball.setLayoutY(Utility.toPixelPosY(yPos));
		return ball;
	}

	@Override
	protected Shape createShape() {
		Shape shape = new CircleShape();
		shape.m_radius = Utility.toWidth(radius);
		return shape;
	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(xPos, yPos);
		bodyDef.fixedRotation = true;
		return bodyDef;
	}

	@Override
	protected FixtureDef createFD() {
		FixtureDef fix = new FixtureDef();
		fix.shape = ps;
		fix.density = 0.6f;
		fix.friction = 0.3f;
		fix.restitution = 0f;
		return fix;
	}

	public static BouncyBall parse(String[] frag) {
		return new BouncyBall(Float.parseFloat(frag[1]),
				Float.parseFloat(frag[2]), Integer.parseInt(frag[3]),
				Color.web(frag[4]));
	}

	@Override
	public String toString() {
		return super.toString() + Parse.delim + radius + Parse.delim
				+ color.toString();
	}

}
