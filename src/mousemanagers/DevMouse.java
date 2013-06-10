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
		}
	}

}
