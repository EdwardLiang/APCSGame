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

public class App extends Application {
	public static GameWorld game;
	public static Entity player;
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
		offsetX = 0.0f;
		offsetY = 0.0f;

		primaryStage.setTitle("Prototype World");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);

		game = new GameWorld("file:castle.jpg");

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

		for (Entity a : game.gameElements) {
			root.getChildren().add(a.node);
		}

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
