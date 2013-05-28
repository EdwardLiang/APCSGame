package Game;

import javafx.scene.Node;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Projectile extends Entity {
	public float radius;

	public Projectile(float posX, float posY, float width, float height,
			float radius) {
		xPos = posX;
		yPos = posY;
		this.width = width + radius;
		this.height = height;
		this.radius = radius;
	}

	public Node create() {
		Polygon polygon = new Polygon();
		// polygon.getPoints().addAll(
		// new Double[] { 0.0, 0.0, 20.0, 10.0, 10.0, 20.0 });
		polygon.getPoints().addAll(
				new Double[] { 0.0, 0.0,
						(double) Utility.toPixelWidth(width - radius), 0.0,
						(double) Utility.toPixelWidth(width),
						(double) Utility.toPixelHeight(height) / 2,
						(double) Utility.toPixelWidth(width - radius),
						(double) Utility.toPixelHeight(height), 0.0,
						(double) Utility.toPixelHeight(height) });
		// polygon.setFill(Color.RED);

		// polygon.getPoints().addAll(fxCoords);
		polygon.setFill(Color.DARKBLUE);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.position.set(xPos, yPos);
		bd.fixedRotation = true;
		polygon.setLayoutX(Utility.toPixelPosX(xPos)
				- Utility.toPixelWidth(width) / 2);
		polygon.setLayoutY(Utility.toPixelPosY(yPos)
				- Utility.toPixelWidth(height) / 2);

		// CircleShape cs = new CircleShape();
		// cs.m_radius = 8*0.1f;

		PolygonShape ps = new PolygonShape();
		ps.m_count = 5;
		ps.m_vertices[0] = new Vec2(0, 0);
		ps.m_vertices[1] = new Vec2(0, height);
		ps.m_vertices[2] = new Vec2(width - radius, height);
		ps.m_vertices[3] = new Vec2(width, height / 2);
		ps.m_vertices[4] = new Vec2(width - radius, 0);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.6f;
		fd.friction = 0.3f;
		fd.restitution = 0.8f;

		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		polygon.setUserData(body);

		return polygon;
	}

}
