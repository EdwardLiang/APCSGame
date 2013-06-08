package mousemanagers;

import utils.Util;
import infrastructure.App;
import javafx.scene.input.MouseEvent;
import keymanagers.MouseManager;

public class DevMouse extends MouseManager {
	public void handle(MouseEvent me) {
		if (me.getEventType() == MouseEvent.MOUSE_CLICKED) {
			App.shaker
					.addMarker((float) me.getSceneX(), (float) me.getSceneY());
			System.out.println("Java FX Coords: " + (me.getSceneX() - App.camera.getOffsetX()) + "," + (me.getSceneY() - App.camera.getOffsetY()));
			System.out.println("JBox2D Coords: " + Util.toPosX(me.getSceneX() - App.camera.getOffsetX()) + "," + Util.toPosY(me.getSceneY() - App.camera.getOffsetY()));
			System.out.println(App.game.getCurrentMap().getDoor().node.getLayoutX());
			System.out.println(App.game.getCurrentMap().getDoor().node.getLayoutY());
		}
	}

}
