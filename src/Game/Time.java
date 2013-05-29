package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

import org.jbox2d.dynamics.Body;

public class Time {
	Timeline timeline;
	GameWorld world;

	final EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent t) {
			world.world.step(1.0f / 60.0f, 8, 3);
			world.backGround.setLayoutX(App.getOffsetX());
			world.backGround.setLayoutY(-world.pHeight + Utility.HEIGHT
					+ App.getOffsetY());

			for (Entity a : world.gameElements) {
				Body body = (Body) a.node.getUserData();
				if (a.node instanceof Circle) {
					float xpos = (Utility.toPixelPosX(body.getPosition().x)
							+ App.getOffsetX());
					float ypos = Utility.toPixelPosY(body.getPosition().y)
							+ App.getOffsetY();
					a.node.setLayoutX(xpos);
					a.node.setLayoutY(ypos);
				} else {
					float xpos = Utility.toPixelPosX(body.getPosition().x)
							+ App.getOffsetX() - Utility.toPixelWidth(a.width)
							/ 2;
					float ypos = Utility.toPixelPosY(body.getPosition().y)
							+ App.getOffsetY() - Utility.toPixelWidth(a.height)
							/ 2;
					a.node.setLayoutX(xpos);
					a.node.setLayoutY(ypos);
				}
			}
		}
	};

	final Runnable r = new Runnable() {
		public void run() {
			while (true) {
				Body playerData = (Body) App.player.node.getUserData();
				if (!isPaused()) {
					if (Utility.toPixelPosX(playerData.getPosition().x)
							+ App.getOffsetX() > Utility.WIDTH / 2 + 20
							&& !(-App.getOffsetX() + Utility.WIDTH + 1 > 
							Utility.toPixelWidth(world.width))) {
						App.setOffsetX(App.getOffsetX() - 1);
					} else if (Utility.toPixelPosX(playerData.getPosition().x)
							+ App.getOffsetX() < Utility.WIDTH / 2 - 20
							&& !(App.getOffsetX() - 1 > 0))
						App.setOffsetX(App.getOffsetX() + 1);

					if (Utility.toPixelPosY(playerData.getPosition().y)
							+ App.getOffsetY() > Utility.HEIGHT / 2 + 20
							&& !(App.getOffsetY() - 1 < 0))
						App.setOffsetY(App.getOffsetY() - 1);
					else if (Utility.toPixelPosY(playerData.getPosition().y)
							+ App.getOffsetY() < Utility.HEIGHT / 2 - 20
							&& !(App.getOffsetY() + Utility.HEIGHT + 1 > Utility.toPixelWidth(world.width)))
						App.setOffsetY(App.getOffsetY() + 1);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	};

	public Time(GameWorld world) {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		Duration duration = Duration.seconds(1.0 / 60.0);
		KeyFrame frame = new KeyFrame(duration, ae, null, null);
		timeline.getKeyFrames().add(frame);
		this.world = world;
	}

	public void stopTime() {
		timeline.pause();
	}

	public void startTime() {
		timeline.play();
	}

	public void toggleTime() {
		if (timeline.getCurrentRate() > 0)
			stopTime();
		else
			startTime();
	}

	public void reverseTime() {
		System.out.println("NOT IMPLEMENTED");
	}

	public boolean isPaused() {
		return timeline.getCurrentRate() == 0;
	}

	// For experimental time manipulation
	public Time(GameWorld world, int a) {

	}

}
