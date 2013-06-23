package mousemanagers;

import infrastructure.App;
import javafx.scene.input.MouseEvent;
import keymanagers.MouseManager;

public class DevMouse extends MouseManager {
	@Override
	public void handle(MouseEvent me) {
		if (me.getEventType() == MouseEvent.MOUSE_CLICKED) {
			App.shaker
					.addMarker((float) me.getSceneX(), (float) me.getSceneY());
		}
	}

}
