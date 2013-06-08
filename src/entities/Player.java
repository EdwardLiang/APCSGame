package entities;

import java.io.IOException;

import utils.Util;

import infrastructure.App;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
	public synchronized void setMoving(boolean bool){
		moving = bool;
	}
	public synchronized boolean isMoving(){
		return moving;
	}

	public Side getSide() {
		return side;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public enum Status {
		IDLE, WALK, CLIMBING, DOWNWARDS, UPWARD, DEAD;
	}

	public enum Side {
		RIGHT, LEFT;
	}

	public void updatePic() {
		switch (this.status) {
		case IDLE:
			this.image = new Image("sprites/tim-idle.gif");
			break;
		case WALK:
			this.image = new Image("sprites/tim-run.gif");
			break;
		case CLIMBING:
			this.image = new Image("sprites/tim-climbing.gif");
			break;
		case DOWNWARDS:
			this.image = new Image("sprites/tim-downwards.gif");
			break;
		case UPWARD:
			this.image = new Image("sprites/tim-upward.gif");
			break;
		case DEAD:
			this.image = new Image("sprites/tim-dead.gif");
			break;
		}
	}

	public void kill() throws IOException {
		this.status = Status.DEAD;
		App.game.getCurrentMap().reset();

	}

	protected Node createNode() {
		this.image = new Image("sprites/tim-idle.gif");
		imageView = new ImageView(image);
		return imageView;
	}

	public void changeNode() {
		updatePic();
		imageView = new ImageView(image);
		imageView.setLayoutX(Util.toPPosX(xPos) - Util.toPWidth(width) / 2);
		imageView.setLayoutY(Util.toPPosY(yPos) - Util.toPWidth(height) / 2);
		if (side == Side.LEFT)
			imageView.setScaleX(-1);
		imageView.setUserData(getBody());
		this.node = imageView;
	}
}
