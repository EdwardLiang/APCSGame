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

	public GameMap map;

	public Entity(float posX, float posY, float width, float height) {
		this.xPos = posX;
		this.yPos = posY;
		this.width = width;
		this.height = height;
		create();

	}

	public Entity(float posX, float posY) {
		this.xPos = posX;
		this.yPos = posY;
		create();
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

	public void addToMap(GameMap map) {
		if (this.map != null) {
			removeFromMap();
		}
		this.map = map;
		Body body = map.getPhysics().createBody(bd);
		body.createFixture(fd);
		node.setUserData(body);
		map.addEntity(this);
	}

	public void setVisible(Boolean bool) {
		if (bool == true)
			App.root.getChildren().add(node);
		else if (App.root.getChildren().contains(node) == true)
			App.root.getChildren().remove(this);
	}

	public void removeFromMap() {
		map.removeEntity(this);
		map.getPhysics().destroyBody((Body) this.getBody());
		setVisible(false);
		map = null;
	}

	public synchronized void setLayoutX(float x) {
		node.setLayoutX(x);
	}

	public synchronized void setLayoutY(float y) {
		node.setLayoutY(y);
	}

	public synchronized Body getBody() {
		return (Body) node.getUserData();
	}

	public synchronized Vec2 getPosition() {
		return ((Body) getBody()).getPosition();
	}

	public synchronized Vec2 getPPosition() {
		return new Vec2(Util.toPPosX(getPosition().x),
				Util.toPPosY(getPosition().y));
	}

	public synchronized double getPWidth() {
		return Util.toPWidth(width);
	}

	public synchronized double getPHeight() {
		return Util.toPHeight(height);
	}

	public synchronized float getWidth() {
		return width;
	}

	public synchronized float getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "" + this.getClass() + Parse.delim + xPos + Parse.delim + yPos
				+ Parse.delim + width + Parse.delim + height;
	}
}
