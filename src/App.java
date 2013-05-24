import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class App extends Application{
	GameWorld game;
	
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage primaryStage){
		primaryStage.setTitle("Bouncy Ball");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);
		
		game = new GameWorld();
		
		Group root = new Group();
		Scene scene = new Scene(root, Utility.WIDTH, Utility.HEIGHT);
												
		final Button btn = new Button();
		btn.setLayoutX((Utility.WIDTH/2) - 15);
		btn.setLayoutY((Utility.HEIGHT-30));
		btn.setText("Start");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				GameWorld.time.timeline.playFromStart();
				btn.setVisible(false);
			}
		});
			
		scene.setOnKeyPressed(Events.keyPress);
		root.getChildren().add(btn);
		
		for(Entity a: GameWorld.gameElements){
			root.getChildren().add(a.node);
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
