package entities;

import java.io.Serializable;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import infrastructure.App;
import infrastructure.FXJBox;
import infrastructure.GameMap;
import infrastructure.Parse;
import infrastructure.Util;
import javafx.scene.Node;

public abstract class Entity implements FXJBox, Serializable {
	public Node node;
	protected float xPos;
	protected float yPos;
	protected float width;
	protected float height;
	protected BodyDef bd;
	protected FixtureDef fd;
	protected Shape ps;

	public GameMap world;

	public Entity(float posX, float posY, float width, float height) {
		this.xPos = posX;
		this.yPos = posY;
		this.width = width;
		this.height = height;
	}

	public Entity(float posX, float posY) {
		this.xPos = posX;
		this.yPos = posY;
	}

	protected void create() {
		node = createNode();
		bd = createBD();
		ps = createShape();
		fd = createFD();
		node.setLayoutX(Util.toPPosX(xPos) - Util.toPWidth(width) / 2);
		node.setLayoutY(Util.toPPosY(yPos) - Util.toPWidth(height) / 2);
	}

	protected abstract Node createNode();

	protected abstract Shape createShape();

	protected abstract BodyDef createBD();

	protected abstract FixtureDef createFD();

	public void addToWorld(GameMap world) {
		if (world != null) {
			removeFromWorld();
		}
		this.world = world;
		Body body = world.getPhysics().createBody(bd);
		body.createFixture(fd);
		node.setUserData(body);
		world.addEntity(this);
	}

	public void setVisible(Boolean bool) {
		if (bool == true)
			App.root.getChildren().add(node);
		else if (App.root.getChildren().contains(node) == true)
			App.root.getChildren().remove(this);
	}

	public void removeFromWorld() {
		world.removeEntity(this);
		world.getPhysics().destroyBody((Body) this.getBody());
		setVisible(false);
		world = null;
	}

	public void setLayoutX(float x) {
		node.setLayoutX(x);
	}

	public void setLayoutY(float y) {
		node.setLayoutY(y);
	}

	public Body getBody() {
		return (Body) getBody();
	}

	public Vec2 getPosition() {
		return ((Body) getBody()).getPosition();
	}

	public Vec2 getPPosition() {
		return new Vec2(Util.toPPosX(getPosition().x),
				Util.toPPosY(getPosition().y));
	}

	public double getPWidth() {
		return Util.toPWidth(width);
	}

	public double getPHeight() {
		return Util.toPHeight(height);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "" + this.getClass() + Parse.delim + xPos + Parse.delim + yPos
				+ Parse.delim + width + Parse.delim + height;
	}
}
