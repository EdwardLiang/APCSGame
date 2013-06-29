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
		IDLE, WALK, CLIMBING, DOWNWARDS, UPWARD, DEAD;
	}

	public enum Side {
		RIGHT, LEFT;
	}

	private synchronized void updatePic() {
		switch (this.status) {
		case IDLE:
			this.imageView = ImageLoader.idlev;
			break;
		case WALK:
			this.imageView = ImageLoader.runv;
			break;
		case CLIMBING:
			this.imageView = ImageLoader.climbv;
			break;
		case DOWNWARDS:
			this.imageView = ImageLoader.downv;
			break;
		case UPWARD:
			this.imageView = ImageLoader.upwardv;
			break;
		case DEAD:
			this.imageView = ImageLoader.deadv;
			break;
		}
	}

	public synchronized void kill() {
		this.setVisible(false);
		this.status = Status.DEAD;
		this.changeNode();
		this.setVisible(true);
	}

	@Override
	protected synchronized Node createNode() {
		imageView = ImageLoader.idlev;
		imageView.setPreserveRatio(true);
		imageView.setCache(true);
		return imageView;
	}

	public synchronized void changeNode() {
		updatePic();
		imageView.setLayoutX(Util.toPPosX(xPos) - Util.toPWidth(width) / 2);
		imageView.setLayoutY(Util.toPPosY(yPos) - Util.toPWidth(height) / 2);
		imageView.setPreserveRatio(true);
		imageView.setCache(true);
		if (side == Side.LEFT && imageView.getScaleX() != -1)
			imageView.setScaleX(-1);
		if (side == Side.RIGHT && imageView.getScaleX() != 1) {
			imageView.setScaleX(1);
		}
		imageView.setUserData(getBody());
		this.node = imageView;
	}

	@Override
	public PlayerData backUp() {
		return new PlayerData(this);
	}

	@Override
	public void update() {
		if (getStatus() == Status.DEAD) {
			return;
		} else {
			if (getBody().getContactList() != null) {
				if (getBody().getLinearVelocity().x == 0
						&& getStatus() != Player.Status.IDLE
						&& getStatus() != Player.Status.UPWARD && !isMoving()
						&& getBody().getContactList() != null) {
					setVisible(false);
					setStatus(Player.Status.IDLE);
					changeNode();
					setVisible(true);
				} else if (Math.abs(getBody().getLinearVelocity().x) > 0
						&& getStatus() != Player.Status.WALK) {
					setVisible(false);
					setStatus(Player.Status.WALK);
					changeNode();
					setVisible(true);
				}
			} else {
				if (getBody().getLinearVelocity().y > 0
						&& getStatus() != Player.Status.UPWARD) {
					setVisible(false);
					setStatus(Player.Status.UPWARD);
					changeNode();
					setVisible(true);
				} else if (getBody().getLinearVelocity().y < 0
						&& getStatus() != Player.Status.DOWNWARDS) {
					setVisible(false);
					setStatus(Player.Status.DOWNWARDS);
					changeNode();
					setVisible(true);
				} else if (getBody().getLinearVelocity().y != 0) {
					;
				}
			}
		}
		super.update();
	}

	@Override
	public synchronized void restore(Frame frame) {
		super.restore(frame);
		EntityData data = frame.getData().get(this);
		if (((PlayerData) data).getStatus() != getStatus()) {
			((Player) GameWorld.world.getPlayer()).setVisible(false);
			((Player) GameWorld.world.getPlayer())
					.setStatus(((PlayerData) data).getStatus());
			((Player) GameWorld.world.getPlayer()).changeNode();
			((Player) GameWorld.world.getPlayer()).setVisible(true);
		}
	}
}
