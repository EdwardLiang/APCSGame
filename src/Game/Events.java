package Game;

import java.util.EnumSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

//Multi-Key Event Set use inspiration from http://www.brainoverload.nl/java/167/keypressedeventhandler-with-java-fx-2-0-and-multiple-keys

//KNOWN GLITCH: Multiple Jumps Possible.

public class Events {
	public static final Set<KeyCode> buffer = EnumSet.noneOf(KeyCode.class);
	public static final EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.add(t.getCode());
			t.consume();
			if (t.getCode() == KeyCode.P) {
				App.game.time.toggleTime();
			}
			if(t.getCode() == KeyCode.R){
				App.changeWorld(App.game2);
			}
		}
	};
	public static final EventHandler<KeyEvent> keyRelease = new EventHandler<KeyEvent>() {
		public synchronized void handle(KeyEvent key) {
			Body body = (Body) App.player.node.getUserData();
			final KeyEvent t = key;
			buffer.remove(t.getCode());
			if (t.getCode() == KeyCode.A && body.getLinearVelocity().x != 0) {
				Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
				body.setLinearVelocity(velocity);
			}
			if (t.getCode() == KeyCode.D && body.getLinearVelocity().x != 0) {
				Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
				body.setLinearVelocity(velocity);
			}
			t.consume();
		}

	};

	public static final Runnable keyThread = new Runnable() {
		public void run() {
			Body body = (Body) App.player.node.getUserData();
			while (true) {
				if (buffer.contains(KeyCode.W)
						&& body.getLinearVelocity().y == 0
						&& body.getContactList() != null) {
					Vec2 impulse = new Vec2(0, 200.0f);
					Vec2 point = body.getWorldPoint(body.getWorldCenter());
					body.applyLinearImpulse(impulse, point);
				}
				if (buffer.contains(KeyCode.A) && buffer.contains(KeyCode.D)) {
					Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
				} else if (buffer.contains(KeyCode.A)) {
					Vec2 velocity = new Vec2(-20.0f, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
				} else if (buffer.contains(KeyCode.D)) {
					Vec2 velocity = new Vec2(20.0f, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	};
}
