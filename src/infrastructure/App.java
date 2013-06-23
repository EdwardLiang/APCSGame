package infrastructure;

import guiobject.Camera;
import guiobject.EdwardPopup;
import guiobject.ShapeMaker;
import guiobject.TimePopup;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import entities.*;
import mousemanagers.DevMouse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import keymanagers.DevModeKeys;
import keymanagers.MouseManager;
import utils.Parse;
import utils.Util;

public class App extends Application {
	public static Camera camera;
	public static ShapeMaker shaker;
	public static Group root;
	public static Scene scene;
	public static Stage pS;
	public static MenuBar menuBar;
	public static Thread key;
	public static Thread cam;
	public static float tC;
	private MediaView mediaView;
	public static EdwardPopup reverse;
	public static EdwardPopup speed;
	public static EdwardPopup slow;

	public static final List<String> musicList = Arrays.asList(new String[] {
			"audio/Melancholy.mp3", "audio/ZombieTheme.mp3",
			"audio/Mysterious.mp3", "audio/Mountain.m4a" });

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws IOException {
		pS = primaryStage;
		primaryStage.setTitle("Dreamscape");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);
		tC = 1.0f / 60.0f;

		root = new Group();
		scene = new Scene(root, Util.WIDTH, Util.HEIGHT);

		scene.setCursor(Cursor.CROSSHAIR);
		camera = new Camera();
		shaker = new ShapeMaker();
		MouseManager mouse = new DevMouse();
		DevModeKeys keyManager = new DevModeKeys();
		key = new Thread(keyManager.keyThread);
		cam = new Thread(camera);
		key.start();
		cam.start();

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

		Menu menuFile = new Menu("File");
		Menu menuEdit = new Menu("Edit");
		Menu menuView = new Menu("View");
		MenuItem save = new MenuItem("Placeholder");
		menuFile.getItems().addAll(save);
		MenuItem hold = new MenuItem("PlaceHolder2");
		menuEdit.getItems().add(hold);
		MenuItem hold2 = new MenuItem("PlaceHolder3");
		menuView.getItems().add(hold2);
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

		((Group) scene.getRoot()).getChildren().addAll(menuBar);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public MediaView createMediaView() {
		MediaView mediaView = new MediaView();
		initMediaPlayer(mediaView, musicList.listIterator());
		return mediaView;
	}

	private void initMediaPlayer(final MediaView mediaView,
			final ListIterator<String> urls) {
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

	public static String readFromFile(String fileName) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(App.class
				.getClassLoader().getResourceAsStream(fileName)));
		String result = "";
		String local = "";
		while ((local = in.readLine()) != null) {
			result += local + "\n";
			local = "";
		}
		return result;
	}
}
