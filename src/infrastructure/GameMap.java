package infrastructure;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import entities.BouncyBall;
import entities.Creature;
import entities.Entity;
import entities.Projectile;
import entities.Wall;

public class GameMap {
	public ArrayList<Entity> gameElements;
	public ImageView backGround;
	public World world;
	public Time time;
	public double pWidth;
	public double pHeight;
	public double width;
	public double height;
	public String title;
	public String bacLoc;
	public float gravityMag;
	public String originalData;

	public GameMap(String backLoc, String title, float gravityMag) {
		world = new World(new Vec2(0.0f, -gravityMag));
		gameElements = new ArrayList<Entity>();
		time = new Time(this);
		Image back = new Image(backLoc);
		backGround = new ImageView(back);
		pWidth = back.getWidth();
		pHeight = back.getHeight();
		width = Utility.toWidth((float) pWidth);
		height = Utility.toHeight((float) pHeight);
		this.title = title;
		this.gravityMag = gravityMag;
		this.bacLoc = backLoc;
		backGround.setLayoutX(App.game.camera.getOffsetX());
		backGround.setLayoutY(-pHeight + Utility.HEIGHT
				+ App.game.camera.getOffsetY());
	}

	public GameMap(String backLoc) {
		this.title = "test";
		this.bacLoc = backLoc;
		this.gravityMag = 30.0f;
		world = new World(new Vec2(0.0f, -30.0f));
		gameElements = new ArrayList<Entity>();
		time = new Time(this);

		Image back = new Image(backLoc);
		backGround = new ImageView(back);
		pWidth = back.getWidth();
		pHeight = back.getHeight();
		width = (int) Utility.toWidth(pWidth);
		height = (int) Utility.toHeight(pHeight);
		backGround.setLayoutX(0);
		backGround.setLayoutY(-pHeight + Utility.HEIGHT);
		addCoreElements();
	}

	public void reset() {
		stopAll();
		for (Entity a : gameElements) {
			a.removeFromWorld();
		}
		
		if (originalData != null) {
			String[] parsed = originalData.split("[\n]");
			for (int i = 3; i < parsed.length - 1; i++) {
				parseElements(parsed[i]).addToWorld(this);
			}
		}
		else{
			addCoreElements();
		}
	}

	public void addElementsToGUI() {
		App.root.getChildren().add(backGround);
		for (Entity a : gameElements) {
			App.root.getChildren().add(a.node);
		}
		App.pS.setScene(App.scene);
		App.pS.show();
	}

	public void stopAll() {
		time.timeline.stop();
	}

	// Use Entity's addToWorld method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public void addEntity(Entity entity) {
		gameElements.add(entity);
	}

	public void removeEntity(Entity entity) {
		gameElements.remove(entity);
	}

	public void addCoreElements() {
		Wall left = new Wall(0, (float) height / 2, 1, (float) height);
		Wall right = new Wall((float) width, (float) height / 2, 1,
				(float) height);
		Wall top = new Wall((float) width / 2, (float) height, (float) width, 1);
		Wall bottom = new Wall((float) width / 2, 0, (float) width, 1);
		left.addToWorld(this);
		right.addToWorld(this);
		top.addToWorld(this);
		bottom.addToWorld(this);
	}

	private static Entity parseElements(String raw) {
		String[] frags = Parse.fragment(raw);
		String className = frags[0];
		switch (className) {
		case "class entities.BouncyBall":
			return BouncyBall.parse(frags);
		case "class entities.Creature":
			return Creature.parse(frags);
		case "class entities.Projectile":
			return Projectile.parse(frags);
		case "class entities.Wall":
			return Wall.parse(frags);
		default:
			return null;
		}
	}

	public static GameMap parse(String raw) {
		String[] parsed = raw.split("[\n]");
		GameMap game = new GameMap(parsed[0], parsed[1],
				Float.parseFloat(parsed[2]));
		for (int i = 3; i < parsed.length - 1; i++) {
			parseElements(parsed[i]).addToWorld(game);
		}
		game.originalData = game.toString();
		return game;
	}

	@Override
	public String toString() {
		String result = "";
		result += bacLoc + "\n";
		result += title + "\n";
		result += gravityMag + "\n";
		for (Entity a : gameElements) {
			if (a != App.game.player)
				result += a.toString() + "\n";
		}
		return result;
	}
}
