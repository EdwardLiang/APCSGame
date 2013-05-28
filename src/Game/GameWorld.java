package Game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class GameWorld {
	public ArrayList<Entity> gameElements;
	public ImageView backGround;
	public World world;
	public Time time;
	public double pWidth;
	public double pHeight;
	public int width;
	public int height;

	public GameWorld(String backLoc) {
		world = new World(new Vec2(0.0f, -30.0f));
		gameElements = new ArrayList<Entity>();
		time = new Time(this);
		Image back = new Image(backLoc);
		backGround = new ImageView(back);
		pWidth = back.getWidth();
		pHeight = back.getHeight();
		width = (int) Utility.toWidth((float)pWidth);
		height = (int) Utility.toHeight((float)pHeight);
		addElements();
	}

	// Use Entity's addToWorld method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public void addEntity(Entity entity) {
		gameElements.add(entity);
	}

	public void addElements() {
		backGround.setLayoutX(App.getOffsetX());
		backGround.setLayoutY(-pHeight + Utility.HEIGHT + App.getOffsetY());

		BouncyBall bouncy = new BouncyBall(45, 90, 8, Color.BLUE);
		bouncy.addToWorld(this);

		Wall left = new Wall(0, height / 2, 1, height);
		Wall right = new Wall(width, height / 2, 1, height);
		Wall top = new Wall(width / 2, height, width, 1);
		Wall bottom = new Wall(width / 2, 0, width, 1);

		Wall platform = new Wall(50, 50, 25, 3);
		Projectile proj = new Projectile(30.f, 25.f, 10.f, 10.f, 5.f);

		left.addToWorld(this);
		right.addToWorld(this);
		top.addToWorld(this);
		bottom.addToWorld(this);

		platform.addToWorld(this);
		proj.addToWorld(this);

	//	((Body) (proj.node.getUserData()))
			//	.setLinearVelocity(new Vec2(100,0));
	}
}
