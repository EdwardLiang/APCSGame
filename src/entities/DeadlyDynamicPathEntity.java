package entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import org.jbox2d.common.Vec2;

public class DeadlyDynamicPathEntity extends DynamicPathEntity{

	public DeadlyDynamicPathEntity(Vec2[] verts) {
		super(verts);
		// TODO Auto-generated constructor stub
		((Polygon)node).setFill(Color.RED);
	}

}
