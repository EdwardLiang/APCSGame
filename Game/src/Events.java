import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;


public class Events {
	public static final EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>(){
		public void handle(KeyEvent t) {
			Body body = (Body)App.player.node.getUserData();
			if(t.getCode() == KeyCode.W){
				Vec2 impulse = new Vec2(0,50.0f);
				Vec2 point = body.getWorldPoint(body.getWorldCenter());
				body.applyLinearImpulse(impulse, point);
			}
			if(t.getCode() == KeyCode.A){
				Vec2 force = new Vec2(-50.0f,0);
				Vec2 point = body.getWorldPoint(body.getWorldCenter());
				body.applyForce(force, point);
			}
			if(t.getCode() == KeyCode.S){
				Vec2 force = new Vec2(0,-50.0f);
				Vec2 point = body.getWorldPoint(body.getWorldCenter());
				body.applyForce(force, point);
			}
			if(t.getCode() == KeyCode.D){
				Vec2 force = new Vec2(50.0f,0);
				Vec2 point = body.getWorldPoint(body.getWorldCenter());
				body.applyForce(force, point);
			}
			if(t.getCode() == KeyCode.UP){
				App.offsetY++;
			}
			if(t.getCode() == KeyCode.DOWN){
				App.offsetY--;
			}
			if(t.getCode() == KeyCode.LEFT){
				App.offsetX++;
			}
			if(t.getCode() == KeyCode.RIGHT){
				App.offsetX--;
			}
			if(t.getCode() == KeyCode.P){
				App.game.time.toggleTime();
			}
		}
	};

}
