package inputManagers;

import infrastructure.App;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class PixelEscapeKeys extends DefaultKeys {
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.add(t.getCode());
			t.consume();
			if (t.getCode() == KeyCode.P) {
				App.game.getCurrentMap().toggleTime();
			}
		}
	};
	public EventHandler<KeyEvent> keyRelease = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.remove(t.getCode());
		}

	};

	public Runnable keyThread = new Runnable() {
		@Override
		public void run() {
			while (true) {
				Body body = App.game.getPlayer().getBody();
				if (buffer.contains(KeyCode.W)) {
					Vec2 impulse = new Vec2(0, 200.0f);
					Vec2 point = body.getWorldPoint(body.getWorldCenter());
					body.applyLinearImpulse(impulse, point);
				}
				Vec2 velocity = new Vec2(20.0f, body.getLinearVelocity().y);
				body.setLinearVelocity(velocity);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("KeyManager stopped");
				}
			}
		}

	};
}
