package Game;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class GameWorld{
	public ArrayList<Entity> gameElements;
	public World world;
	public Time time;
	
	public GameWorld(){
		world = new World(new Vec2(0.0f, -10.0f));
		gameElements = new ArrayList<Entity>();
		time = new Time(this);
	}
	
	//Use Entity's addToWorld method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public void addEntity(Entity entity){
		gameElements.add(entity);
	}
}
