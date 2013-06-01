package infrastructure;

import org.jbox2d.dynamics.Body;

public class Camera implements Runnable {
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

	public void run() {
		while (true) {
			Body playerData = (Body) App.game.getPlayer().node.getUserData();
			if (!App.game.getCurrentMap().isPaused()) {
				if (Utility.toPixelPosX(playerData.getPosition().x)
						+ getOffsetX() > Utility.WIDTH / 2 + 20
						&& !(-getOffsetX() + Utility.WIDTH + 1 > Utility
								.toPixelWidth(App.game.getCurrentMap()
										.getWidth()))) {
					setOffsetX(getOffsetX() - 1);
				} else if (Utility.toPixelPosX(playerData.getPosition().x)
						+ getOffsetX() < Utility.WIDTH / 2 - 20
						&& !(getOffsetX() - 1 > 0))
					setOffsetX(getOffsetX() + 1);

				if (Utility.toPixelPosY(playerData.getPosition().y)
						+ getOffsetY() > Utility.HEIGHT / 2 + 20
						&& !(getOffsetY() - 1 < 0))
					setOffsetY(getOffsetY() - 1);
				else if (Utility.toPixelPosY(playerData.getPosition().y)
						+ getOffsetY() < Utility.HEIGHT / 2 - 20
						&& !(getOffsetY() + Utility.HEIGHT + 1 > Utility
								.toPixelHeight(App.game.getCurrentMap()
										.getHeight())))
					setOffsetY(getOffsetY() + 1);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

		}
	}
}
