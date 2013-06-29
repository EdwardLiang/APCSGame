package entities;

import infrastructure.App;
import infrastructure.DynamicEntityData;
import infrastructure.EntityData;
import infrastructure.Frame;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import utils.PathUtil;

public class DynamicPathEntity extends PathEntity {
	public double previousRotation;

	public DynamicPathEntity(Vec2[] verts) {
		super(verts);
	}

	public DynamicPathEntity(Vec2[] lp, Vec2[] local, float x, float y,
			float width, float height) {
		super(lp, local, x, y, width, height);
	}

	public synchronized double getPreviousRotation() {
		return previousRotation;
	}

	public synchronized void setPreviousRotation(double rotation) {
		previousRotation = rotation;
	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(xPos, yPos);
		bodyDef.fixedRotation = false;
		
		bodyDef.allowSleep = true;
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

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public synchronized DynamicEntityData backUp() {
		return new DynamicEntityData(this);
	}

	@Override
	public synchronized void update() {
		super.update();
		float theta = -getBody().getAngle();
		node.setRotate(Math.toDegrees(theta));
	}

	@Override
	public synchronized void restore(Frame frame) {
		getBody().setAngularVelocity(0);
		getBody().setLinearVelocity(new Vec2(0, 0));
		EntityData data = frame.getData().get(this);
		getBody().setTransform(
				new Vec2(data.getX(), data.getY()),
				(float) Math.toRadians(-((DynamicEntityData) data)
						.getPreviousRotation()));
	}
}
