package Game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Scanner;

public class App extends Application {
	public static GameWorld game;
	public static Group root;
	public static Scene scene;
	public static Stage pS;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final Scanner sc = new Scanner(System.in);
		pS = primaryStage;

		primaryStage.setTitle("Dreamscape");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);

		root = new Group();
		scene = new Scene(root, Utility.WIDTH, Utility.HEIGHT);
		
		game = new GameWorld();
		game.addLevel(new GameLevel("Game/castle.jpg"));
		//game.changeLevel(game.levels.get(1));
		Thread key = new Thread(Events.keyThread);
		Thread r = new Thread(Events.r);
		key.start();
		r.start();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
				System.exit(0);
			}
		});

		scene.setOnKeyPressed(Events.keyPress);
		scene.setOnKeyReleased(Events.keyRelease);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
