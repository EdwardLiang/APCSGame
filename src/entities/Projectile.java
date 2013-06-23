package entities;

import javafx.scene.Node;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

import utils.Parse;
import utils.Util;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Projectile extends Entity {
	private float radius;

	public Projectile(float posX, float posY, float width, float height,
			float radius) {
		super(posX, posY, width + radius, height);
		this.radius = radius;
	}

	@Override
	protected Node createNode() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(
				new Double[] { 0.0, 0.0,
						(double) Util.toPWidth(width - radius), 0.0,
						(double) Util.toPWidth(width),
						(double) Util.toPHeight(height) / 2,
						(double) Util.toPWidth(width - radius),
						(double) Util.toPHeight(height), 0.0,
						(double) Util.toPHeight(height) });
		polygon.setFill(Color.DARKBLUE);
		return polygon;
	}

	@Override
	protected Shape createShape() {
		Vec2[] verts = new Vec2[5];
		verts[0] = new Vec2(-(width) / 2, -height / 2); // bottom left
		verts[1] = new Vec2((width - radius) / 2, -height / 2); // bottom middle
		verts[2] = new Vec2(width / 2, 0);// middle right
		verts[3] = new Vec2((width - radius) / 2, height / 2); // top middle
		verts[4] = new Vec2(-(width) / 2, height / 2); // top left

		PolygonShape polygon = new PolygonShape();
		polygon.set(verts, 5);
		polygon.m_centroid.setZero();

		return polygon;

	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KINEMATIC;
		bodyDef.position.set(xPos, yPos);
		bodyDef.fixedRotation = true;
		bodyDef.userData = this.getClass();
		return bodyDef;
	}

	@Override
	protected FixtureDef createFD() {
		FixtureDef fix = new FixtureDef();
		fix.shape = ps;
		fix.density = 1.6f;
		fix.friction = 0.3f;
		fix.restitution = 0.0f;
		return fix;
	}
	public static Projectile parse(String[] frag){
		return new Projectile(Float.parseFloat(frag[1]),
				Float.parseFloat(frag[2]), Float.parseFloat(frag[3]),
				Float.parseFloat(frag[4]), Float.parseFloat(frag[5]));
	}

	@Override
	public String toString() {
		return super.toString() + Parse.delim + radius;
	}

}
