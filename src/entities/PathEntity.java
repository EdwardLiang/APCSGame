package entities;

import infrastructure.App;
import infrastructure.PathUtil;
import infrastructure.Util;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

public abstract class PathEntity extends Entity {
	Vec2[] worldPPoints;
	Vec2[] localPPoints;

	public PathEntity(Vec2[] wp) {
		this.worldPPoints = wp;
		this.localPPoints = PathUtil.PWorldToPLocal(worldPPoints);
		this.xPos = Util.toPosX(PathUtil.posX(wp) - App.camera.getOffsetX());
		this.yPos = Util.toPosY(PathUtil.posY(wp) - App.camera.getOffsetY());
		this.width = Util.toWidth(PathUtil.width(wp));
		this.height = Util.toHeight(PathUtil.height(wp));
		create();
	}

	public PathEntity() {
	}

	@Override
	protected Node createNode() {
		Node node = PathUtil.makeNode(localPPoints);
		((Polygon) node).setFill(Color.DARKSLATEGREY);
		return node;
	}

	@Override
	protected Shape createShape() {
		PolygonShape shape = (PolygonShape) PathUtil.makeShape(PathUtil
				.shapePoints(PathUtil.PWorldToLocal(worldPPoints)));
		return shape;
	}
}
