package entities;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Util;

public class Princess extends Player{
	private Status status;
	private Side side;
	private ImageView imageView;
	private boolean moving;
	public static final Image idle = new Image("sprites/princessidle.gif");
	public static final ImageView idlev = new ImageView(idle);
	
	public static final Image run =  new Image("sprites/princehug.gif");
	public static final ImageView runv =   new ImageView(run);

	public static final Image climb =  new Image("sprites/princehugstill.gif");
	public static final ImageView climbv =   new ImageView(climb);

	public Princess(float posX, float posY) {
		super(posX, posY);
		this.width = Util.toWidth(61);
		this.height = Util.toHeight(70);
		this.status = Status.IDLE;
		this.side = Side.LEFT;
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void setMoving(boolean bool) {
		moving = bool;
	}

	@Override
	public synchronized boolean isMoving() {
		return moving;
	}

	@Override
	public synchronized void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public synchronized void setSide(Side side) {
		this.side = side;
	}
	
	@Override
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

	@Override
	protected synchronized Node createNode() {
		imageView = idlev;
		imageView.setPreserveRatio(true);
		imageView.setCache(true);
		imageView.setScaleX(-1);
		return imageView;
	}

	@Override
	public synchronized void changeNode() {
		updatePic();
		imageView.setLayoutX(Util.toPPosX(xPos) - Util.toPWidth(width) / 2);
		imageView.setLayoutY(Util.toPPosY(yPos) - Util.toPWidth(height) / 2);
		imageView.setPreserveRatio(true);
		imageView.setCache(true);
		if (side == Side.LEFT && imageView.getScaleX() != -1)
			imageView.setScaleX(-1);
		if(side == Side.RIGHT && imageView.getScaleX() != 1){
			imageView.setScaleX(1);
		}
		imageView.setUserData(getBody());
		this.node = imageView;
	}

}
