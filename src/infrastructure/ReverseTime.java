package infrastructure;

import entities.Entity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class ReverseTime extends Time {
	public ReverseTime(GameMap world) {
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		Duration duration = Duration.seconds(1.0 / 60.0);
		KeyFrame frame = new KeyFrame(duration, fs, null, null);
		timeline.getKeyFrames().add(frame);
		this.map = world;
	}

	EventHandler<ActionEvent> fs = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			Frame frame = map.getTimeData().getLastFrame();
			map.getBack().update();
			
			for (Entity a : map.getElements()) {
				if (frame.getData().keySet().contains(a) == false) {
					a.update();
				}
			}
			for (Entity a : frame.getData().keySet()) {
				a.update(frame.getData().get(a));
			}
		}
	};
}
