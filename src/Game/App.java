package Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application{
	public static GameWorld game;
	public static Entity player;
	public static float offsetX;
	public static float offsetY;
	
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage primaryStage){
		offsetX = 0.0f;
		offsetY = 0.0f;
		
		primaryStage.setTitle("Bouncy Ball");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);
		
		game = new GameWorld();
		player = new BouncyBall(45, 50, 8, Color.BLUE);
		player.addToWorld(game);
		
		Wall wall = new Wall(0,100,1,100);
		Wall wall2 = new Wall(99,100,1,100);
		Wall wall3 = new Wall(0,100,100,1);
		Wall wall4 = new Wall(0,0,100,1);
		Wall wall5 = new Wall(50,50,50,3);
		Projectile proj = new Projectile(25,25,10,1);
		proj.addToWorld(game);
		wall.addToWorld(game);
		wall2.addToWorld(game);
		wall3.addToWorld(game);
		wall4.addToWorld(game);
		wall5.addToWorld(game);
		
		
		Group root = new Group();
		Scene scene = new Scene(root, Utility.WIDTH, Utility.HEIGHT);
														
		final Button btn = new Button();
		btn.setLayoutX((Utility.WIDTH/2) - 15);
		btn.setLayoutY((Utility.HEIGHT - 30));
		btn.setText("Start");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				game.time.timeline.playFromStart();
				btn.setVisible(false);
			}
		});
			
		scene.setOnKeyPressed(Events.keyPress);
		root.getChildren().add(btn);
		
		for(Entity a: game.gameElements){
			root.getChildren().add(a.node);
		}
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}