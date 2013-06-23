package keymanagers;

import infrastructure.App;
import infrastructure.GameWorld;

import java.io.IOException;
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
							((Player) GameWorld.world.getPlayer())
									.setMoving(true);

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
							((Player) GameWorld.world.getPlayer())
									.setMoving(true);

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
				}
			} catch (Exception e) {

			}
		}
	};
	public EventHandler<KeyEvent> keyRelease = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			Body body = GameWorld.world.getPlayer().getBody();
			final KeyEvent t = key;
			buffer.remove(t.getCode());
			if (((Player) GameWorld.world.getPlayer()).getStatus() == Player.Status.DEAD) {
				GameWorld.world.getPlayer().getBody()
						.setLinearVelocity(new Vec2(0, 0));
				return;
			}
			try {
				if (t.getCode() == KeyCode.A && body.getLinearVelocity().x != 0) {
					Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
					((Player) GameWorld.world.getPlayer()).setMoving(false);

				}
				if (t.getCode() == KeyCode.D && body.getLinearVelocity().x != 0) {
					Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
					((Player) GameWorld.world.getPlayer()).setMoving(false);

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
				if (((Player) GameWorld.world.getPlayer()).getStatus() != Player.Status.DEAD)
					try {
						Body body = GameWorld.world.getPlayer().getBody();
						if (buffer.contains(KeyCode.W)
								&& body.getLinearVelocity().y == 0
								&& body.getContactList() != null) {
							Vec2 impulse = new Vec2(0, 5000.0f);
							Vec2 point = body.getWorldPoint(body
									.getWorldCenter());
							body.applyLinearImpulse(impulse, point);
						}
						if (buffer.contains(KeyCode.A)
								&& buffer.contains(KeyCode.D)) {
							Vec2 velocity = new Vec2(0,
									body.getLinearVelocity().y);
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
