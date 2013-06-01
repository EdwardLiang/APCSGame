package entities;

import infrastructure.Parse;
import infrastructure.Util;

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
		node.setLayoutX(Util.toPPosX(xPos));
		node.setLayoutY(Util.toPPosY(yPos));
	}

	@Override
	protected Node createNode() {
		Circle ball = new Circle(radius);
		ball.setLayoutX(Util.toPPosX(xPos));
		ball.setLayoutY(Util.toPPosY(yPos));
		return ball;
	}

	@Override
	protected Shape createShape() {
		Shape shape = new CircleShape();
		shape.m_radius = Util.toWidth(radius);
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

	@Override
	public String toString() {
		return super.toString() + Parse.delim + radius + Parse.delim
				+ color.toString();
	}

}
