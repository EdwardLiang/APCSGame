package inputManagers;

import java.io.IOException;

import infrastructure.App;
import infrastructure.GameMap;
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
			if (t.getCode() == KeyCode.N) {
				if (App.game.getIsAtDoor()) {
					int index = App.game.getMaps().indexOf(
							App.game.getCurrentMap());
					if (index + 1 < App.game.getMaps().size())
						App.game.changeMap(App.game.getMaps().get(index + 1));
				}
			}
			if (t.getCode() == KeyCode.O) {
				System.out.println(App.game.getCurrentMap());
			}
			if (t.getCode() == KeyCode.U) {
				System.out.println(App.game.getCurrentMap());
//				System.out.println(GameMap.parse(App.game.getCurrentMap()
//						.toString(), "asdf"));
			}
			if (t.getCode() == KeyCode.P) {
				App.game.getCurrentMap().toggleTime();
			} else if (t.getCode() == KeyCode.DIGIT1) {
				App.shaker.generateDynamicEntity();
			} else if (t.getCode() == KeyCode.DIGIT2) {
				App.shaker.generateStaticEntity();
			}
			if (t.getCode() == KeyCode.R)
				try {
					App.game.getCurrentMap().reset();
					System.out.println("R Hit");
				} catch (IOException e) {
					e.printStackTrace();
				}

		}
	};
}
