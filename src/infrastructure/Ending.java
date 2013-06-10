package infrastructure;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.jbox2d.dynamics.BodyType;
import utils.Util;
import entities.DynamicPathEntity;
import entities.Entity;
import entities.Player;
import entities.Princess;
import guiobject.BackGround;
import guiobject.EdwardPopup;

public class Ending extends Time {
	Timeline timeline;
	GameMap map;
	private int count = 0;
	private Princess pr = new Princess(Util.toPosX(10), Util.toPosY(530));
	private EdwardPopup pop = new EdwardPopup(
			"I guess after all this time, I forgot what it was like to care.");
	private EdwardPopup popup2 = new EdwardPopup(
			"...And I almost hate you for it.");

	EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent t) {
			map.getBack()
					.setViewport(
							new Rectangle2D(0, map.getPHeight() - Util.HEIGHT,
									600, 600));
			count++;
			((Player) App.game.getPlayer()).setStatus(Player.Status.IDLE);
			App.game.getPlayer().node.setLayoutX(10);
			App.game.getPlayer().node.setLayoutY(530);
			pr.setVisible(false);
			pr.node.setLayoutX(500);
			pr.node.setLayoutY(530);
			if (count == 8) {
				map.setBack(new BackGround("maps/2-3.jpg"));
			} else if (count == 9) {
				map.setBack(new BackGround("maps/1-5-1.jpg"));
			} else if (count == 10) {
				map.setBack(new BackGround("maps/1-5.jpg"));
			} else if (count == 11) {
				map.setBack(new BackGround("maps/1-7.jpg"));
			} else if (count == 12) {
				map.setBack(new BackGround("maps/2-2.jpg"));
			} else if (count == 13) {
				map.setBack(new BackGround("maps/2-2.jpg"));
			} else if (count == 14) {
				map.setBack(new BackGround("maps/2-1.jpg"));
			} else if (count == 15) {
				map.setBack(new BackGround("maps/1-6.jpg"));
			} else if (count == 16) {
				map.setBack(new BackGround("maps/1-4.png"));
			} else if (count == 17) {
				map.setBack(new BackGround("maps/1-3.jpg"));
			} else if (count == 18) {
				map.setBack(new BackGround("maps/1-2.jpg"));
			} else if (count == 20) {
				pr.setVisible(false);
				pr.setStatus(Player.Status.WALK);
				pr.setSide(Player.Side.RIGHT);
				pr.changeNode();
				pr.node.setLayoutX(530);
				pr.node.setLayoutY(500);
				pr.setVisible(true);
				pop.toggle();
				popup2.toggle();
			}
			map.getBack().setVisible(true);
			App.game.getPlayer().setVisible(true);
			if ((count > 20)){
				pr.setVisible(false);
				pr.setStatus(Player.Status.CLIMBING);
				pr.changeNode();
				pr.node.setLayoutX(530);
				pr.node.setLayoutY(500);
			}
			pr.setVisible(true);

			if (pop.getIsON()) {
				pop.toggle();
				pop.toggle();
			} else if (popup2.getIsON()) {
				popup2.toggle();
				popup2.toggle();
			}
		};
	};

	public Ending(GameMap world) {
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		Duration duration = Duration.seconds(.25);
		KeyFrame frame = new KeyFrame(duration, ae, null, null);
		timeline.getKeyFrames().add(frame);
		this.map = world;
	}

	public void startTime() {
		timeline.playFromStart();
		pop.toggle();
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
