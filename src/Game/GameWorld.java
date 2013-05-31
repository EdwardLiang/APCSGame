package Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GameWorld {
	public LinkedList<GameLevel> levels;
	public GameLevel currentLevel;
	public float offsetX;
	public float offsetY;
	public Entity player;

	public GameWorld() {
		offsetX = 0.0f;
		offsetY = 0.0f;
		player = new Creature(30, 80);
		currentLevel = new GameLevel("Game/menu.jpg");
		player.addToWorld(currentLevel);
		levels = new LinkedList<GameLevel>();
		changeLevel(currentLevel);
		levels.add(currentLevel);
	}

	public synchronized float getOffsetX() {
		return offsetX;
	}

	public synchronized void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public synchronized float getOffsetY() {
		return offsetY;
	}

	public synchronized void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public void setPlayer(Entity entity) {
		this.player = entity;
	}

	public void changeLevel(GameLevel level) {
		if(currentLevel != null){
			currentLevel.stopAll();
		}
		currentLevel = level;
		player.addToWorld(level);
		offsetX = 0.0f;
		offsetY = 0.0f;
		App.root.getChildren().removeAll(App.root.getChildren());
		currentLevel.addElementsToGUI();
		currentLevel.time.startLevel();
	}
	public static GameWorld parse(String raw) {
		String[] levelList = Parse.fragment(raw);
		GameWorld game = new GameWorld();
		for (String a : levelList) {
			try {
				game.addLevel(GameLevel.parse(Parse.readFromFile(a + ".txt")));
			} catch (IOException e) {
				System.out
						.println("Error: attempted to add a level that doesn't exist");
				e.printStackTrace();
			}
		}
		return game;
	}

	public String toString() {
		String str = "";
		Iterator iter = levels.iterator();
		//skip adding menu to toString()
		iter.next();
		while(iter.hasNext()){
			str += iter.next().toString() + Parse.delim;
		}
		return str;
	}

	public void addLevel(GameLevel game) {
		levels.add(game);
	}
}
