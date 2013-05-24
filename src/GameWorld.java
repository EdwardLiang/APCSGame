import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;


public class GameWorld extends GameMap{
	public static final ArrayList<Entity> gameElements = new ArrayList<Entity>();
	public static final World world = new World(new Vec2(0.0f, -10.0f));
	public static final Time time = new Time();
	public static final Entity player = new BouncyBall(45, 50, Utility.BALL_RADIUS, Color.BLUE);
	public static final ArrayList<GameMap> gameMaps = new ArrayList<GameMap>();
	
	public GameWorld(){
		Utility.addGround(100,10);
		Utility.addWall(0, 100, 1, 100);
		Utility.addWall(99,100,1,100);
		Utility.addWall(0,100,100,1);
		gameElements.add(player);
		for(Entity a: gameElements){
			System.out.println(a.node);
		}
	}
}
