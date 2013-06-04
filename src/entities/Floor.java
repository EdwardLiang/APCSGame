package entities;

import java.io.IOException;

import infrastructure.PathUtil;
import infrastructure.Util;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Floor extends Entity {
	String url;

	public Floor(String url) throws IOException {
		super(PathUtil.posX(url), PathUtil.posY(url), PathUtil.width(url),
				PathUtil.height(url));
		this.url = url;

	}

	@Override
	protected Node createNode() {
		try {
			return PathUtil.readToNode(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Shape createShape() {
		try {
			return PathUtil.readToShape(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(xPos, yPos);
		bodyDef.type = BodyType.STATIC;
		return bodyDef;
	}

	@Override
	protected FixtureDef createFD() {
		FixtureDef fix = new FixtureDef();
		fix.shape = ps;
		fix.density = 1.0f;
		fix.friction = 0.3f;
		return fix;
	}
}
