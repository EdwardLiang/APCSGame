package keymanagers;

import java.io.IOException;

import infrastructure.App;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import entities.Player;

public class InertialKeys extends DefaultKeys {
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			try {
				final KeyEvent t = key;
				if (t.getCode() == KeyCode.R)
					try {
						App.game.getCurrentMap().reset();
					} catch (IOException e) {
						e.printStackTrace();
					}

				if (((Player) App.game.getPlayer()).getStatus() == Player.Status.DEAD) {
					return;
				}

				buffer.add(t.getCode());
				t.consume();
				if (t.getCode() == KeyCode.P) {
					App.game.getCurrentMap().toggleTime();
				}
				if (t.getCode() == KeyCode.D) {
					if (App.game.getPlayer() != null) {
						if (((Player) App.game.getPlayer()).getSide() == Player.Side.LEFT) {
							App.game.getPlayer().setVisible(false);
							((Player) App.game.getPlayer())
									.setSide(Player.Side.RIGHT);
							((Player) App.game.getPlayer()).changeNode();
							App.game.getPlayer().setVisible(true);
						}
					}
				}
				if (t.getCode() == KeyCode.A) {
					if (App.game.getPlayer() != null) {
						if (((Player) App.game.getPlayer()).getSide() == Player.Side.RIGHT) {
							App.game.getPlayer().setVisible(false);
							((Player) App.game.getPlayer())
									.setSide(Player.Side.LEFT);
							((Player) App.game.getPlayer()).changeNode();
							App.game.getPlayer().setVisible(true);
						}
					}
				}

				if (t.getCode() == KeyCode.N) {
					if (App.game.getIsAtDoor()) {
						int index = App.game.getMaps().indexOf(
								App.game.getCurrentMap());
						if (index + 1 < App.game.getMaps().size())
							App.game.changeMap(App.game.getMaps()
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
			if (((Player) App.game.getPlayer()).getStatus() == Player.Status.DEAD) {
				return;
			}

			t.consume();
			try {
				if (t.getCode() == KeyCode.A || t.getCode() == KeyCode.D) {
					((Player) App.game.getPlayer()).setMoving(false);
				}
			} catch (Exception e) {

			}
		};
	};
	public Runnable keyThread = new Runnable() {
		@Override
		public void run() {
			while (true) {
				if (((Player) App.game.getPlayer()).getStatus() != Player.Status.DEAD)
					try {
						Body body = App.game.getPlayer().getBody();
						if (buffer.contains(KeyCode.W)) {
							Vec2 velocity = new Vec2(
									body.getLinearVelocity().x, 200.0f);
							body.setLinearVelocity(velocity);
						}
						if (buffer.contains(KeyCode.S)) {
							Vec2 velocity = new Vec2(
									body.getLinearVelocity().x, -200.0f);
							body.setLinearVelocity(velocity);
						}
						if (buffer.contains(KeyCode.A)) {
							Vec2 velocity = new Vec2(-200.0f,
									body.getLinearVelocity().y);
							body.setLinearVelocity(velocity);
						}
						if (buffer.contains(KeyCode.D)) {
							Vec2 velocity = new Vec2(200.0f,
									body.getLinearVelocity().y);
							body.setLinearVelocity(velocity);
						}
					} catch (Exception e) {

					}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("KeyManager stopped");
				}
			}
		}

	};

}
