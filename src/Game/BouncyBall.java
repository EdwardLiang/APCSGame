package Game;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BouncyBall extends Entity {
	private int radius;
	private Color color;

	public BouncyBall(float posX, float posY, int radius, Color color) {
		xPos = posX;
		yPos = posY;
		this.radius = radius;
		this.color = color;
	}
	
	public static BouncyBall parse(String[] frag){
		return new BouncyBall(Float.parseFloat(frag[1]), Float.parseFloat(frag[2]), Integer.parseInt(frag[3]), Color.web(frag[4]));
	}

	@Override
	public Node create() {
		Circle ball = new Circle();
		ball.setRadius(radius);
		ball.setFill(color);

		ball.setLayoutX(Utility.toPixelPosX(xPos));
		ball.setLayoutY(Utility.toPixelPosY(yPos));
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(xPos, yPos);
		bd.fixedRotation = true;

		CircleShape cs = new CircleShape();
		cs.m_radius = Utility.toWidth(radius);

		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.6f;
		fd.friction = 0.3f;
		fd.restitution = 0f;

		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		ball.setUserData(body);
		return ball;
	}
	@Override
	public String toString(){
		return super.toString() + Utility.delim + radius + Utility.delim + color.toString();
	}
}
