package infrastructure;

import java.io.Serializable;
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

public class GameMap implements Serializable {
	private ArrayList<Entity> gameElements;
	private BackGround back;
	private World world;
	private Time time;
	private float width;
	private float height;
	private String name;
	private float gravityMag;
	private String originalData;

	public GameMap(BackGround back, String name, float gravityMag) {
		this.world = new World(new Vec2(0.0f, -gravityMag));
		this.gameElements = new ArrayList<Entity>();
		this.time = new Time(this);
		this.width = back.getWidth();
		this.height = back.getHeight();
		this.name = name;
		this.gravityMag = gravityMag;
		this.back = back;
	}

	public ArrayList<Entity> getElements() {
		return gameElements;
	}

	public BackGround getBack() {/* , you don't know what you're dealing with */
		return back;
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

	public void killTime() {
		time.killTime();
	}

	public GameMap(BackGround back) {
		this.name = "Generic";
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
			// TODO
			//
			//

		} else {
			addCoreElements();
		}
	}

	public void removeAll() {
		for (Entity a : gameElements) {
			a.removeFromMap();
		}
	}

	public void setVisible(Boolean bool) {
		if (bool == true) {
			back.setVisible(true);
			for (Entity a : gameElements) {
				a.setVisible(true);
			}
			App.pS.setScene(App.scene);
			App.pS.show();
		} else {
			back.setVisible(false);
			for (Entity a : gameElements) {
				a.setVisible(false);
			}
		}
	}

	private void stopAll() {
		time.timeline.stop();
	}

	// Use Entity's addToMap method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public void addEntity(Entity entity) {
		gameElements.add(entity);
	}

	// Use Entity's removeFromMap method. DO NOT DIRECTLY CALL THIS METHOD.
	public void removeEntity(Entity entity) {
		gameElements.remove(entity);
	}

	public void addCoreElements() {
		Wall left = new Wall(0, (float) height / 2, 1, (float) height);
		Wall right = new Wall((float) width, (float) height / 2, 1,
				(float) height);
		Wall top = new Wall((float) width / 2, (float) height, (float) width, 1);
		Wall bottom = new Wall((float) width / 2, 0, (float) width, 1);
		left.addToMap(this);
		right.addToMap(this);
		top.addToMap(this);
		bottom.addToMap(this);
	}

	public void toggleTime() {
		time.toggleTime();
	}

	@Override
	public String toString() {
		String result = "";
		result += back.toString() + "\n";
		result += name + "\n";
		result += gravityMag + "\n";
		for (Entity a : gameElements) {
			if (a != App.game.getPlayer())
				result += a.toString() + "\n";
		}
		return result;
	}
}
