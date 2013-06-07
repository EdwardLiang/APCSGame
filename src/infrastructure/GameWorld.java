package infrastructure;

import inputManagers.DevModeKeys;
import inputManagers.FlyingKeys;
import inputManagers.InertialKeys;
import inputManagers.PixelEscapeKeys;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import org.jbox2d.dynamics.Fixture;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import entities.BouncyBall;
import entities.Creature;
import entities.Entity;
import entities.Player;

public class GameWorld implements Serializable {
	private LinkedList<GameMap> maps;
	private GameMap currentMap;
	private Entity player;
	private Boolean isAtDoor;

	public GameWorld() throws IOException {
		currentMap = GameMap.parse(Parse.readFromFile("menu.txt"), "menu.txt");
		// currentMap = new GameMap(new BackGround("maps/menu.jpg"), 1190, 352,
		// 20, 20, 30.0f);
		player = new Player(currentMap.getPX(), currentMap.getPY());
		player.addToMap(currentMap);
		maps = new LinkedList<GameMap>();
		isAtDoor = false;
		maps.add(currentMap);
		currentMap.startTime();
		currentMap.setVisible(true);
	}

	public GameMap getCurrentMap() {
		return currentMap;
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
			// currentMap.reset();
			currentMap.killTime();
			currentMap.setVisible(false);
		}
		currentMap = Map;
		App.camera.reset();
		player = new Player(currentMap.getPX(), currentMap.getPY());
		player.addToMap(Map);
		currentMap.setVisible(true);
		App.root.getChildren().removeAll(App.menuBar);
		App.root.getChildren().addAll(App.menuBar);
		if(Map.getBack().getPath().equals("maps/1-3.jpg")){
			FlyingKeys keyManager = new FlyingKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
		}
		if(Map.getBack().getPath().equals("maps/1-4.png")){
			InertialKeys keyManager = new InertialKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
		}
		if(Map.getBack().getPath().equals("maps/1-7.jpg")){
			PixelEscapeKeys keyManager = new PixelEscapeKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			App.cam.interrupt();
			App.camera = new CustomCamera();
			App.cam = new Thread(App.camera);
			App.cam.start();
		}

		currentMap.startTime();
	}

	@Override
	public String toString() {
		String str = "";
		Iterator iter = maps.iterator();
		while (iter.hasNext()) {
			str += iter.next().toString() + Parse.delim;
		}
		return str;
	}

	public void isAtDoor(boolean b) {
		this.isAtDoor = b;
	}

	public boolean getIsAtDoor() {
		return isAtDoor;
	}
}
