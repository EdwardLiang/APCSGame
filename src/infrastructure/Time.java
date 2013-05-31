package infrastructure;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

import org.jbox2d.dynamics.Body;

import entities.Entity;

public class Time {
	Timeline timeline;
	GameMap world;

	final EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent t) {
			world.world.step(1.0f / 60.0f, 8, 3);
			world.backGround.setLayoutX(App.game.camera.getOffsetX());
			world.backGround.setLayoutY(-world.pHeight + Utility.HEIGHT
					+ App.game.camera.getOffsetY());

			for (Entity a : world.gameElements) {
				Body body = (Body) a.node.getUserData();
				if (a.node instanceof Circle) {
					float xpos = (Utility.toPixelPosX(body.getPosition().x) + App.game.camera
							.getOffsetX());
					float ypos = Utility.toPixelPosY(body.getPosition().y)
							+ App.game.camera.getOffsetY();
					a.node.setLayoutX(xpos);
					a.node.setLayoutY(ypos);
				} else {
					float xpos = Utility.toPixelPosX(body.getPosition().x)
							+ App.game.camera.getOffsetX() - Utility.toPixelWidth(a.width)
							/ 2;
					float ypos = Utility.toPixelPosY(body.getPosition().y)
							+ App.game.camera.getOffsetY() - Utility.toPixelWidth(a.height)
							/ 2;
					a.node.setLayoutX(xpos);
					a.node.setLayoutY(ypos);
				}
			}
		}
	};


	public Time(GameMap world) {
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		Duration duration = Duration.seconds(1.0 / 60.0);
		KeyFrame frame = new KeyFrame(duration, ae, null, null);
		timeline.getKeyFrames().add(frame);
		this.world = world;
	}
	
	public void startLevel(){
		timeline.playFromStart();
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
	public Time(GameMap world, int a) {

	}

}
