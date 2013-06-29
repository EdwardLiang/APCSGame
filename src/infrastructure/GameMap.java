package infrastructure;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import utils.Parse;

import entities.*;
import guiobject.BackGround;

public class GameMap implements Serializable {
	private static final long serialVersionUID = 42L;
	private ArrayList<Entity> gameElements;
	private BackGround back;
	private World world;
	private float width;
	private float height;
	private float gravityMag;
	private float playerX;
	private float playerY;

	public GameMap(BackGround back, float playerX, float playerY,
			float gravityMag) {
		this.gravityMag = gravityMag;
		this.world = new World(new Vec2(0.0f, -gravityMag));
		this.gameElements = new ArrayList<Entity>();
		this.back = back;
		this.width = back.getWidth();
		this.height = back.getHeight();
		this.playerX = playerX;
		this.playerY = playerY;
		world.setContactListener(new ContactManager());
	}

	public GameMap() {
		this.gravityMag = -30.0f;
		this.world = new World(new Vec2(0.0f, -gravityMag));
		this.gameElements = new ArrayList<Entity>();
		this.back = new BackGround(ImageLoader.background);
		this.width = back.getWidth();
		this.height = back.getHeight();
		this.playerX = 30;
		this.playerY = 30;
		world.setContactListener(new ContactManager());
	}

	public synchronized ArrayList<Entity> getElements() {
		return gameElements;
	}

	public synchronized void setBack(BackGround back) {
		this.back = back;
	}

	public synchronized BackGround getBack() {
		return back;
	}

	public synchronized float getWidth() {
		return width;
	}

	public synchronized float getHeight() {
		return height;
	}

	public synchronized double getPHeight() {
		return back.getPHeight();
	}

	public synchronized double getPWidth() {
		return back.getWidth();
	}

	public synchronized World getPhysics() {
		return world;
	}

	public synchronized float getPX() {
		return playerX;
	}

	public synchronized float getPY() {
		return playerY;
	}

	public synchronized void reset() {
		System.out.println("not implemented");
	}

	public synchronized void removeAll() {
		gameElements.clear();
		Body body = world.getBodyList();
		while (body.getNext() != null) {
			world.destroyBody(body);
			body = body.getNext();
		}
		world.destroyBody(body);
	}

	public synchronized void setVisible(Boolean bool) {
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

	// Use Entity's addToMap method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public synchronized void addEntity(Entity entity) {
		gameElements.add(entity);
	}

	// Use Entity's removeFromMap method. DO NOT DIRECTLY CALL THIS METHOD.
	public synchronized void removeEntity(Entity entity) {
		gameElements.remove(entity);
	}

	public synchronized void addCoreElements() {
		Wall left = new Wall(0, height / 2, 1, height);
		Wall right = new Wall(width, height / 2, 1, height);
		Wall top = new Wall(width / 2, height, width, 1);
		Wall bottom = new Wall(width / 2, 0, width, 1);
		left.addToMap(this);
		right.addToMap(this);
		top.addToMap(this);
		bottom.addToMap(this);
	}

	public synchronized void addLeftRightWalls() {
		Wall left = new Wall(0, height / 2, 1, height);
		Wall right = new Wall(width, height / 2, 1, height);
		left.addToMap(this);
		right.addToMap(this);
	}

	public synchronized void update() {
		getBack().update();
		for (Entity a : getElements()) {
			a.update();
		}
	}

	public synchronized void restore(Frame frame) {
		for (Entity a : frame.getData().keySet()) {
			a.restore(frame);
		}
	}

	public synchronized void boundCheck() {
		if (GameWorld.world.getPlayer().getPosition().x > GameWorld.world
				.getCurrentMap().getWidth()
				|| GameWorld.world.getPlayer().getPosition().y > GameWorld.world
						.getCurrentMap().getHeight()
				|| GameWorld.world.getPlayer().getPosition().y < 0
				|| GameWorld.world.getPlayer().getPosition().x < 0) {
			((Player) GameWorld.world.getPlayer()).setVisible(false);
			((Player) GameWorld.world.getPlayer())
					.setStatus(Player.Status.DEAD);
			((Player) GameWorld.world.getPlayer()).changeNode();
			((Player) GameWorld.world.getPlayer()).setVisible(true);
		}
	}

	@Override
	public synchronized String toString() {
		String result = "";
		result += back.toString() + "\n";
		result += playerX + "\n";
		result += playerY + "\n";
		result += gravityMag + "\n";

		for (Entity a : gameElements) {
			if (a != GameWorld.world.getPlayer())
				result += a.toString() + "\n";
		}
		return result;
	}
}
