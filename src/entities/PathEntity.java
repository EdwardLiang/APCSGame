package entities;

import infrastructure.App;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import utils.Parse;
import utils.PathUtil;
import utils.Util;

public abstract class PathEntity extends Entity {
	Vec2[] worldPPoints;
	Vec2[] localPPoints;
	Vec2[] local;
	float offsetX;
	float offsetY;

	public PathEntity(Vec2[] wp) {
		this.worldPPoints = wp;
		this.localPPoints = PathUtil.PWorldToPLocal(worldPPoints);
		this.local = PathUtil.PWorldToLocal(worldPPoints);
		if (App.camera != null) {
			this.xPos = Util
					.toPosX(PathUtil.posX(wp) - App.camera.getOffsetX());
			this.yPos = Util
					.toPosY(PathUtil.posY(wp) - App.camera.getOffsetY());
		} else {
			this.xPos = Util.toPosX(PathUtil.posX(wp));
			this.yPos = Util.toPosY(PathUtil.posY(wp));
		}
		this.width = Util.toWidth(PathUtil.width(wp));
		this.height = Util.toHeight(PathUtil.height(wp));
		create();
	}

	public PathEntity(Vec2[] lp, Vec2[] local, float x, float y, float width,
			float height) {
		this.localPPoints = lp;
		this.local = local;
		this.width = width;
		this.height = height;
		this.xPos = x;
		this.yPos = y;
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
				.shapePoints(local));
		return shape;
	}

	@Override
	public String toString() {
		return "" + this.getClass() + Parse.delim + xPos + Parse.delim + yPos
				+ Parse.delim + PathUtil.pToString(localPPoints) + Parse.delim
				+ PathUtil.pToString(local) + Parse.delim + width + Parse.delim
				+ height;
	}
}
