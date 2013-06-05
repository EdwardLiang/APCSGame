package inputManagers;

import infrastructure.App;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DevModeKeys extends FlyingKeys {
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.add(t.getCode());
			t.consume();
			if (t.getCode() == KeyCode.P) {
				App.game.getCurrentMap().toggleTime();
			} else if (t.getCode() == KeyCode.DIGIT1) {
				App.shaker.generateDynamicEntity();
			} else if (t.getCode() == KeyCode.DIGIT2) {
				App.shaker.generateStaticEntity();
			}

		}
	};
}
