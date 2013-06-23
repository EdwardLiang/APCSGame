package entities;

import utils.Util;

import infrastructure.App;
import infrastructure.EntityData;
import infrastructure.PlayerData;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Creature {
	private Status status;
	private Side side;
	private ImageView imageView;
	private boolean moving;
	public static final Image idle = new Image("sprites/tim-idle.gif");
	public static final Image run = new Image("sprites/tim-run.gif");
	public static final Image climb = new Image("sprites/tim-climbing.gif");
	public static final Image down = new Image("sprites/tim-downwards.gif");
	public static final Image upward = new Image("sprites/tim-upward.gif");
	public static final Image dead = new Image("sprites/tim-dead.gif");
	public static final ImageView idlev = new ImageView(idle);
	public static final ImageView runv = new ImageView(run);
	public static final ImageView climbv = new ImageView(climb);
	public static final ImageView downv = new ImageView(down);
	public static final ImageView upwardv = new ImageView(upward);
	public static final ImageView deadv = new ImageView(dead);

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

	public synchronized void updatePic() {
		switch (this.status) {
		case IDLE:
			this.imageView = idlev;
			break;
		case WALK:
			this.imageView = runv;
			break;
		case CLIMBING:
			this.imageView = climbv;
			break;
		case DOWNWARDS:
			this.imageView = downv;
			break;
		case UPWARD:
			this.imageView = upwardv;
			break;
		case DEAD:
			this.imageView = deadv;
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
		imageView = idlev;
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
	}

	@Override
	public synchronized void update(EntityData data) {
		if (((PlayerData) data).getStatus() != ((Player) App.game.getPlayer())
				.getStatus()) {
			((Player) App.game.getPlayer()).setVisible(false);
			((Player) App.game.getPlayer()).setStatus(((PlayerData) data)
					.getStatus());
			((Player) App.game.getPlayer()).changeNode();
			((Player) App.game.getPlayer()).setVisible(true);
		}
		super.update(data);
	}
}
