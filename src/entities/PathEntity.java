package entities;

import infrastructure.PathUtil;
import javafx.scene.Node;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

public abstract class PathEntity extends Entity{
	Vec2[] worldPPoints;
	Vec2[] localPPoints;
	public PathEntity(Vec2[] wp){
		super();
		this.worldPPoints = wp;
		this.localPPoints = PathUtil.PWorldToPLocal(worldPPoints);
		this.xPos = PathUtil.posX(wp);
		this.yPos = PathUtil.posY(wp);
		this.width = PathUtil.width(wp);
		this.height = PathUtil.height(wp);
		create();
	}

	@Override
	protected Node createNode() {
		return PathUtil.makeNode(localPPoints);
	}

	@Override
	protected Shape createShape() {
		return PathUtil.makeShape(PathUtil.PWorldToLocal(worldPPoints));
	}
}
