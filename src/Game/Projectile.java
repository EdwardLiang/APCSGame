package Game;

import javafx.scene.Node;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

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
//		Rectangle rectangle = new Rectangle();
//		rectangle.setHeight(Utility.toPixelHeight(height));
//		rectangle.setWidth(Utility.toPixelWidth(width));
//		rectangle.setLayoutX(Utility.toPixelPosX(xPos));
//		rectangle.setLayoutY(Utility.toPixelPosY(yPos));
//		
//		rectangle.setFill(Color.RED);
//		
//		Circle circle = new Circle();
//
//		circle.setRadius(radius);
//		circle.setLayoutX(Utility.toPixelPosX(xPos));
//		circle.setLayoutY(Utility.toPixelPosX(yPos + height/2));
//		
//		circle.setFill(Color.RED);
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[]{
		    0.0, 0.0,
		    20.0, 10.0,
		    10.0, 20.0 });
		polygon.setFill(Color.BISQUE);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(xPos, yPos);
		bd.fixedRotation = true;
		
		CircleShape cs = new CircleShape();
		cs.m_radius = radius*0.1f;
		 
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.6f;
		fd.friction = 0.3f;
		fd.restitution = 0.8f;
		
		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		polygon.setUserData(body);
		
		return polygon;
	}

}
