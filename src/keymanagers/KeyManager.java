package keymanagers;

import infrastructure.App;
import infrastructure.GameWorld;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import org.jbox2d.common.Vec2;
import entities.Player;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class KeyManager {
	public Set<KeyCode> buffer = EnumSet.noneOf(KeyCode.class);
	protected Player p = (Player) GameWorld.world.getPlayer();
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			if (t.getCode() == KeyCode.R) {
				GameWorld.world.getCurrentMap().reset();
				buffer.clear();
			}
			if (((Player) GameWorld.world.getPlayer()).getStatus() == Player.Status.DEAD) {
				GameWorld.world.getPlayer().getBody()
						.setLinearVelocity(new Vec2(0, 0));
				return;
			}

			buffer.add(t.getCode());
			t.consume();
			try {
				if (t.getCode() == KeyCode.N) {
					if (GameWorld.world.getIsAtDoor()) {
						int index = GameWorld.world.getMaps().indexOf(
								GameWorld.world.getCurrentMap());
						if (index + 1 < GameWorld.world.getMaps().size())
							GameWorld.world.changeMap(GameWorld.world.getMaps()
									.get(index + 1));
					}
				}
			} catch (Exception e) {

			}
		}
	};
	public EventHandler<KeyEvent> keyRelease = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.remove(t.getCode());
			if (((Player) GameWorld.world.getPlayer()).getStatus() == Player.Status.DEAD) {
				GameWorld.world.getPlayer().getBody()
						.setLinearVelocity(new Vec2(0, 0));
				return;
			}

			t.consume();
		}
	};

	public Runnable keyThread = new Runnable() {
		@Override
		public void run() {

			// TODO Auto-generated method stub
		}

	};
}
