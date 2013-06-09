package entities;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import utils.Util;

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
		Vec2[] verts = new Vec2[5];
		verts[0] = new Vec2(-(width) / 2, -height / 2); // bottom left
		verts[1] = new Vec2((width) / 2, -height / 2); // bottom right
		verts[2] = new Vec2(1 + (width) / 2, height / 2); // top right
		verts[3] = new Vec2(-1 - (width) / 2, height / 2); // top left

		((PolygonShape) polygon).set(verts, 4);
		((PolygonShape) polygon).m_centroid.setZero();

		return polygon;
	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(xPos, yPos);
		bodyDef.type = BodyType.STATIC;
		bodyDef.userData = this.getClass();
		return bodyDef;
	}

	@Override
	protected FixtureDef createFD() {
		FixtureDef fix = new FixtureDef();
		fix.shape = ps;
		fix.density = 1.0f;
		fix.friction = 1.0f;
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
