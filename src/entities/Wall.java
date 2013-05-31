package entities;

import infrastructure.Utility;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
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
		rectangle.setHeight(Utility.toPixelHeight(height));
		rectangle.setWidth(Utility.toPixelWidth(width));
		rectangle.setFill(Color.DARKSLATEGRAY);
		return rectangle;
	}

	@Override
	protected Shape createShape() {
		PolygonShape polygon = new PolygonShape();
		((PolygonShape) polygon).setAsBox(width / 2, height / 2);
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

	public static Wall parse(String[] frag) {
		return new Wall(Float.parseFloat(frag[1]), Float.parseFloat(frag[2]),
				Float.parseFloat(frag[3]), Float.parseFloat(frag[4]));
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
