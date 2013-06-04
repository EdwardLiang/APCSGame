package infrastructure;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jbox2d.collision.shapes.PolygonShape;

import java.io.IOException;

import org.jbox2d.common.Vec2;

import keymanagers.DefaultKeys;

public class App extends Application {
	public static GameWorld game;
	public static Camera camera;
	public static Group root;
	public static Scene scene;
	public static Stage pS;

	public static void main(String[] args) throws IOException {
		for(Vec2 a: ((PolygonShape)PathUtil.readToShape("castlefloor")).m_vertices){
			System.out.println(a);
		}
		//for(Vec2 a: PathUtil.readInPPoints("castlefloor"))
		//	System.out.println(a);
		//launch(args);
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
		DefaultKeys keyManager = new DefaultKeys();
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
