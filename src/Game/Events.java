package Game;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;


public class Events {
	public static final EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>(){
		public void handle(KeyEvent key) {
			final KeyEvent t = key;
					Body body = (Body)App.player.node.getUserData();
					if(t.getCode() == KeyCode.W){
						Vec2 velocity = new Vec2(0,50.0f);
						body.setLinearVelocity(velocity);
					}
					else if(t.getCode() == KeyCode.A){
						Vec2 velocity = new Vec2(-50.0f,0);
						body.setLinearVelocity(velocity);
					}
					else if(t.getCode() == KeyCode.S){
						Vec2 velocity = new Vec2(0,-50.0f);
						body.setLinearVelocity(velocity);
					}
					else if(t.getCode() == KeyCode.D){
						Vec2 velocity = new Vec2(50.0f,0);
						body.setLinearVelocity(velocity);
					}
					else if(t.getCode() == KeyCode.UP){
						App.offsetY+=2;
					}
					else if(t.getCode() == KeyCode.DOWN){
						App.offsetY--;
					}
					else if(t.getCode() == KeyCode.LEFT){
						App.offsetX++;
					}
					else if(t.getCode() == KeyCode.RIGHT){
						App.offsetX--;
					}
					else if(t.getCode() == KeyCode.P){
						App.game.time.toggleTime();
					}
			}
	};

}
