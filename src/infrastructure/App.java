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
import entities.PopupText;

public class App extends Application {
	public static GameWorld game;
	public static Camera camera;
	public static ShapeMaker shaker;
	public static Group root;
	public static Scene scene;
	public static Stage pS;
	public static MenuBar menuBar;
	protected static final List<String> musicList =  Arrays
			.asList(new String[] { "src/audio/ZombieTheme.mp3","src/audio/Melancholy.mp3",
					"src/audio/Mountain.m4a","src/audio/Mysterious.mp3" });
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
		BouncyBall ball = new BouncyBall(30, 90, 8, Color.RED);
		ball.addToMap(game.getCurrentMap());
		ball.setVisible(true);
		Creature creature = new Creature(30, 30);
		creature.addToMap(game.getCurrentMap());
		creature.setVisible(true);

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

		game.addMap(new GameMap(new BackGround("maps/castle.jpg"), 20, 13, 20,
				20, 30.0f));
		try {
			game.addMap(GameMap.parse(Parse.readFromFile("savefile.txt"),
					"savefile.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		MediaView mediaView = createMediaView();
		((Group) scene.getRoot()).getChildren().add(mediaView);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	public MediaView createMediaView(){
	    MediaView mediaView = new MediaView();
	    initMediaPlayer(mediaView, musicList.iterator());
	    return mediaView;
	}

	private void initMediaPlayer(
	          final MediaView mediaView, 
	          final Iterator<String> urls
	){
	    if (urls.hasNext()){
	        MediaPlayer mediaPlayer = new MediaPlayer(new Media((new File(urls.next()).toURI().toString())));
	        mediaPlayer.setAutoPlay(true);
	        mediaPlayer.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                initMediaPlayer(mediaView, urls);
	            }
	        });
	        mediaView.setMediaPlayer(mediaPlayer);
	    } 
	}
}
