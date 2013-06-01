package infrastructure;

import java.io.Serializable;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

import org.jbox2d.dynamics.Body;

import entities.Entity;

public class Time implements Serializable {
	Timeline timeline;
	GameMap map;

	final EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent t) {
			map.getPhysics().step(1.0f / 60.0f, 8, 3);
			map.getBack().setLayoutX(App.game.getOffsetX());
			map.getBack().setLayoutY(
					(float) (-map.getPHeight() + Util.HEIGHT + App.game
							.getOffsetY()));

			for (Entity a : map.getElements()) {
				if (a.node instanceof Circle) {
					float xpos = a.getPosition().x + App.game.getOffsetX();
					float ypos = a.getPosition().y + App.game.getOffsetY();
					a.setLayoutX(xpos);
					a.setLayoutY(ypos);
				} else {
					float xpos = (float) (a.getPPosition().x
							+ App.game.getOffsetX() - a.getPWidth() / 2);
					float ypos = (float) (a.getPPosition().y
							+ App.game.getOffsetY() - a.getPHeight() / 2);
					a.setLayoutX(xpos);
					a.setLayoutY(ypos);
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
		this.map = world;
	}

	public void startTime() {
		timeline.playFromStart();
	}

	public void stopTime() {
		timeline.pause();
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
