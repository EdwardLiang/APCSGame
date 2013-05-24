package Game;

import javafx.scene.Node;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Projectile extends Entity {
	public float width;
	public float height;
	public float radius;
	
	public Projectile(float posX, float posY,float width, float height){
		xPos = posX;
		yPos = posY;
		this.width = width;
		this.height = height;
		radius = height/2;
	}

	public Node create() {
		Polygon polygon = new Polygon();
		Vec2[] jCoords = new Vec2[]{
			    new Vec2(xPos,yPos),
			    new Vec2((xPos+width),yPos),
			    new Vec2((xPos)+width + radius,((yPos)+height/2)),
			    new Vec2(((xPos)+width),(yPos)+height),
			    new Vec2((xPos),(yPos)+height)};
		Double[] fxCoords = new Double[jCoords.length*2];
		int k = 0;
		for(int i = 0; i < jCoords.length; i++)
		{
			fxCoords[k] =(double)Utility.toPixelPosX((jCoords[i].x));
			fxCoords[k+1] = (double)Utility.toPixelPosY(jCoords[i].y);
			k+=2;
		}
		polygon.getPoints().addAll(fxCoords);
		polygon.setFill(Color.DARKBLUE);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(xPos, yPos);
		bd.fixedRotation = true;
		
		PolygonShape shape = new PolygonShape();
		
		shape.set(jCoords, 5);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 0.6f;
		fd.friction = 0.3f;
		fd.restitution = 0.8f;
		
		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		polygon.setUserData(body);
		
		return polygon;
	}

}
