package Game;

import javafx.scene.Node;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Projectile extends Entity {
	public Projectile(float posX, float posY,float width, float height){
		xPos = posX;
		yPos = posY;
		this.width = width;
		this.height = height;
	}

	public Node create() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[]{
		    0.0, 0.0,
		    20.0, 10.0,
		    10.0, 20.0 });
		
		polygon.setFill(Color.RED);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(xPos, yPos);
		bd.fixedRotation = true;
		polygon.setLayoutX(Utility.toPixelPosX(xPos) - Utility.toPixelWidth(width)/2);
		polygon.setLayoutY(Utility.toPixelPosY(yPos) -  Utility.toPixelWidth(height)/2);
		
		//CircleShape cs = new CircleShape();
		//cs.m_radius = 8*0.1f;
						
		PolygonShape ps = new PolygonShape();
		ps.m_count = 3;
		ps.m_vertices[0] = new Vec2(0,0);
		ps.m_vertices[1] = new Vec2(Utility.toWidth(20), Utility.toHeight(10));
		ps.m_vertices[2] = new Vec2(Utility.toWidth(10), Utility.toHeight(20));
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.6f;
		fd.friction = 0.3f;
		fd.restitution = 0.8f;
		
		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		polygon.setUserData(body);
		
		return polygon;
	}

}
