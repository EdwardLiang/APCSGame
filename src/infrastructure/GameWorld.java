package infrastructure;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import org.jbox2d.common.Vec2;
import utils.Parse;

import entities.Entity;
import entities.Player;

public class GameWorld implements Serializable {
	private LinkedList<GameMap> maps;
	private GameMap currentMap;
	private Entity player;
	private Boolean isAtDoor;

	public GameWorld() throws IOException {
		player = new Player(currentMap.getPX(), currentMap.getPY());
		player.addToMap(currentMap);
		maps = new LinkedList<GameMap>();
		isAtDoor = false;
		maps.add(currentMap);
		currentMap.startTime();
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
			currentMap.killTime();
			currentMap.setVisible(false);
			currentMap.setTimeData(new TimeData());
		}
		currentMap = Map;
		App.camera.reset();
		player = new Player(currentMap.getPX(), currentMap.getPY());
		player.addToMap(Map);
		player.getBody().setLinearVelocity(new Vec2(0, 0));
		currentMap.setVisible(true);
		App.root.getChildren().removeAll(App.menuBar);
		App.root.getChildren().addAll(App.menuBar);
		currentMap.startTime();
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
