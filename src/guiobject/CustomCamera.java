package guiobject;

import infrastructure.App;

import java.io.Serializable;

import org.jbox2d.dynamics.Body;

import utils.Util;

import entities.Player;

public class CustomCamera extends Camera {
	public float offsetX;
	public float offsetY;

	public CustomCamera() {
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
			if (!App.game.getCurrentMap().isPaused()) {
				if (Util.toPPosX(playerData.getPosition().x) + getOffsetX() > Util.WIDTH / 2 + 20
						&& !(-getOffsetX() + Util.WIDTH + 2 > Util
								.toPWidth(App.game.getCurrentMap().getWidth()))) {
					setOffsetX(getOffsetX() - 2);
				} else if (Util.toPPosX(playerData.getPosition().x)
						+ getOffsetX() < Util.WIDTH / 2 - 20
						&& !(getOffsetX() - 2 > 0))
					setOffsetX(getOffsetX() + 2);

				if (Util.toPPosY(playerData.getPosition().y) + getOffsetY() > Util.HEIGHT / 2 + 20
						&& !(getOffsetY() - 2 < 0))
					setOffsetY(getOffsetY() - 2);
				else if (Util.toPPosY(playerData.getPosition().y)
						+ getOffsetY() < Util.HEIGHT / 2 - 20
						&& !(getOffsetY() + Util.HEIGHT + 2 > Util
								.toPHeight(App.game.getCurrentMap().getHeight())))
					setOffsetY(getOffsetY() + 2);
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
			}

		}
	}
}
