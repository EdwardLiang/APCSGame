package entities;

import java.io.IOException;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class StaticPathEntity extends PathEntity {

	public StaticPathEntity(Vec2[] verts) {
		super(verts);
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
		fix.friction = 10.3f;
		return fix;
	}

}
