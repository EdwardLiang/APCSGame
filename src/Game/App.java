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

public class App extends Application {
	public static GameWorld game;
	public static Entity player;
	//public static ArrayList<String> levels = new ArrayList<String>();
	private static float offsetX;
	private static float offsetY;

	public static void main(String[] args) {
		launch(args);
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

	public void start(Stage primaryStage) {
		//levels = Utility.addLevelsToAdd(levels);
		offsetX = 0.0f;
		offsetY = 0.0f;

		primaryStage.setTitle("test");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);

		/*try {
			game = Parse.toWorld(Utility.readFromFile(levels.get(0)));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//game = new GameWorld("file:castle.jpg");
		try {
			System.out.println("test2");
			game = GameWorld.parse(Parse.readFromFile("test"));
			System.out.println("test3");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		player = new Creature(30, 80);

		player.addToWorld(game);

		Group root = new Group();
		Scene scene = new Scene(root, Utility.WIDTH, Utility.HEIGHT);

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

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
				System.exit(0);
			}
		});

		scene.setOnKeyPressed(Events.keyPress);
		scene.setOnKeyReleased(Events.keyRelease);
		root.getChildren().add(game.backGround);
		root.getChildren().add(btn);

		BouncyBall bouncy = new BouncyBall(45, 90, 8, Color.BLUE);
		Wall platform = new Wall(50, 50, 25, 3);
		Projectile proj = new Projectile(15.f, 75.f, 2.f, 1.f, 3.f);
		platform.addToWorld(game);
		proj.addToWorld(game);
		bouncy.addToWorld(game);
		((Body) (proj.node.getUserData())).setLinearVelocity(new Vec2(50.0f,
				0.0f));
		
		for (Entity a : game.gameElements) {
			root.getChildren().add(a.node);
		}
		
		try {
			Parse.writeToFile(game.toString(), "test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
