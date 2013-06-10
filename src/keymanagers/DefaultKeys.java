package keymanagers;

import infrastructure.App;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import entities.Player;

public class DefaultKeys extends KeyManager {
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			if (t.getCode() == KeyCode.R) {
				try {
					App.game.getCurrentMap().reset();
					buffer.clear();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (((Player) App.game.getPlayer()).getStatus() == Player.Status.DEAD) {
				return;
			}

			buffer.add(t.getCode());
			t.consume();
			try {
				if (t.getCode() == KeyCode.SHIFT) {
					App.toggleRTime();
				}
				if (t.getCode() == KeyCode.D) {
					if (App.game.getPlayer() != null) {
						if (((Player) App.game.getPlayer()).getSide() == Player.Side.LEFT) {
							App.game.getPlayer().setVisible(false);
							((Player) App.game.getPlayer())
									.setSide(Player.Side.RIGHT);
							((Player) App.game.getPlayer()).changeNode();
							App.game.getPlayer().setVisible(true);
							((Player) App.game.getPlayer()).setMoving(true);

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
							((Player) App.game.getPlayer()).setMoving(true);

						}
					}
				}

				if (t.getCode() == KeyCode.P) {
					App.game.getCurrentMap().toggleTime();
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
			Body body = App.game.getPlayer().getBody();
			final KeyEvent t = key;
			buffer.remove(t.getCode());
			if (((Player) App.game.getPlayer()).getStatus() == Player.Status.DEAD) {
				App.game.getPlayer().getBody()
						.setLinearVelocity(new Vec2(0, 0));
				return;
			}
			try {
				if (t.getCode() == KeyCode.A && body.getLinearVelocity().x != 0) {
					Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
					((Player) App.game.getPlayer()).setMoving(false);

				}
				if (t.getCode() == KeyCode.D && body.getLinearVelocity().x != 0) {
					Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
					((Player) App.game.getPlayer()).setMoving(false);

				}
				t.consume();
			} catch (Exception e) {

			}
		}

	};

	public Runnable keyThread = new Runnable() {
		@Override
		public void run() {
			while (true) {
				if(((Player) App.game.getPlayer()).getStatus() != Player.Status.DEAD)
				try {
					Body body = App.game.getPlayer().getBody();
					if (buffer.contains(KeyCode.W)
							&& body.getLinearVelocity().y == 0
							&& body.getContactList() != null) {
						Vec2 impulse = new Vec2(0, 5000.0f);
						Vec2 point = body.getWorldPoint(body.getWorldCenter());
						body.applyLinearImpulse(impulse, point);
					}
					if (buffer.contains(KeyCode.A)
							&& buffer.contains(KeyCode.D)) {
						Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
						body.setLinearVelocity(velocity);
					} else if (buffer.contains(KeyCode.A)) {
						Vec2 velocity = new Vec2(-20.0f,
								body.getLinearVelocity().y);
						body.setLinearVelocity(velocity);
					} else if (buffer.contains(KeyCode.D)) {
						Vec2 velocity = new Vec2(20.0f,
								body.getLinearVelocity().y);
						body.setLinearVelocity(velocity);
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						buffer.clear();
					}
				} catch (Exception e) {
					buffer.clear();
				}
			}
		}

	};

}
