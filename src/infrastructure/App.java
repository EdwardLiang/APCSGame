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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;

import entities.BouncyBall;
import entities.Creature;
import entities.EdwardPopup;
import entities.Entity;
import entities.Floor;
import entities.PopupText;
import entities.Wall;

public class App extends Application {
	public static GameWorld game;
	public static Camera camera;
	public static ShapeMaker shaker;
	public static Group root;
	public static Scene scene;
	public static Stage pS;
	public static MenuBar menuBar;
	public static Thread key;
	public static Thread cam;

	protected static final List<String> musicList = Arrays.asList(new String[] {
			"src/audio/ZombieTheme.mp3", "src/audio/Melancholy.mp3",
			"src/audio/Mountain.m4a", "src/audio/Mysterious.mp3" });

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
		MouseManager mouse = new DevMouse();
		CreationKeys keyManager = new CreationKeys();
		key = new Thread(keyManager.keyThread);
		cam = new Thread(camera);
		key.start();
		cam.start();

		// ball.setVisible(true);

		// game.addMap(new GameMap(new BackGround("maps/1-1.jpg"), 1350, 280,
		// 36,
		// 49, 30.0f));
		game.addMap(GameMap.parse(Parse.readFromFile("1-1.txt"), "1-1.txt"));
		// game.addMap(new GameMap(new BackGround("maps/1-2.jpg"), 20, 280, 36,
		// 49, 30.0f));
		game.addMap(GameMap.parse(Parse.readFromFile("1-2.txt"), "1-2.txt"));
		// game.addMap(new GameMap(new BackGround("maps/1-3.jpg"), 1200, 270,
		// 36,
		// 49, 30.0f));
		game.addMap(GameMap.parse(Parse.readFromFile("1-3.txt"), "1-3.txt"));
		// game.addMap(new GameMap(new BackGround("maps/1-4.png"), 900, 100, 36,
		// 49, 0));
		game.addMap(GameMap.parse(Parse.readFromFile("1-4.txt"), "1-4.txt"));
		// game.addMap(new GameMap(new BackGround("maps/1-4.png"), 900, 100, 36,
		// 49, 0));
		game.addMap(new GameMap(new BackGround("maps/1-7.jpg"), 1200, 237, 10,
				49, 30.0f));
		game.getMaps().get(5).addCoreElements();
		for (int a = 0; a < 1280; a += 10) {
			BouncyBall bouncy = new BouncyBall(a, 90, 8, Color.BLUE);
			bouncy.addToMap(game.getMaps().get(5));
		}
		for (int a = 0; a < 720; a += 5) {
			BouncyBall bouncy = new BouncyBall(30, a, 8, Color.WHITE);
			bouncy.addToMap(game.getMaps().get(5));
		}

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
		MediaView mediaView = createMediaView();
		((Group) scene.getRoot()).getChildren().add(mediaView);
		primaryStage.setScene(scene);
		primaryStage.show();
		game.changeMap(game.getMaps().get(1));
		game.getCurrentMap().addCoreElements();
		// Floor bottom = new Floor(game.getCurrentMap().getWidth() / 2, 35,
		// game.getCurrentMap().getWidth(), 17);
		// bottom.addToMap(game.getCurrentMap());

	}

	public MediaView createMediaView() {
		MediaView mediaView = new MediaView();
		initMediaPlayer(mediaView, musicList.iterator());
		return mediaView;
	}

	private void initMediaPlayer(final MediaView mediaView,
			final Iterator<String> urls) {
		if (urls.hasNext()) {
			MediaPlayer mediaPlayer = new MediaPlayer(new Media((new File(
					urls.next()).toURI().toString())));
			mediaPlayer.setAutoPlay(true);
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					initMediaPlayer(mediaView, urls);
				}
			});
			mediaView.setMediaPlayer(mediaPlayer);
		}
	}
}
