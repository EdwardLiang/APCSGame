package infrastructure;

import guiobject.Camera;
import guiobject.CustomCamera;
import guiobject.EdwardPopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.Timer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import utils.Parse;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import keymanagers.CreationKeys;
import keymanagers.DCreationKeys;
import keymanagers.DefaultKeys;
import keymanagers.DevModeKeys;
import keymanagers.FlyingKeys;
import keymanagers.InertialKeys;
import keymanagers.PartialTimeControlKeys;
import keymanagers.PixelEscapeKeys;
import keymanagers.SlowKeys;
import keymanagers.TimeControlKeys;

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
		player.getBody().setLinearVelocity(new Vec2(0,0));
		currentMap.setVisible(true);
		App.root.getChildren().removeAll(App.menuBar);
		App.root.getChildren().addAll(App.menuBar);
		currentMap.startTime();
		EdwardPopup pop = new EdwardPopup("");

		if (Map.getBack().getPath().equals("maps/1-1.jpg")) {
			pop = new EdwardPopup(
					"I once could Dream...\nWASD to move. N to go through doors");
			pop.getText().setFill(Color.WHITE);
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-2.jpg")) {
			pop = new EdwardPopup(
					"...back when I could spend my time playing with blocks.\n R to reset.");
			pop.getText().setFill(Color.WHITE);
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-3.jpg")) {
			FlyingKeys keyManager = new FlyingKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup("Sometimes I would imagine I could fly.");
			pop.getText().setFill(Color.WHITE);
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-4.png")) {
			pop.toggle();
			InertialKeys keyManager = new InertialKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup("Sometimes I was an astronaut");
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-6.jpg")) {
			pop.toggle();
			DCreationKeys keyManager = new DCreationKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup(
					"I could create solutions with only my mind.\n"
							+ "Draw two or more markers and press 1 to generate a block");
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-8.jpg")) {
			pop.toggle();
			CreationKeys keyManager = new CreationKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup(
					"Sometimes I had to create my own floor to stand on.\n"
							+ "Draw two or more markers and press 2 to generate a static block");
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-7.jpg")) {
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
			pop = new EdwardPopup(
					"Then my mind turned on me. Reality battered me around.");
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/2-2.jpg")) {
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
			pop = new EdwardPopup("");
			pop.getText().setFill(Color.BLACK);
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-5.jpg")) {
			pop.toggle();
			PartialTimeControlKeys keyManager = new PartialTimeControlKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup(
					"The world became deadly. I lost my dreams. I traded them bend time.\n"
							+ "Press Q to toggle time slow.");
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/1-5-1.jpg")) {
			pop.toggle();
			TimeControlKeys keyManager = new TimeControlKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup("I replayed my worst moments.\n"
					+ "Press F to toggle reverse time");
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/2-3.jpg")) {
			pop.toggle();
			SlowKeys keyManager = new SlowKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
			pop = new EdwardPopup(
					"I always hesitated. I slowed down but the world sped up.");
			pop.toggle();
		}
		if (Map.getBack().getPath().equals("maps/3-1.jpg")) {
			pop.toggle();
			DefaultKeys keyManager = new DefaultKeys();
			App.key.interrupt();
			App.key = new Thread(keyManager.keyThread);
			App.key.start();
			App.scene.setOnKeyPressed(keyManager.keyPress);
			App.scene.setOnKeyReleased(keyManager.keyRelease);
		}
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
