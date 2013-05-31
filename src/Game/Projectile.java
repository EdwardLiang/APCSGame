package Game;

import javafx.scene.Node;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.collision.shapes.PolygonShape;
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
	//For anyone that isn't Alex or I. DONT USE this constructor if you're not sure what you're doing.
	public static Projectile parse(String[] frag){
		return new Projectile(Float.parseFloat(frag[1]), Float.parseFloat(frag[2]), Float.parseFloat(frag[3]), Float.parseFloat(frag[4]), Float.parseFloat(frag[5]));
	}

	@Override
	public Node create() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(
				new Double[] { 0.0, 0.0,
						(double) Utility.toPixelWidth(width - radius), 0.0,
						(double) Utility.toPixelWidth(width),
						(double) Utility.toPixelHeight(height) / 2,
						(double) Utility.toPixelWidth(width - radius),
						(double) Utility.toPixelHeight(height), 0.0,
						(double) Utility.toPixelHeight(height) });

		polygon.setFill(Color.DARKBLUE);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(xPos, yPos);
		bd.fixedRotation = true;
		polygon.setLayoutX(Utility.toPixelPosX(xPos)
				- Utility.toPixelWidth(width) / 2);
		polygon.setLayoutY(Utility.toPixelPosY(yPos)
				- Utility.toPixelWidth(height) / 2);

		Vec2[] verts = new Vec2[5];

		verts[0] = new Vec2(-(width) / 2, -height / 2); // bottom left
		verts[1] = new Vec2((width - radius) / 2, -height / 2); // bottom middle
		verts[2] = new Vec2(width / 2, 0);// middle right
		verts[3] = new Vec2((width - radius) / 2, height / 2); // top middle
		verts[4] = new Vec2(-(width) / 2, height / 2); // top left
		
		PolygonShape ps = new PolygonShape();
		ps.set(verts, 5);

		ps.m_centroid.setZero();

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 1.6f;
		fd.friction = 0.3f;
		fd.restitution = 0.0f;

		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		polygon.setUserData(body);

		return polygon;
	}
	@Override
	public String toString(){
		return super.toString() + Parse.delim + radius;
	}

}
