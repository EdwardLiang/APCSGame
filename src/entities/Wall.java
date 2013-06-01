package entities;

import infrastructure.Util;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Entity {
	public Wall(float posX, float posY, float width, float height) {
		super(posX, posY, width, height);
	}

	@Override
	protected Node createNode() {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(Util.toPHeight(height));
		rectangle.setWidth(Util.toPWidth(width));
		rectangle.setFill(Color.DARKSLATEGRAY);
		return rectangle;
	}

	@Override
	protected Shape createShape() {
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(width / 2, height / 2);
		return polygon;
	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(xPos, yPos);
		bodyDef.type = BodyType.STATIC;
		return bodyDef;
	}

	@Override
	protected FixtureDef createFD() {
		FixtureDef fix = new FixtureDef();
		fix.shape = ps;
		fix.density = 1.0f;
		fix.friction = 0.3f;
		return fix;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
