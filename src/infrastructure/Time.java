package infrastructure;

import java.io.Serializable;

import utils.Util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

import entities.*;

public class Time implements Serializable {
	Timeline timeline;
	GameMap map;

	final EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent t) {
			map.getPhysics().step(App.getTC(), 8, 3);
			map.getBack().setLayoutX(App.camera.getOffsetX());
			map.getBack().setLayoutY(
					(float) (-map.getPHeight() + Util.HEIGHT + App.camera
							.getOffsetY()));
			map.getDoor().node.setLayoutX(map.getDoor().pCoord().x
					+ App.camera.getOffsetX() - 30);
			map.getDoor().node.setLayoutY(map.getDoor().pCoord().y
					+ App.camera.getOffsetY() - 34);

			if (map.getDoor().node.getLayoutX() < App.game.getPlayer().node
					.getLayoutX()
					&& map.getDoor().node.getLayoutX() + 30 > App.game
							.getPlayer().node.getLayoutX()
					&& map.getDoor().node.getLayoutY() - 15 < App.game
							.getPlayer().node.getLayoutY()
					&& map.getDoor().node.getLayoutY() + 30 > App.game
							.getPlayer().node.getLayoutY())
				App.game.isAtDoor(true);
			else
				App.game.isAtDoor(false);

			for (Entity a : map.getElements()) {
				if (a instanceof Player) {
					if(a.getBody().getContactList() != null){
						if (a.getBody().getLinearVelocity().x == 0
								&& ((Player) a).getStatus() != Player.Status.IDLE
								&& ((Player) a).getStatus() != Player.Status.UPWARD
								&& !((Player) a).isMoving()
								&& a.getBody().getContactList() != null) {
							a.setVisible(false);
							((Player) a).setStatus(Player.Status.IDLE);
							((Player) a).changeNode();
							a.setVisible(true);
						} else if (Math.abs(a.getBody().getLinearVelocity().x) > 0
								&& ((Player) a).getStatus() != Player.Status.WALK) {
							a.setVisible(false);
							((Player) a).setStatus(Player.Status.WALK);
							((Player) a).changeNode();
							a.setVisible(true);
						}
					}
					else{
						if (a.getBody().getLinearVelocity().y > 0
								&& ((Player) a).getStatus() != Player.Status.UPWARD) {
							a.setVisible(false);
							((Player) a).setStatus(Player.Status.UPWARD);
							((Player) a).changeNode();
							a.setVisible(true);
						} else if (a.getBody().getLinearVelocity().y < 0
								&& ((Player) a).getStatus() != Player.Status.DOWNWARDS) {
							a.setVisible(false);
							((Player) a).setStatus(Player.Status.DOWNWARDS);
							((Player) a).changeNode();
							a.setVisible(true);
						} else if (a.getBody().getLinearVelocity().y != 0) {
							;
						}
					}
					/*if (((Player) a).getSide() == Player.Side.RIGHT
							&& a.getBody().getLinearVelocity().x < -.2) {
						a.setVisible(false);
						((Player) a).setSide(Player.Side.LEFT);
						((Player) a).changeNode();
						a.setVisible(true);
					} else if (((Player) a).getSide() == Player.Side.LEFT
							&& a.getBody().getLinearVelocity().x > .2) {
						a.setVisible(false);
						((Player) a).setSide(Player.Side.RIGHT);
						((Player) a).changeNode();
						a.setVisible(true);
					}*/

				}
				if (a.node instanceof Circle) {
					float xpos = a.getPPosition().x + App.camera.getOffsetX();
					float ypos = a.getPPosition().y + App.camera.getOffsetY();
					a.setLayoutX(xpos);
					a.setLayoutY(ypos);
				} else {
					float xpos = (float) (a.getPPosition().x
							+ App.camera.getOffsetX() - a.getPWidth() / 2);
					float ypos = (float) (a.getPPosition().y
							+ App.camera.getOffsetY() - a.getPHeight() / 2);
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

	// For experimental time manipulation
	public Time(GameMap world, int a) {

	}

}
