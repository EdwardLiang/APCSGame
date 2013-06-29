package guiobject;

import infrastructure.App;
import infrastructure.GameWorld;

import java.io.Serializable;

import org.jbox2d.dynamics.Body;

import utils.Util;

public class Camera implements Serializable {
	private float offsetX;
	private float offsetY;
	public static Camera camera = new Camera();

	private Camera() {
		offsetX = 0.0f;
		offsetY = 0.0f;
	}

	public synchronized void reset() {
		offsetX = 0.0f;
		offsetY = 0.0f;
	}

	public synchronized float getOffsetX() {
		return offsetX;
	}

	public synchronized void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public synchronized float getOffsetY() {
		return offsetY;
	}

	public synchronized void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public synchronized void update() {
		Body playerData = GameWorld.world.getPlayer().getBody();
		if (GameWorld.world.getPlayer() != null && playerData != null) {
			offsetX = Util.WIDTH / 2 - Util.toPPosX(playerData.getPosition().x);
			offsetY = Util.HEIGHT / 2
					- Util.toPPosY(playerData.getPosition().y);
		}
		boundCheck();
	}

	private synchronized void boundCheck() {
		boundXCheck();
		boundYCheck();
	}

	private synchronized void boundXCheck() {
		if (offsetX > Util.WIDTH
				- Util.toPWidth(GameWorld.world.getCurrentMap().getWidth()))
			offsetX = Util.WIDTH
					- Util.toPWidth(GameWorld.world.getCurrentMap().getWidth());
		else if (offsetX > 0) {
			offsetX = 0;
		}
	}

	private synchronized void boundYCheck() {
		if (offsetY < 0) {
			offsetY = 0;
		} else if (offsetY > Util.toPHeight(GameWorld.world.getCurrentMap()
				.getHeight()) - Util.HEIGHT) {
			offsetY = Util.toPHeight(GameWorld.world.getCurrentMap()
					.getHeight()) - Util.HEIGHT;
		}
	}
}
