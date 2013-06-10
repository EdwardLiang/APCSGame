package entities;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import utils.Util;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Creature extends Entity {
	private boolean status;
	protected Image image;
	private int col;
	private int count;
	private int offsetX;
	private int offsetY;
	private int pWidth;
	private int pHeight;

	public Creature(float posX, float posY) {
		// Image Credits:
		// http://www.bit-101.com/blog/wp-content/uploads/2011/03/spritesheet.png
		super(posX, posY, Util.toWidth(60), Util.toHeight(60));
	}

	@Override
	protected Node createNode() {
		this.image = new Image("sprites/spritesheet.jpg");
		this.col = 8;
		this.count = 60;
		this.offsetX = 0;
		this.offsetY = 0;
		this.pWidth = 60;
		this.pHeight = 60;
		ImageView imageView = new ImageView(image);
		imageView
				.setViewport(new Rectangle2D(offsetX, offsetY, pWidth, pHeight));
		Animation animation = new SpriteAnimation(imageView,
				Duration.millis(1000), count, col, offsetX, offsetY, pWidth,
				pHeight);
		animation.setCycleCount(Animation.INDEFINITE);
		animation.play();
		return imageView;
	}

	@Override
	protected Shape createShape() {
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(width / 2, height / 2);
		return polygon;
	}

	@Override
	protected BodyDef createBD() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(xPos, yPos);
		bodyDef.fixedRotation = true;
		bodyDef.userData = this.getClass();
		return bodyDef;
	}

	@Override
	protected FixtureDef createFD() {
		FixtureDef fix = new FixtureDef();
		fix.shape = ps;
		fix.density = 1.0f;
		fix.friction = 0.2f;
		fix.restitution = 0f;
		return fix;
	}

	public static Creature parse(String[] frag) {
		return new Creature(Float.parseFloat(frag[1]),
				Float.parseFloat(frag[2]));
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
