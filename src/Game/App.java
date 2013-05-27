package Game;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

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
		
		primaryStage.setTitle("Prototype World");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);
		
		game = new GameWorld();
		BouncyBall bouncy = new BouncyBall(45, 90, 8, Color.BLUE);
		bouncy.addToWorld(game);
		
		//left
		Wall wall = new Wall(0,50,1,100);
		//right
		Wall wall2 = new Wall(100,50,1,100);
		//top
		Wall wall3 = new Wall(50,100,100,1);
		//bottom
		Wall wall4 = new Wall(50,0,100,1);
		Wall wall5 = new Wall(50,50,25,3);
		Projectile proj = new Projectile(25,25,10,1);
		player = new Creature(30,80);
		
		player.addToWorld(game);
		proj.addToWorld(game);
		wall.addToWorld(game);
		wall2.addToWorld(game);
		wall3.addToWorld(game);
		wall4.addToWorld(game);
		wall5.addToWorld(game);
		

		((Body)(proj.node.getUserData())).setLinearVelocity(new Vec2(-100, 100));
		
		
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
		scene.setOnKeyReleased(Events.keyPress);
		root.getChildren().add(btn);
		
		for(Entity a: game.gameElements){
			root.getChildren().add(a.node);
		}
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
