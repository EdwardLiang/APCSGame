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
	// public static ArrayList<String> levels = new ArrayList<String>();
	// public static GameLevel game2;
	public static Group root;
	public static Scene scene;
	public static Stage pS;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// levels = Utility.addLevelsToAdd(levels);
		final Scanner sc = new Scanner(System.in);

		pS = primaryStage;

		primaryStage.setTitle("Dreamscape");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);

		root = new Group();
		scene = new Scene(root, Utility.WIDTH, Utility.HEIGHT);
		
		game = new GameWorld();
		game.startLevel();
		game.addLevel(new GameLevel("Game/castle.jpg"));
		game.changeLevel(game.levels.get(1));

		/*
		 * System.out.println("Would you like to load a level file?"); char next
		 * = sc.next().charAt(0); String level;
		 */

		/*
		 * if(next == 'y' || next == 'Y'){
		 * System.out.println("What file would you like to load?"); level =
		 * sc.next(); try { game = GameLevel.parse(Parse.readFromFile(level)); }
		 * catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } } else{
		 */
		// System.out.println("Generating generic file....");
		// game = new GameLevel(("Game/castle.jpg"));
		// game2 = new GameLevel("Game/finalv2.png");
		// }

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				/*
				 * System.out.println("Would you like to save this level?");
				 * char choice = sc.next().charAt(0); if (choice == 'y' ||
				 * choice == 'Y') {
				 * System.out.println("What name would like to save it as?");
				 * String str = sc.next(); try {
				 * Parse.writeToFile(game.toString(), str + ".txt"); } catch
				 * (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } } else{ Platform.exit();
				 * System.exit(0); }
				 */
				Platform.exit();
				System.exit(0);
			}
		});

		scene.setOnKeyPressed(Events.keyPress);
		scene.setOnKeyReleased(Events.keyRelease);

		/*
		 * BouncyBall bouncy = new BouncyBall(45, 90, 8, Color.BLUE); Wall
		 * platform = new Wall(50, 50, 25, 3); Projectile proj = new
		 * Projectile(15.f, 75.f, 2.f, 1.f, 3.f); platform.addToWorld(game);
		 * proj.addToWorld(game); bouncy.addToWorld(game); ((Body)
		 * (proj.node.getUserData())).setLinearVelocity(new Vec2(50.0f, 0.0f));
		 */
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
