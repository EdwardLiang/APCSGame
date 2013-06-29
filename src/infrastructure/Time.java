package infrastructure;

import java.io.Serializable;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import entities.*;
import guiobject.Camera;

public class Time implements Serializable {
	private Timeline timeline;
	private TimeData data;
	private boolean inReverse;

	EventHandler<ActionEvent> nm = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent t) {
			GameMap map = GameWorld.world.getCurrentMap();
			map.getPhysics().step(1 / 60, 8, 3);
			Camera.camera.update();
			map.update();
			data.backUp(map);
		}
	};

	EventHandler<ActionEvent> rw = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			GameMap map = GameWorld.world.getCurrentMap();
			data.restore(map);
			map.update();
		}
	};

	public Time() {
		normalTimeline();
	}

	public void normalTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		Duration duration = Duration.seconds(1.0 / 60.0);
		KeyFrame frame = new KeyFrame(duration, nm, null, null);
		timeline.getKeyFrames().add(frame);
		this.data = new TimeData();
		inReverse = false;
	}

	public void reverseTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		Duration duration = Duration.seconds(1.0 / 60.0);
		KeyFrame frame = new KeyFrame(duration, rw, null, null);
		timeline.getKeyFrames().add(frame);
		inReverse = true;
	}

	public void startTime() {
		timeline.playFromStart();
	}

	public void stopTime() {
		timeline.pause();
	}

	public void killTime() {
		timeline.stop();
	}

	public void toggleTime() {
		if (timeline.getCurrentRate() > 0)
			stopTime();
		else
			startTime();
	}

	public void reverseTime() {
		killTime();
		reverseTimeline();
		startTime();
	}

	public void forwardTime() {
		killTime();
		normalTimeline();
		startTime();
	}

	public void toggleRTime() {
		if (inReverse)
			forwardTime();
		else
			reverseTime();
	}

	public boolean isPaused() {
		return timeline.getCurrentRate() == 0;
	}
}
