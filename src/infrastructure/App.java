package infrastructure;

import inputManagers.DefaultKeys;
import inputManagers.DevModeKeys;
import inputManagers.DevMouse;
import inputManagers.MouseManager;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jbox2d.collision.shapes.PolygonShape;

import java.io.IOException;

import org.jbox2d.common.Vec2;

import entities.BouncyBall;
import entities.Creature;

public class App extends Application {
	public static GameWorld game;
	public static Camera camera;
	public static ShapeMaker shaker;
	public static Group root;
	public static Scene scene;
	public static Stage pS;

	public static void main(String[] args) throws IOException {
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

		scene.setCursor(Cursor.CROSSHAIR);
		camera = new Camera();
		shaker = new ShapeMaker();
		DevModeKeys keyManager = new DevModeKeys();
		MouseManager mouse = new DevMouse();
		Thread key = new Thread(keyManager.keyThread);
		Thread cam = new Thread(camera);
		key.start();
		cam.start();
		
//		ball.setVisible(true);

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
		scene.setOnMouseClicked(mouse);
		scene.setOnMouseMoved(mouse);

        MenuBar menuBar = new MenuBar();
 
        // --- Menu File
        Menu menuFile = new Menu("File");
        //menuFile.setOnAction(EventHandler<ActionEvent> )
        Menu menuEdit = new Menu("Edit");
        MenuItem devMode = new MenuItem("DevMode");
        menuEdit.getItems().add(devMode);
        Menu menuView = new Menu("View");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

		((Group) scene.getRoot()).getChildren().addAll(menuBar);

		primaryStage.setScene(scene);
		primaryStage.show();
		
		BouncyBall ball = new BouncyBall(30, 90, 8, Color.RED);
		//Creature ball = new Creature(30,90);
		ball.addToMap(game.getCurrentMap());
		ball.setVisible(true);
	}
}
