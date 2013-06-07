package infrastructure;

import inputManagers.DefaultKeys;
import inputManagers.CreationKeys;
import inputManagers.DevModeKeys;
import inputManagers.DevMouse;
import inputManagers.FlyingKeys;
import inputManagers.InertialKeys;
import inputManagers.KeyManager;
import inputManagers.MouseManager;
import inputManagers.PixelEscapeKeys;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.Popup;
import javafx.scene.text.Text;
import org.jbox2d.collision.shapes.PolygonShape;

import java.io.File;
import java.io.IOException;

import org.jbox2d.common.Vec2;

import entities.BouncyBall;
import entities.Creature;
import entities.EdwardPopup;
import entities.Entity;
import entities.PopupText;

public class App extends Application {
	public static GameWorld game;
	public static Camera camera;
	public static ShapeMaker shaker;
	public static Group root;
	public static Scene scene;
	public static Stage pS;
	public static MenuBar menuBar;
	private static final String MEDIA_URL = "src/audio/ZombieTheme.mp3";

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws IOException {
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

		// ball.setVisible(true);
		game.addMap(GameMap.parse(Parse.readFromFile("savefile.txt"),
				"savefile.txt"));

		game.addMap(new GameMap(new BackGround("maps/1-1.jpg"), 20, 13, 20, 20,
				30.0f));
		// game.changeMap(game.getMaps().get(1));

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

		menuBar = new MenuBar();

		// Menu File
		Menu menuFile = new Menu("File");
		MenuItem save = new MenuItem("Save Map");
		menuFile.getItems().add(save);

		Menu menuEdit = new Menu("Edit");
		MenuItem devMode = new MenuItem("DevMode");
		menuEdit.getItems().add(devMode);
		Menu menuView = new Menu("View");
		MenuItem zoom = new MenuItem("Zoom");
		menuView.getItems().add(zoom);
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
		devMode.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public synchronized void handle(ActionEvent event) {
				(new EdwardPopup("PLEASE TELL ME HOW TO REVERSE DEVMODE!1!!1!"))
						.toggle();
			}

		});
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public synchronized void handle(ActionEvent event) {
				try {
					Parse.writeToFile(App.game.getCurrentMap().toString(),
							"savefile.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		((Group) scene.getRoot()).getChildren().addAll(menuBar);
		// audio stuff
		String otherSong = null;// list of songs to be implemented
		File file = new File(MEDIA_URL);

		Media media = new Media(file.toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		MediaView mediaView = new MediaView(mediaPlayer);
		((Group) scene.getRoot()).getChildren().add(mediaView);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
