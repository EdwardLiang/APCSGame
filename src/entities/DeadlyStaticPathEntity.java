package entities;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import org.jbox2d.common.Vec2;

public class DeadlyStaticPathEntity extends StaticPathEntity {
	public DeadlyStaticPathEntity(Vec2[] verts) {
		super(verts);
		Image clouds = new Image("sprites/DeathTexture.jpg");
		((Polygon) node).setFill(new ImagePattern(clouds));
	}
}
