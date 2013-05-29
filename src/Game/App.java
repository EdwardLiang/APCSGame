package Game;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class App extends Application {
	public static GameWorld game;
	public static Entity player;
	// public static ArrayList<String> levels = new ArrayList<String>();
	private static float offsetX;
	private static float offsetY;
	public static GameWorld game2;
	public static Group root;
	public static Scene scene;
	public static Stage pS;


	public static void main(String[] args) {
		launch(args);
	}
	
	public static void changeWorld(GameWorld world){
		game.time.timeline.stop();

		game = world;
		player.addToWorld(game);
		offsetX = 0;
		offsetY = 0;
				
		root.getChildren().removeAll(root.getChildren());
		
		final Button btn = new Button();
		btn.setLayoutX((Utility.WIDTH / 2) - 15);
		btn.setLayoutY((Utility.HEIGHT - 30));
		
		btn.setText("Start");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				game.time.timeline.playFromStart();
				Thread t = new Thread(game.time.r);
				Thread key = new Thread(Events.keyThread);
				t.start();
				key.start();
				btn.setVisible(false);
			}
		});
		root.getChildren().add(game.backGround);
		
		root.getChildren().add(btn);
		for (Entity a : game.gameElements) {
			root.getChildren().add(a.node);
		}
		
		pS.setScene(scene);
		pS.show();

	}

	public static synchronized float getOffsetX() {
		return offsetX;
	}

	public static synchronized void setOffsetX(float offsetX) {
		App.offsetX = offsetX;
	}

	public static synchronized float getOffsetY() {
		return offsetY;
	}

	public static synchronized void setOffsetY(float offsetY) {
		App.offsetY = offsetY;
	}

	public static void setPlayer(Entity entity) {
		player = entity;
	}

	public void start(Stage primaryStage) {
		// levels = Utility.addLevelsToAdd(levels);
		final Scanner sc = new Scanner(System.in);
		
		pS = primaryStage;
		
		offsetX = 0.0f;
		offsetY = 0.0f;

		primaryStage.setTitle("test");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);
		
		root = new Group();
		scene = new Scene(root, Utility.WIDTH, Utility.HEIGHT);
		
		System.out.println("Would you like to load a level file?");
		char next = sc.next().charAt(0);
		String level;
		
		if(next == 'y' || next == 'Y'){
			System.out.println("What file would you like to load?");
			level = sc.next();
			try {
				game = GameWorld.parse(Parse.readFromFile(level));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else{
			System.out.println("Generating generic file....");
			game = new GameWorld("file:castle.jpg");
			game2 = new GameWorld("file:finalv2.png");
		}

		
		player = new Creature(30, 80);

		player.addToWorld(game);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent arg0) {
				System.out.println("Would you like to save this level?");
				char choice = sc.next().charAt(0);
				if (choice == 'y' || choice == 'Y') {
					System.out.println("What name would like to save it as?");
					String str = sc.next();
					try {
						Parse.writeToFile(game.toString(), str + ".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					Platform.exit();
					System.exit(0);
				}
				Platform.exit();
				System.exit(0);
			}
		});

		scene.setOnKeyPressed(Events.keyPress);
		scene.setOnKeyReleased(Events.keyRelease);

		/*
		 * BouncyBall bouncy = new BouncyBall(45, 90, 8, Color.BLUE); Wall
		 * platform = new Wall(50, 50, 25, 3); Projectile proj = new
		 * Projectile(15.f, 75.f, 2.f, 1.f, 3.f); platform.addToWorld(game);
		 * proj.addToWorld(game); bouncy.addToWorld(game); ((Body)
		 * (proj.node.getUserData())).setLinearVelocity(new Vec2(50.0f, 0.0f));
		 */
		
		final Button btn = new Button();
		btn.setLayoutX((Utility.WIDTH / 2) - 15);
		btn.setLayoutY((Utility.HEIGHT - 30));
		btn.setText("Start");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				game.time.timeline.playFromStart();
				Thread t = new Thread(game.time.r);
				Thread key = new Thread(Events.keyThread);
				t.start();
				key.start();
				btn.setVisible(false);
			}
		});

		root.getChildren().add(game.backGround);
		root.getChildren().add(btn);

		for (Entity a : game.gameElements) {
			root.getChildren().add(a.node);
		}

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
