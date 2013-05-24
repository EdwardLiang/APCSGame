import javafx.scene.Node;


public abstract class Entity {
	public Node node;
	public float xPos;
	public float yPos;
	public abstract Node create();
}
