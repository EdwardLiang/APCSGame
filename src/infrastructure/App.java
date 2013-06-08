package infrastructure;

import guiobject.Camera;
import guiobject.EdwardPopup;
import guiobject.PopupText;
import guiobject.ShapeMaker;
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
import mousemanagers.DevMouse;

import org.jbox2d.collision.shapes.PolygonShape;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import keymanagers.CreationKeys;
import keymanagers.DefaultKeys;
import keymanagers.DevModeKeys;
import keymanagers.FlyingKeys;
import keymanagers.InertialKeys;
import keymanagers.KeyManager;
import keymanagers.MouseManager;
import keymanagers.PixelEscapeKeys;

import org.jbox2d.common.Vec2;

import utils.Parse;
import utils.Util;

import entities.BouncyBall;
import entities.Creature;
import entities.Entity;
import entities.Floor;
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
	public static float tC;

	public static final List<String> musicList = Arrays.asList(new String[] {
			"src/audio/ZombieTheme.mp3", "src/audio/Melancholy.mp3",
			"src/audio/Mountain.m4a", "src/audio/Mysterious.mp3" });
	public static final List<String> levelList = Arrays.asList(new String[] {
			"src/levels/menu.txt", "src/levels/1-1.txt", "src/levels/1-2.txt",
			"src/levels/1-3.txt", "src/levels/1-4.txt", "src/levels/1-7.txt",
			"src/levels/2-2.txt" });

	public static void main(String[] args) throws IOException {
		launch(args);
	}
	
	public static synchronized void setTC(float tC){
		App.game.getCurrentMap().killTime();
		App.game.getCurrentMap().newTime();
		App.tC = tC;
		App.game.getCurrentMap().startTime();
	}
	public static synchronized float getTC(){
		return tC;
	}

	@Override
	public void start(final Stage primaryStage) throws IOException {
		pS = primaryStage;
		primaryStage.setTitle("Dreamscape");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);
		tC = 1.0f /60.0f;

		root = new Group();
		scene = new Scene(root, Util.WIDTH, Util.HEIGHT);

		game = new GameWorld();

		scene.setCursor(Cursor.CROSSHAIR);
		camera = new Camera();
		shaker = new ShapeMaker();
		MouseManager mouse = new DevMouse();
		DevModeKeys keyManager = new DevModeKeys();
		key = new Thread(keyManager.keyThread);
		cam = new Thread(camera);
		key.start();
		cam.start();

		// ball.setVisible(true);

		// game.addMap(new GameMap(new BackGround("maps/1-1.jpg"), 1350, 280,
		// 36,
		// 49, 30.0f));
		game.addMap(GameMap.parse(Parse.readFromFile(getLevelForIndex(1)),
				getLevelForIndex(1)));
		// game.addMap(new GameMap(new BackGround("maps/1-2.jpg"), 20, 280, 36,
		// 49, 30.0f));
		game.addMap(GameMap.parse(Parse.readFromFile(getLevelForIndex(2)),
				getLevelForIndex(2)));
		// game.addMap(new GameMap(new BackGround("maps/1-3.jpg"), 1200, 270,
		// 36,
		// 49, 30.0f));
		game.addMap(GameMap.parse(Parse.readFromFile(getLevelForIndex(3)),
				getLevelForIndex(3)));
		// game.addMap(new GameMap(new BackGround("maps/1-4.png"), 900, 100, 36,
		// 49, 0));
		game.addMap(GameMap.parse(Parse.readFromFile(getLevelForIndex(4)),
				getLevelForIndex(4)));
		// game.addMap(new GameMap(new BackGround("maps/1-4.png"), 900, 100, 36,
		// 49, 0));
		game.addMap(GameMap.parse(Parse.readFromFile(getLevelForIndex(5)),
				getLevelForIndex(5)));
		// game.addMap(new GameMap(new BackGround("maps/1-7.jpg"), 1200, 237,
		// 10,
		// 49, 30.0f));
		// game.getMaps().get(5).addCoreElements();
		// for (int a = 0; a < 210; a += 10) {
		// BouncyBall bouncy = new BouncyBall(a, 90, 8, Color.BLUE);
		// bouncy.addToMap(game.getMaps().get(5));
		// }
		// for (int a = 0; a < 311; a += 5) {
		// BouncyBall bouncy = new BouncyBall(30, a, 8, Color.WHITE);
		// bouncy.addToMap(game.getMaps().get(5));
		// }
		// game.addMap(new GameMap(new BackGround("maps/2-2.jpg"), 1150, 100,
		// 36,
		// 49, 30));
		// game.getMaps().get(6).addCoreElements();
		game.addMap(GameMap.parse(Parse.readFromFile(getLevelForIndex(6)),
				getLevelForIndex(6)));
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
		MenuItem reset = new MenuItem("Reset Level");
		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					App.game.getCurrentMap().reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuFile.getItems().addAll(save, reset);

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
				(new EdwardPopup(
						"This is the feature of our game that allows us to dynamically draw and execute player-defined shapes. To use, draw points with"
								+ "your mouse, and press F1 or F2 to create the shape, depending on what kind of shape you want. "))
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
		// App.game.changeMap(App.game.getMaps().get(6));

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

	public static File getLevelForIndex(int index) {
		return (new File(levelList.get(index)));
	}
}
