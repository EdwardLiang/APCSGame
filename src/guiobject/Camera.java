package guiobject;

import infrastructure.App;

import java.io.Serializable;

import org.jbox2d.dynamics.Body;

import utils.Util;

import entities.Player;

public class Camera implements Runnable, Serializable {
	public float offsetX;
	public float offsetY;

	public Camera() {
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

	@Override
	public void run() {
		while (true) {
			Body playerData = App.game.getPlayer().getBody();
			if (!App.game.getCurrentMap().isPaused()
					&& App.game.getPlayer() != null && playerData != null) {
				if (Util.toPPosX(playerData.getPosition().x) + getOffsetX() > Util.WIDTH / 2 + 20
						&& !(-getOffsetX() + Util.WIDTH + 1.5f > Util
								.toPWidth(App.game.getCurrentMap().getWidth()))) {
					setOffsetX(getOffsetX() - 1.5f);
				} else if (Util.toPPosX(playerData.getPosition().x)
						+ getOffsetX() < Util.WIDTH / 2 - 20
						&& !(getOffsetX() - 1.5f> 0))
					setOffsetX(getOffsetX() + 1.5f);

				if (Util.toPPosY(playerData.getPosition().y) + getOffsetY() > Util.HEIGHT / 2 + 20
						&& !(getOffsetY() - 1.5f < 0))
					setOffsetY(getOffsetY() - 1.5f);
				else if (Util.toPPosY(playerData.getPosition().y)
						+ getOffsetY() < Util.HEIGHT / 2 - 20
						&& !(getOffsetY() + Util.HEIGHT + 1.5f > Util
								.toPHeight(App.game.getCurrentMap().getHeight())))
					setOffsetY(getOffsetY() + 1.5f);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Camera stopped");
			}

		}
	}
}
