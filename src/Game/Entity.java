package Game;
import javafx.scene.Node;


public abstract class Entity {
	public Node node;
	public float xPos;
	public float yPos;
	public float width;
	public float height;
	
	public GameWorld world;
	public abstract Node create();
	
	public void addToWorld(GameWorld world){
		this.world = world;
		node = create();
		world.addEntity(this);
	}
}
