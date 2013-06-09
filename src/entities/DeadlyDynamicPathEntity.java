package entities;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import org.jbox2d.common.Vec2;

public class DeadlyDynamicPathEntity extends DynamicPathEntity{

	public DeadlyDynamicPathEntity(Vec2[] verts) {
		super(verts);
		// TODO Auto-generated constructor stub
		Image clouds = new Image("sprites/DeathTexture.jpg");
		((Polygon)node).setFill(new ImagePattern(clouds));
	}

}
