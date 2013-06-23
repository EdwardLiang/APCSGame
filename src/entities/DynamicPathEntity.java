package entities;

import infrastructure.App;
import infrastructure.DynamicEntityData;
import infrastructure.EntityData;

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
		bodyDef.userData = this.getClass();
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

	public static DynamicPathEntity parse(String[] frags) {
		return new DynamicPathEntity(PathUtil.parseVec2(frags[3]),
				PathUtil.parseVec2(frags[4]), Float.parseFloat(frags[1]),
				Float.parseFloat(frags[2]), Float.parseFloat(frags[5]),
				Float.parseFloat(frags[6]));
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
		getBody().setAngularVelocity(0);
		getBody().setLinearVelocity(new Vec2(0, 0));
		float xpos = (float) (getPPosition().x + App.camera.getOffsetX() - getPWidth() / 2);
		float ypos = (float) (getPPosition().y + App.camera.getOffsetY() - getPHeight() / 2);
		float theta = -getBody().getAngle();
		setLayoutX(xpos);
		setLayoutY(ypos);
		node.setRotate(Math.toDegrees(theta));
	}

	@Override
	public synchronized void update(EntityData data) {
		getBody().setTransform(
				new Vec2(data.getX(), data.getY()),
				(float) Math.toRadians(-((DynamicEntityData) data)
						.getPreviousRotation()));
		update();
	}
}
