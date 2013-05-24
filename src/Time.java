import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import org.jbox2d.dynamics.Body;


public class Time {
	Timeline timeline;
	
	final EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>(){
		public void handle(ActionEvent t){
			GameWorld.world.step(1.0f/60.0f, 8, 3);
			for(Entity a: GameWorld.gameElements){
				Body body = (Body)a.node.getUserData();
				float xpos = Utility.toPixelPosX(body.getPosition().x);
				float ypos = Utility.toPixelPosY(body.getPosition().y);
				a.node.setLayoutX(xpos);
				a.node.setLayoutY(ypos);
			}
		}
	};
	
	public Time(){
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		Duration duration = Duration.seconds(1.0/60.0);
		KeyFrame frame = new KeyFrame(duration,ae,null,null);
		timeline.getKeyFrames().add(frame);
	}

}
