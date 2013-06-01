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
		world.getPhysics().destroyBody((Body) this.node.getUserData());
		setVisible(false);
		world = null;
	}

	@Override
	public String toString() {
		return "" + this.getClass() + Parse.delim + xPos + Parse.delim + yPos
				+ Parse.delim + width + Parse.delim + height;
	}
}
