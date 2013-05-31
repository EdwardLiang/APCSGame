package Game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class GameLevel {
	public ArrayList<Entity> gameElements;
	public ImageView backGround;
	public World world;
	public Time time;
	public double pWidth;
	public double pHeight;
	public double width;
	public double height;
	public String title;
	public String bacLoc;
	public float gravityMag;

	public GameLevel(String backLoc, String title,
			float gravityMag) {
		world = new World(new Vec2(0.0f, -gravityMag));
		gameElements = new ArrayList<Entity>();		
		time = new Time(this);
		Image back = new Image(backLoc);
		backGround = new ImageView(back);
		pWidth = back.getWidth();
		pHeight = back.getHeight();
		width = Utility.toWidth((float) pWidth);
		height = Utility.toHeight((float) pHeight);
		this.title = title;
		this.bacLoc = bacLoc;
		this.gravityMag = gravityMag;
		backGround.setLayoutX(App.game.getOffsetX());
		backGround.setLayoutY(-pHeight + Utility.HEIGHT + App.game.getOffsetY());
	}

	public GameLevel(String backLoc) {
		this.title = "test";
		this.bacLoc = backLoc;
		this.gravityMag = 30.0f;
		world = new World(new Vec2(0.0f, -30.0f));
		gameElements = new ArrayList<Entity>();
		time = new Time(this);
		
		Image back = new Image(backLoc);
		backGround = new ImageView(back);
		pWidth = back.getWidth();
		pHeight = back.getHeight();
		width = (int) Utility.toWidth(pWidth);
		height = (int) Utility.toHeight(pHeight);
		backGround.setLayoutX(0);
		backGround.setLayoutY(-pHeight + Utility.HEIGHT);
		
		addCoreElements();
	}

	// Use Entity's addToWorld method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public void addEntity(Entity entity) {
		gameElements.add(entity);
	}
	public void removeEntity(Entity entity){
		gameElements.remove(entity);
	}

	public void addCoreElements() {
		// BouncyBall bouncy = new BouncyBall(45, 90, 8, Color.BLUE);
		// bouncy.addToWorld(this);
		//
		Wall left = new Wall(0, (float)height / 2, 1, (float)height);
		Wall right = new Wall((float)width, (float)height / 2, 1, (float)height);
		Wall top = new Wall((float)width / 2, (float)height, (float)width, 1);
		Wall bottom = new Wall((float)width / 2, 0, (float)width, 1);
		//
		// Wall platform = new Wall(50, 50, 25, 3);
		// Projectile proj = new Projectile(15.f, 75.f, 2.f, 1.f, 3.f);
		//
		left.addToWorld(this);
		right.addToWorld(this);
		top.addToWorld(this);
		bottom.addToWorld(this);
		//
		// platform.addToWorld(this);
		// proj.addToWorld(this);
		//
		// ((Body)(proj.node.getUserData())).setLinearVelocity(new Vec2(50.0f,
		// 0.0f));
	}

	public static Entity parseElements(String raw) {
		String[] frags = Parse.fragment(raw);
		String className = frags[0];
		if (className.equals("class Game.BouncyBall")) {
			return BouncyBall.parse(frags);
		} else if (className.equals("class Game.Creature")) {
			return Creature.parse(frags);
		} else if (className.equals("class Game.Projectile")) {
			return Projectile.parse(frags);
		} else if (className.equals("class Game.Wall")) {
			return Wall.parse(frags);
		} else {
			System.out.println("ERROR: Attempted to add unsupported class");
		}
		return null;
	}

	public static GameLevel parse(String raw) {
		String[] parsed = raw.split("[\n]");
		GameLevel game = new GameLevel(parsed[0], parsed[1],
				Float.parseFloat(parsed[2]));
		for (int i = 3; i < parsed.length - 1; i++) {
			parseElements(parsed[i]).addToWorld(game);
		}
		return game;
	}

	@Override
	public String toString() {
		String result = "";
		result += bacLoc + "\n";
		result += title + "\n";
		result += gravityMag + "\n";
		for (Entity a : gameElements) {
			if(a != App.game.player)
				result += a.toString() + "\n";
		}
		return result;
	}
}
