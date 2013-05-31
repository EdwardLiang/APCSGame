package entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import infrastructure.App;
import infrastructure.GameMap;
import infrastructure.Parse;
import infrastructure.Utility;
import javafx.scene.Node;

public abstract class Entity {
	public Node node;
	public float xPos;
	public float yPos;
	public float width;
	public float height;
	public BodyDef bd;
	public FixtureDef fd;
	public Shape ps;

	public GameMap world;

	public Entity(float posX, float posY, float width, float height) {
		xPos = posX;
		yPos = posY;
		this.width = width;
		this.height = height;
	}

	public Entity(float posX, float posY) {
		xPos = posX;
		yPos = posY;
	}

	protected void create() {
		node = createNode();
		bd = createBD();
		ps = createShape();
		fd = createFD();
		node.setLayoutX(Utility.toPixelPosX(xPos) - Utility.toPixelWidth(width)
				/ 2);
		node.setLayoutY(Utility.toPixelPosY(yPos)
				- Utility.toPixelWidth(height) / 2);
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
		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		node.setUserData(body);
		App.root.getChildren().add(node);
		world.addEntity(this);
	}

	public void removeFromWorld() {
		world.removeEntity(this);
		world.world.destroyBody((Body) this.node.getUserData());
		App.root.getChildren().remove(this);
		world = null;
	}

	@Override
	public String toString() {
		return "" + this.getClass() + Parse.delim + xPos + Parse.delim + yPos
				+ Parse.delim + width + Parse.delim + height;
	}
}
