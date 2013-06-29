package infrastructure;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import org.jbox2d.common.Vec2;
import utils.Parse;

import entities.Entity;
import entities.Player;
import guiobject.Camera;

public class GameWorld implements Serializable {
	public static GameWorld world = new GameWorld();
	private LinkedList<GameMap> maps;
	private GameMap currentMap;
	private Entity player;
	private Boolean isAtDoor;
	private Time time;

	private GameWorld() {
		currentMap = new GameMap();
		player = new Player(currentMap.getPX(), currentMap.getPY());
		player.addToMap(currentMap);
		maps = new LinkedList<GameMap>();
		isAtDoor = false;
		maps.add(currentMap);
		currentMap.setVisible(true);
	}
	public synchronized GameMap getCurrentMap() {
		return currentMap;
	}

	public synchronized void setPlayer(Entity entity) {
		this.player = entity;
	}

	public synchronized Entity getPlayer() {
		return player;
	}

	public synchronized LinkedList<GameMap> getMaps() {
		return maps;
	}

	public synchronized void addMap(GameMap game) {
		maps.add(game);
	}

	public synchronized void changeMap(GameMap Map) throws IOException {
		if (currentMap != null) {
			// currentMap.reset();
			time.killTime();
			currentMap.setVisible(false);
		}
		currentMap = Map;
		Camera.camera.reset();
		player = new Player(currentMap.getPX(), currentMap.getPY());
		player.addToMap(Map);
		player.getBody().setLinearVelocity(new Vec2(0, 0));
		currentMap.setVisible(true);
		App.root.getChildren().removeAll(App.menuBar);
		App.root.getChildren().addAll(App.menuBar);
		time.startTime();
	}

	public synchronized Boolean isPaused() {
		return time.isPaused();
	}	

	@Override
	public synchronized String toString() {
		String str = "";
		Iterator iter = maps.iterator();
		while (iter.hasNext()) {
			str += iter.next().toString() + Parse.delim;
		}
		return str;
	}
	
	public synchronized void isAtDoor(boolean b) {
		this.isAtDoor = b;
	}

	public synchronized boolean getIsAtDoor() {
		return isAtDoor;
	}
}
