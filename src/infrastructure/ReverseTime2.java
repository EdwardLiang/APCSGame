package infrastructure;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import org.jbox2d.common.Vec2;

import utils.Util;
import entities.Entity;
import entities.Player;

public class ReverseTime2 extends Time {
	public ReverseTime2(GameMap world) {
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
			map.getBack().setViewport(new Rectangle2D(-App.camera.getOffsetX(),map.getPHeight() - Util.HEIGHT - App.camera
					.getOffsetY(),600,600) );
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

			Frame frame = map.getTimeData().getLastFrame();
			for (Entity a : frame.getData().keySet()) {
				if (frame.getData().get(a) instanceof PlayerData) {
					continue;
				}
				a.getBody().setAngularVelocity(0);
				a.getBody().setLinearVelocity(new Vec2(0, 0));
				// a.getBody().getPosition().x = frame.getData().get(a).getX();
				// a.getBody().getPosition().y = frame.getData().get(a).getY();
				a.getBody().setTransform(
						new Vec2(frame.getData().get(a).getX(), frame.getData()
								.get(a).getY()), 0);
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

}
