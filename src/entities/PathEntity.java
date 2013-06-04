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
		super(PathUtil.posX(wp), PathUtil.posY(wp), PathUtil.width(wp), PathUtil.height(wp));
		this.worldPPoints = wp;
		this.localPPoints = PathUtil.PWorldToPLocal(worldPPoints);
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
