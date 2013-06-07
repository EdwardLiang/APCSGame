package entities;

import infrastructure.Parse;
import infrastructure.PathUtil;

import java.io.IOException;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class DynamicPathEntity extends PathEntity {

	public DynamicPathEntity(Vec2[] verts) {
		super(verts);
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
		fix.friction = 2.3f;
		fix.restitution = 0f;
		return fix;
	}
	public static DynamicPathEntity parse(String[] frags){
		return new DynamicPathEntity(PathUtil.parseWPPoints(frags[3]));
	}
	public String toString(){
		return super.toString();
	}

}
