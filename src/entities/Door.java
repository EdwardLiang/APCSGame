package entities;

import infrastructure.App;
import infrastructure.GameMap;
import infrastructure.Parse;
import infrastructure.Util;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

public class Door {
	public Node node;
	protected double xPPos;
	protected double yPPos;

	public Door(double xPPos, double yPPos) {
		this.xPPos = xPPos;
		this.yPPos = yPPos;
		node = create();
	}

	public Vec2 pCoord() {
		return new Vec2((float)xPPos, (float)yPPos);
	}

	public Vec2 coord() {
		return new Vec2((float)Util.toPosX(xPPos), (float)Util.toPosY(yPPos));
	}

	public void setVisible(boolean bool) {
		if (bool == true) {
			if (App.root.getChildren().contains(node) == true) {
				App.root.getChildren().remove(node);
				App.root.getChildren().add(node);
			} else
				App.root.getChildren().add(node);
		} else if (App.root.getChildren().contains(node) == true)
			App.root.getChildren().remove(node);
	}

	private Node create() {
		Image image = new Image("sprites/door6.png");
		ImageView imageView = new ImageView(image);
		imageView.setLayoutX(xPPos);
		imageView.setLayoutY(yPPos);
		return imageView;
	}
	public static Door parse(String[] frag){
		return new Door(Double.parseDouble(frag[0]), Double.parseDouble(frag[1]));
	}
	public String toString(){
		return "" +  xPPos + Parse.delim + yPPos;
	}

}
