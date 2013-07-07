package entities;

import org.jbox2d.common.Vec2;

import utils.Util;

import infrastructure.App;
import infrastructure.EntityData;
import infrastructure.Frame;
import infrastructure.GameWorld;
import infrastructure.ImageLoader;
import infrastructure.PlayerData;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Creature {
	private Status status;
	private Side side;
	private ImageView imageView;
	private boolean moving;
	private static final float speed = 20.0f;

	public Player(float posX, float posY) {
		super(posX, posY);
		this.width = Util.toWidth(61);
		this.height = Util.toHeight(70);
		this.status = Status.IDLE;
		this.side = Side.RIGHT;
		// TODO Auto-generated constructor stub
	}

	public synchronized void setMoving(boolean bool) {
		moving = bool;
	}

	public synchronized void moveRight() {
		this.side = Side.RIGHT;
		this.status = Status.WALK;
		Vec2 velocity = new Vec2(speed, body.getLinearVelocity().y);
		body.setLinearVelocity(velocity);
	}

	public synchronized void moveLeft() {
		this.side = Side.LEFT;
		this.status = Status.WALK;
		Vec2 velocity = new Vec2(-speed, body.getLinearVelocity().y);
		body.setLinearVelocity(velocity);
	}

	public synchronized void moveUp() {
		this.status = Status.UPWARD;
		Vec2 velocity = new Vec2(body.getLinearVelocity().x, speed);
		body.setLinearVelocity(velocity);
	}

	public synchronized void moveDown() {
		this.status = Status.DOWNWARD;
		Vec2 velocity = new Vec2(body.getLinearVelocity().x, -speed);
		body.setLinearVelocity(velocity);
	}

	public synchronized void jump() {
		this.status = Status.UPWARD;
		Vec2 impulse = new Vec2(0, speed * 10);
		body.applyLinearImpulse(impulse, body.getWorldCenter());
	}

	public synchronized boolean isMoving() {
		return moving;
	}

	public synchronized Side getSide() {
		return side;
	}

	public synchronized Status getStatus() {
		return status;
	}

	public synchronized void setStatus(Status status) {
		this.status = status;
	}

	public synchronized void setSide(Side side) {
		this.side = side;
	}

	public enum Status {
		IDLE, WALK, CLIMB, DOWNWARD, UPWARD, DEAD;
	}

	public enum Side {
		RIGHT, LEFT;
	}

	private synchronized ImageView getNewPic() {
		switch (this.status) {
		case IDLE:
			return ImageLoader.idlev;
		case WALK:
			return ImageLoader.runv;
		case CLIMB:
			return ImageLoader.climbv;
		case DOWNWARD:
			return ImageLoader.downv;
		case UPWARD:
			return ImageLoader.upwardv;
		case DEAD:
			return ImageLoader.deadv;
		}
		return null;
	}

	public synchronized void kill() {
		this.setVisible(false);
		this.status = Status.DEAD;
		this.setVisible(true);
	}

	@Override
	protected synchronized Node createNode() {
		imageView = ImageLoader.idlev;
		imageView.setPreserveRatio(true);
		imageView.setCache(true);
		return imageView;
	}

	public synchronized void updateSide() {
		if (side == Side.LEFT && imageView.getScaleX() != -1)
			imageView.setScaleX(-1);
		if (side == Side.RIGHT && imageView.getScaleX() != 1) {
			imageView.setScaleX(1);
		}
	}

	public synchronized void updateLocation() {
		imageView.setLayoutX(Util.toPPosX(xPos) - Util.toPWidth(width) / 2);
		imageView.setLayoutY(Util.toPPosY(yPos) - Util.toPWidth(height) / 2);
		imageView.setPreserveRatio(true);
		imageView.setCache(true);
	}

	private synchronized void updatePic() {
		if (imageView != getNewPic()) {
			setVisible(false);
			imageView = getNewPic();
			updateSide();
			setVisible(true);
		}
	}

	public synchronized void updateNode() {
		updatePic();
		updateLocation();
		imageView.setUserData(getBody());
		this.node = imageView;
	}

	@Override
	public PlayerData backUp() {
		return new PlayerData(this);
	}

	@Override
	public void update() {
		updateNode();
		super.update();
	}

	@Override
	public synchronized void restore(Frame frame) {
		super.restore(frame);
		EntityData data = frame.getData().get(this);
		if (((PlayerData) data).getStatus() != getStatus()) {
			status = ((PlayerData) data).getStatus();
			updateNode();
		}
	}
}
