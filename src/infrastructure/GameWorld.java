package infrastructure;

import guiobject.Camera;
import guiobject.CustomCamera;
import guiobject.EdwardPopup;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import org.jbox2d.dynamics.Fixture;

import utils.Parse;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import keymanagers.CreationKeys;
import keymanagers.DevModeKeys;
import keymanagers.FlyingKeys;
import keymanagers.InertialKeys;
import keymanagers.PixelEscapeKeys;

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
		currentMap = GameMap.parse(Parse.readFromFile(App.getLevelForIndex(0)),
				App.getLevelForIndex(0));
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

	public synchronized void changeMap(GameMap Map) {
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
		currentMap.setVisible(true);
		App.root.getChildren().removeAll(App.menuBar);
		App.root.getChildren().addAll(App.menuBar);
		EdwardPopup pop = new EdwardPopup("");
		if(Map.getBack().getPath().equals("maps/1-1.jpg")){
			pop = new EdwardPopup("Welcome to Dreamscape! WASD is to move, R is reset, N is to move on to the next level if you're at a door, and P is to pause. \nBut wait! You're also a master of Time! B is to reverse time, V is to continue time normally, and shift is to temporarily move back in time. E is to speed up time, and Q is to slow it down. Good luck!");
			pop.getText().setFill(Color.WHITE);
			pop.toggle();
		}
		if(Map.getBack().getPath().equals("maps/1-3.jpg")){
			FlyingKeys keyManager = new FlyingKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop= new EdwardPopup("You can fly!");
			pop.getText().setFill(Color.BLUE);
			pop.toggle();
		}
		if(Map.getBack().getPath().equals("maps/1-4.png")){
			pop.toggle();
			InertialKeys keyManager = new InertialKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup("You are in space. Congrats. Have fun hitting that door!");
			pop.toggle();
		}
		if(Map.getBack().getPath().equals("maps/1-7.jpg")){
			pop.toggle();
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
			pop = new EdwardPopup("You are a gas particle! Have fun!");
		}
		if(Map.getBack().getPath().equals("maps/2-2.jpg")){
			pop.toggle();
			CreationKeys keyManager = new CreationKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			App.cam.interrupt();
			App.camera = new Camera();
			App.cam = new Thread(App.camera);
			App.cam.start();
			pop = new EdwardPopup("YOU are now making the game! 1 makes a dynamic object, 2 makes a static one. As always, R is reset. Have fun!");
			pop.getText().setFill(Color.BLACK);
			pop.toggle();
		}
		if(Map.getBack().getPath().equals("maps/1-5.jpg")){
			pop.toggle();
			DevModeKeys keyManager = new DevModeKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
		}

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
