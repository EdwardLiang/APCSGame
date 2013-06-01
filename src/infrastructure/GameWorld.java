package infrastructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import entities.Creature;
import entities.Entity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GameWorld {
	private LinkedList<GameMap> maps;
	private GameMap currentMap;
	private Entity player;
	private Camera camera;

	public GameWorld() {
		player = new Creature(30, 80);
		currentMap = new GameMap(new BackGround("maps/menu.jpg"));
		player.addToWorld(currentMap);
		maps = new LinkedList<GameMap>();
		camera = new Camera();
		maps.add(currentMap);
		currentMap.startTime();
	}

	public GameMap getCurrentMap() {
		return currentMap;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setPlayer(Entity entity) {
		this.player = entity;
	}

	public Entity getPlayer() {
		return player;
	}

	public LinkedList<GameMap> getMaps() {
		return maps;
	}

	public void addMap(GameMap game) {
		maps.add(game);
	}

	public void changeMap(GameMap Map) {
		if (currentMap != null) {
			currentMap.reset();
			currentMap.setVisible(false);
		}
		camera.reset();

		currentMap = Map;
		player.addToWorld(Map);
		currentMap.setVisible(true);
		currentMap.startTime();
	}

	public static GameWorld parse(String raw) {
		String[] MapList = Parse.fragment(raw);
		GameWorld game = new GameWorld();
		for (String a : MapList) {
			try {
				game.addMap(GameMap.parse(Parse.readFromFile(a)));
			} catch (IOException e) {
				System.out
						.println("Error: attempted to add a Map that doesn't exist");
				e.printStackTrace();
			}
		}
		return game;
	}

	public String toString() {
		String str = "";
		Iterator iter = maps.iterator();
		// skip adding menu to toString()
		iter.next();
		while (iter.hasNext()) {
			str += iter.next().toString() + Parse.delim;
		}
		return str;
	}
}
