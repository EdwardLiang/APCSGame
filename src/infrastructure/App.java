package infrastructure;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class App extends Application {
	public static GameWorld game;
	public static Camera camera;
	public static Group root;
	public static Scene scene;
	public static Stage pS;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		pS = primaryStage;
		primaryStage.setTitle("Dreamscape");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);

		root = new Group();
		scene = new Scene(root, Util.WIDTH, Util.HEIGHT);

		game = new GameWorld();

		camera = new Camera();
		KeyManager keyManager = new KeyManager();
		Thread key = new Thread(keyManager.keyThread);
		Thread cam = new Thread(camera);
		key.start();
		cam.start();

		game.addMap(new GameMap(new BackGround("maps/castle.jpg")));
		game.changeMap(game.getMaps().get(1));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
				System.exit(0);
			}
		});

		scene.setOnKeyPressed(keyManager.keyPress);
		scene.setOnKeyReleased(keyManager.keyRelease);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
