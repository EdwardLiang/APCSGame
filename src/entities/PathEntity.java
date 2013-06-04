package entities;

import infrastructure.PathUtil;
import infrastructure.Util;
import javafx.scene.Node;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

public abstract class PathEntity extends Entity{
	String url;
	Vec2[] verts;
	public PathEntity(String url){
		this.url = url;
	}
	public PathEntity(Vec2[] verts){
		this.verts = verts;
	}
	@Override
	protected Node createNode() {
		return shape = url == null ? new PolygonShape(verts) : PathUtil.readToNode(url));
	}
	@Override
	protected Shape createShape() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected BodyDef createBD() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected FixtureDef createFD() {
		// TODO Auto-generated method stub
		return null;
	}
}
