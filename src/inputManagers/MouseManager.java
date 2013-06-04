package inputManagers;

import infrastructure.App;

import java.util.EnumSet;
import java.util.Set;

import org.jbox2d.common.Vec2;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MouseManager implements EventHandler<MouseEvent> {
	@Override
	public void handle(MouseEvent me) {
		if (me.getEventType() == MouseEvent.MOUSE_MOVED) {
		} else if (me.getEventType() == MouseEvent.MOUSE_CLICKED) {
			 System.out.println(new Vec2((float) me.getSceneX(), (float) me
			 .getSceneY()));
		}
	}
}
