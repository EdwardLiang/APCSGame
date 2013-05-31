package Game;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameWorld extends GameLevel{
	public LinkedList<GameLevel> levels;
	public GameWorld() {
		super("GameWorld.jpg");
		levels = new LinkedList<GameLevel>();
	}
	public static void Parse(String raw){
		String[] levelList = Parse.fragment(raw);
		GameWorld GameWorld = new GameWorld();
		for(String a: levelList)
			GameWorld.addLevel(new GameLevel(a));
	}
	public String toString(){
		String str = "";
		for(GameLevel level: levels){
			str += level.toString() + Parse.delim;
		}
		return str;
	}
	public void addLevel(GameLevel game){
		levels.add(game);
	}
}
