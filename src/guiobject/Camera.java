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
		if(App.cam.isAlive())
			App.cam.interrupt();
		offsetX = 0.0f;
		offsetY = 0.0f;
		App.cam = new Thread(this);
		App.cam.start();
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
			try {
				Body playerData = App.game.getPlayer().getBody();
				if (!App.game.getCurrentMap().isPaused()) {
					if (Util.toPPosX(playerData.getPosition().x) + getOffsetX() > Util.WIDTH / 2 + 20
							&& !(-getOffsetX() + Util.WIDTH + 1 > Util
									.toPWidth(App.game.getCurrentMap().getWidth()))) {
						setOffsetX(getOffsetX() - 1);
					} else if (Util.toPPosX(playerData.getPosition().x)
							+ getOffsetX() < Util.WIDTH / 2 - 20
							&& !(getOffsetX() - 1 > 0))
						setOffsetX(getOffsetX() + 1);

					if (Util.toPPosY(playerData.getPosition().y) + getOffsetY() > Util.HEIGHT / 2 + 20
							&& !(getOffsetY() - 1 < 0))
						setOffsetY(getOffsetY() - 1);
					else if (Util.toPPosY(playerData.getPosition().y)
							+ getOffsetY() < Util.HEIGHT / 2 - 20
							&& !(getOffsetY() + Util.HEIGHT + 1 > Util
									.toPHeight(App.game.getCurrentMap().getHeight())))
						setOffsetY(getOffsetY() + 1);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("Camera stopped");
				}
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				try {
					Thread.sleep(10);
				} catch (InterruptedException k) {
					System.out.println("KeyManager stopped");
				}
			}

		}
	}
}
