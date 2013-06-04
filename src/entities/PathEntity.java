package entities;

import infrastructure.PathUtil;
import infrastructure.Util;
import javafx.scene.Node;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

public abstract class PathEntity extends Entity{
	Vec2[] worldPPoints;
	Vec2[] localPPoints;
	public PathEntity(Vec2[] wp){
		this.worldPPoints = wp;
		this.localPPoints = PathUtil.PWorldToPLocal(worldPPoints);
		this.xPos = Util.toPosX(PathUtil.posX(wp));
		this.yPos = Util.toPosY(PathUtil.posY(wp));
		this.width = Util.toWidth(PathUtil.width(wp));
		this.height = Util.toHeight(PathUtil.height(wp));
		create();
	}

	@Override
	//WORKING
	protected Node createNode() {
		return PathUtil.makeNode(localPPoints);
	}

	@Override
	protected Shape createShape() {
		return PathUtil.makeShape(PathUtil.shapePoints(PathUtil.PWorldToLocal(worldPPoints)));
	}
}
