package Game;

import javafx.scene.Node;

public abstract class Entity {
	public Node node;
	public float xPos;
	public float yPos;
	public float width;
	public float height;

	public GameLevel world;

	public abstract Node create();

	public void addToWorld(GameLevel world) {
		if(world != null){
			world.removeEntity(this);
		}
		this.world = world;
		node = create();
		world.addEntity(this);
	}
	@Override
	public String toString(){
		return "" + this.getClass() + Parse.delim + xPos + Parse.delim + yPos + Parse.delim + width + Parse.delim + height;
	}
}
