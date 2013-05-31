package entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import infrastructure.App;
import infrastructure.GameMap;
import infrastructure.Parse;
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

	public abstract Node create();

	public void addToWorld(GameMap world) {
		if(world != null){
			removeFromWorld();
		}
		this.world = world;
		node = create();
		
		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		node.setUserData(body);
		App.root.getChildren().add(node);
		world.addEntity(this);
	}
	public void removeFromWorld(){
		world.removeEntity(this);
		world.world.destroyBody((Body)this.node.getUserData());
		App.root.getChildren().remove(this);
	}
	@Override
	public String toString(){
		return "" + this.getClass() + Parse.delim + xPos + Parse.delim + yPos + Parse.delim + width + Parse.delim + height;
	}
}
