package entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import org.jbox2d.common.Vec2;

public class DeadlyStaticPathEntity extends StaticPathEntity {
	public DeadlyStaticPathEntity(Vec2[] verts) {
		super(verts);
		((Polygon) node).setFill(Color.RED);

	}
}
