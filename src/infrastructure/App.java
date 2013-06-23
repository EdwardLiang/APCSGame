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

	public static void reverseTime() {
		App.game.getCurrentMap().killTime();
		App.game.getCurrentMap().newReverseTime();
		App.game.getCurrentMap().startTime();
	}

	public static synchronized void setTC(float tC) {
		App.game.getCurrentMap().killTime();
		App.game.getCurrentMap().newTime();
		App.tC = tC;
		App.game.getCurrentMap().startTime();
	}

	public static synchronized float getTC() {
		return tC;
	}

	public static void toggleRTime() {
		if (App.game.getCurrentMap().getTime() instanceof ReverseTime) {
			App.game.getCurrentMap().killTime();
			App.game.getCurrentMap().newTime();
			App.game.getCurrentMap().startTime();
		} else {
			App.game.getCurrentMap().killTime();
			App.game.getCurrentMap().newReverseTime();
			App.game.getCurrentMap().startTime();
		}
		reverse.toggle();
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
		// this.mediaView = createMediaView();

		menuBar = new MenuBar();

		// Menu File
		Menu menuFile = new Menu("File");
		MenuItem save = new MenuItem("Save Map");
		MenuItem reset = new MenuItem("Reset Level");
		MenuItem pause = new MenuItem("Pause Level");
		pause.setOnAction(new EventHandler<ActionEvent>() {
			EdwardPopup pop = new EdwardPopup("Paused. ");
			int i = 0;

			@Override
			public synchronized void handle(ActionEvent arg0) {
				App.game.getCurrentMap().getTime().toggleTime();
				pop.toggle();
				if (i % 2 == 0)
					mediaView.getMediaPlayer().pause();
				else
					mediaView.getMediaPlayer().play();
				i++;
			}
		});
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public synchronized void handle(ActionEvent arg0) {
				try {
					App.game.getCurrentMap().reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuFile.getItems().addAll(save, reset, pause);

		Menu menuEdit = new Menu("Edit");
		MenuItem devMode = new MenuItem("DevMode");
		menuEdit.getItems().add(devMode);
		Menu menuView = new Menu("View");
		MenuItem zoom = new MenuItem("Zoom");
		menuView.getItems().add(zoom);
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
		devMode.setOnAction(new EventHandler<ActionEvent>() {
			EdwardPopup pop = new EdwardPopup(
					"This is the feature of our game that allows us to dynamically draw and execute player-defined shapes. To use, draw points with"
							+ "your mouse, and press 1 or 2 to create the shape, depending on what kind of shape you want. 3 and 4 make shapes that kill the player. ");

			@Override
			public synchronized void handle(ActionEvent event) {
				pop.toggle();

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
		// ((Group) scene.getRoot()).getChildren().add(mediaView);
		primaryStage.setScene(scene);
		primaryStage.show();
		/*
		 * for(int a = 0; a < 11; a++){
		 * game.getMaps().get(a).newNonReversableTime(); }
		 */
		game.changeMap(game.getMaps().get(1));
		// game.getCurrentMap().getPhysics()
		// .setContactListener(new ContactManager());
		for (GameMap a : game.getMaps()) {
			a.getPhysics().setContactListener(new ContactManager());
		}
		// App.game.changeMap(App.game.getMaps().get(12));
		// game.getCurrentMap().addCoreElements();
		// App.game.getMaps().get(7).toggleTime();

		reverse = new TimePopup("Time Reversing...");
		slow = new TimePopup("Time Slow...");
		speed = new TimePopup("Time Sped...");

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
		// if(!urls.hasNext()&&
		// !mediaView.getMediaPlayer().getMedia().getTracks().get(0).getName().equals(musicList.get(musicList.size()-1)))
	}

	public static String readFromFile(String fileName) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(App.class
				.getClassLoader().getResourceAsStream(fileName)));
		// Path path = Paths.get(fileName);
		// List<String> listed = Files.readAllLines(path, ENCODING);
		String result = "";
		String local = "";
		while ((local = in.readLine()) != null) {
			result += local + "\n";
			local = "";
			// System.out.println(result);
		}
		return result;
		// for (String str : listed)
		// result += str + "\n";
		// return result;
	}
	// public static String readFromFile(File file) throws IOException {
	// List<String> listed = Files.readAllLines(file.toPath(), ENCODING);
	// String result = "";
	// for (String str : listed)
	// result += str + "\n";
	// return result;
	// }
}
