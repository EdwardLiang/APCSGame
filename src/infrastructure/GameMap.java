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
	private ArrayList<Entity> gameElements;
	private BackGround back;
	private World world;
	private Time time;
	private float width;
	private float height;
	private String title;
	private float gravityMag;
	private String originalData;

	public GameMap(BackGround back, String title, float gravityMag) {
		this.world = new World(new Vec2(0.0f, -gravityMag));
		this.gameElements = new ArrayList<Entity>();
		this.time = new Time(this);
		this.width = back.getWidth();
		this.height = back.getHeight();
		this.title = title;
		this.gravityMag = gravityMag;
		this.back = back;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public double getPHeight() {
		return back.getPHeight();
	}

	public double getPWidth() {
		return back.getWidth();
	}

	public Boolean isPaused() {
		return time.isPaused();
	}

	public void startTime() {
		time.startTime();
	}

	public GameMap(BackGround back) {
		this.title = "test";
		this.gravityMag = 30.0f;
		this.world = new World(new Vec2(0.0f, -30.0f));
		this.gameElements = new ArrayList<Entity>();
		this.time = new Time(this);
		this.back = back;
		this.width = back.getWidth();
		this.height = back.getHeight();
		addCoreElements();
	}

	public World getPhysics() {
		return world;
	}

	public void reset() {
		stopAll();
		removeAll();
		if (originalData != null) {
			String[] parsed = originalData.split("[\n]");
			for (int i = 3; i < parsed.length - 1; i++) {
				parseElements(parsed[i]).addToWorld(this);
			}
		} else {
			addCoreElements();
		}
	}

	public void removeAll() {
		for (Entity a : gameElements) {
			a.removeFromWorld();
		}
	}

	public void setVisible(Boolean bool) {
		if (bool == true) {
			for (Entity a : gameElements) {
				a.setVisible(true);
			}
			App.pS.setScene(App.scene);
			App.pS.show();
		} else {
			for (Entity a : gameElements) {
				a.setVisible(false);
			}
		}
	}

	private void stopAll() {
		time.timeline.stop();
	}

	// Use Entity's addToWorld method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public void addEntity(Entity entity) {
		gameElements.add(entity);
	}

	// Use Entity's removeFromWorld method. DO NOT DIRECTLY CALL THIS METHOD.
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

	public void toggleTime() {
		time.toggleTime();
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
		GameMap game = new GameMap(BackGround.parse(parsed[0]), parsed[1],
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
		result += back.toString() + "\n";
		result += title + "\n";
		result += gravityMag + "\n";
		for (Entity a : gameElements) {
			if (a != App.game.getPlayer())
				result += a.toString() + "\n";
		}
		return result;
	}
}
