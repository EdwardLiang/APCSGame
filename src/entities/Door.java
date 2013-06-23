package entities;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import utils.Util;

public class Door extends Entity {
	public Node node;

	public Door(float posX, float posY) {
		super(posX, posY);
	}

	@Override
	protected Node createNode() {
		Image image = new Image("sprites/door6.png");
		ImageView imageView = new ImageView(image);
		this.width = (float) Util.toWidth(imageView.getFitWidth());
		this.height = (float) Util.toHeight(imageView.getFitHeight());
		return imageView;
	}

	@Override
	protected Shape createShape() {
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(width / 2, height / 2);
		return polygon;
	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(xPos, yPos);
		bodyDef.fixedRotation = true;
		bodyDef.userData = this.getClass();
		return bodyDef;
	}

	@Override
	protected FixtureDef createFD() {
		FixtureDef fix = new FixtureDef();
		fix.shape = ps;
		fix.density = 1.0f;
		fix.friction = 0.2f;
		fix.restitution = 0f;
		fix.isSensor = true;
		return fix;
	}

	public static Door parse(String[] frag) {
		return new Door(Float.parseFloat(frag[0]), Float.parseFloat(frag[1]));
	}
}
