package entities;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import utils.PathUtil;

public class StaticPathEntity extends PathEntity {

	public StaticPathEntity(Vec2[] verts) {
		super(verts);
		/*Image clouds = new Image("sprites/DeathTexture.jpg");
		((Polygon) node).setFill(new ImagePattern(clouds));*/

	}

	public StaticPathEntity(Vec2[] lp, Vec2[] local, float x, float y,
			float width, float height) {
		super(lp, local, x, y, width, height);
		/*Image clouds = new Image("sprites/DeathTexture.jpg");
		((Polygon) node).setFill(new ImagePattern(clouds));*/
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
		fix.friction = 1.0f;
		return fix;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
