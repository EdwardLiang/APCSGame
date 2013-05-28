package Game;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Entity {
	public Wall(float posX, float posY, float width, float height) {
		xPos = posX;
		yPos = posY;
		this.width = width;
		this.height = height;
	}

	public Node create() {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(Utility.toPixelHeight(height));
		rectangle.setWidth(Utility.toPixelWidth(width));
		rectangle.setLayoutX(Utility.toPixelPosX(xPos)
				- Utility.toPixelWidth(width) / 2);
		rectangle.setLayoutY(Utility.toPixelPosY(yPos)
				- Utility.toPixelWidth(height) / 2);

		rectangle.setFill(Color.DARKSLATEGRAY);

		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width / 2, height / 2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 1.0f;
		fd.friction = 0.3f;

		BodyDef bd = new BodyDef();
		bd.position.set(xPos, yPos);
		bd.type = BodyType.STATIC;

		Body body = world.world.createBody(bd);
		body.createFixture(fd);
		rectangle.setUserData(body);
		return rectangle;
	}

}
