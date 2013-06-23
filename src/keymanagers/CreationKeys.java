package keymanagers;

import java.io.IOException;

import entities.Player;

import infrastructure.App;
import infrastructure.GameWorld;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CreationKeys extends DefaultKeys {
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			if (t.getCode() == KeyCode.R) {
				GameWorld.world.getCurrentMap().reset();
				buffer.clear();
			}
			if (((Player) GameWorld.world.getPlayer()).getStatus() == Player.Status.DEAD) {
				return;
			}

			buffer.add(t.getCode());
			t.consume();
			try {
				if (t.getCode() == KeyCode.D) {
					if (GameWorld.world.getPlayer() != null) {
						if (((Player) GameWorld.world.getPlayer()).getSide() == Player.Side.LEFT) {
							GameWorld.world.getPlayer().setVisible(false);
							((Player) GameWorld.world.getPlayer())
									.setSide(Player.Side.RIGHT);
							((Player) GameWorld.world.getPlayer()).changeNode();
							GameWorld.world.getPlayer().setVisible(true);
						}
					}
				}
				if (t.getCode() == KeyCode.A) {
					if (GameWorld.world.getPlayer() != null) {
						if (((Player) GameWorld.world.getPlayer()).getSide() == Player.Side.RIGHT) {
							GameWorld.world.getPlayer().setVisible(false);
							((Player) GameWorld.world.getPlayer())
									.setSide(Player.Side.LEFT);
							((Player) GameWorld.world.getPlayer()).changeNode();
							GameWorld.world.getPlayer().setVisible(true);
						}
					}
				}

				if (t.getCode() == KeyCode.N) {
					if (GameWorld.world.getIsAtDoor()) {
						int index = GameWorld.world.getMaps().indexOf(
								GameWorld.world.getCurrentMap());
						if (index + 1 < GameWorld.world.getMaps().size())
							GameWorld.world.changeMap(GameWorld.world.getMaps()
									.get(index + 1));
					}
				} else if (t.getCode() == KeyCode.DIGIT1) {
					App.shaker.generateDynamicEntity();
				} else if (t.getCode() == KeyCode.DIGIT2) {
					App.shaker.generateStaticEntity();
				}
			} catch (Exception e) {
				buffer.clear();
			}

		}
	};
}
