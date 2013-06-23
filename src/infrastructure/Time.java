package infrastructure;

import java.io.Serializable;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import entities.*;

public class Time implements Serializable {
	Timeline timeline;
	GameMap map;

	EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent t) {
			Frame frame = new Frame();
			map.getPhysics().step(App.getTC(), 8, 3);
			map.getBack().update();

			if (App.game.getPlayer().getPosition().x > App.game.getCurrentMap()
					.getWidth()
					|| App.game.getPlayer().getPosition().y > App.game
							.getCurrentMap().getHeight()
					|| App.game.getPlayer().getPosition().y < 0
					|| App.game.getPlayer().getPosition().x < 0) {
				((Player) App.game.getPlayer()).setVisible(false);
				((Player) App.game.getPlayer()).setStatus(Player.Status.DEAD);
				((Player) App.game.getPlayer()).changeNode();
				((Player) App.game.getPlayer()).setVisible(true);
			}
			for (Entity a : map.getElements()) {
				frame.addEntity(a);
				a.update();
			}
			map.getTimeData().addFrame(frame);
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

	public Time() {

	}

	public void startTime() {
		timeline.playFromStart();
	}

	public void stopTime() {
		timeline.pause();
	}

	public void killTime() {// Is that what I'm doing here?
		timeline.stop();
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
}
