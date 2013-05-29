package Game;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/*
 * String code for GameWorld:
 * Delimiter: ;
 * Using: String.split(), which passes a String and a delimiter, and returns an array of split Strings.  
 * 0.Name of world
 * 1.Gravity
 * 2.Name of background file in format of: "file:NAME.jpg"
 * 3.Number of entities to add
 * For each entity to add: 
 * 1.Enter code for entity type: 1=ball, 2=wall, 3=projectile, 4=creature
 * 
 */
public class Parse {
	final static String delim = "\n";
	final static Charset ENCODING = StandardCharsets.UTF_8;
	/*public static String inputToParsed() {
		String result = "";
		Scanner input = new Scanner(System.in);
		System.out.println("Name of World?");
		result += input.next() + delim;
		System.out.println("Strength of Gravity(+)?");// pass in a magnitude for
														// y direction of vector
		result += input.next() + delim;
		System.out.println("Name of background file?");
		result += "file:" + input.next() + delim;
		System.out.println("Number of entities to add?");
		int numEnts = input.nextInt();
		result += numEnts + delim;
		for (int i = 0; i < numEnts; i++) {
			System.out.println("Enter your Entity to add. ");
			String choice = input.next();
			int code = Utility.codeEnt(choice);
			switch (code) {
			case 1:
				result += "1" + delim;
				System.out.println("posX?");
				result += input.nextFloat() + delim;
				System.out.println("posY?");
				result += input.nextFloat() + delim;
				System.out.println("radius?");
				result += input.nextInt() + delim;
				System.out.println("Color?");
				break;
			case 2:
				result += "2" + delim;
				System.out.println("posX?");
				result += input.nextFloat() + delim;
				System.out.println("posY?");
				result += input.nextFloat() + delim;
				System.out.println("Width?");
				result += input.nextFloat() + delim;
				System.out.println("Height?");
				result += input.nextFloat() + delim;
				break;
			case 3:
				result += "3" + delim;
				System.out.println("posX?");
				result += input.nextFloat() + delim;
				System.out.println("posY?");
				result += input.nextFloat() + delim;
				System.out.println("Width?");
				result += input.nextFloat() + delim;
				System.out.println("Height?");
				result += input.nextFloat() + delim;
				System.out.println("Radius?");
				result += input.nextFloat() + delim;
				break;
			case 4:
				result += "4" + delim;
				System.out.println("posX?");
				result += input.nextFloat() + delim;
				System.out.println("posY?");
				result += input.nextFloat() + delim;
			default:
				break;
			}
		}
		System.out.println(result);
		return result;
	}*/
/*
	public static Entity parseElements(String raw) {
		String[] frags = Utility.fragment(raw);
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

	public static GameWorld parse(String raw) {
		String[] parsed = raw.split("[\n]");
		ArrayList<Entity> elements = new ArrayList<Entity>();
		for (int i = 3; i < parsed.length - 1; i++) {
			elements.add(parseElements(parsed[i]));
		}
		GameWorld game = new GameWorld(parsed[0], parsed[1], elements,
				Float.parseFloat(parsed[2]));
		return game;
	}*/
	
	public static void writeToFile(String code, String fileName)
			throws IOException {
		Path path = Paths.get(fileName + ".txt");
		ArrayList<String> list = new ArrayList<String>();
		list.add(code);
		Files.write(path, list, ENCODING);
	}

	public static String readFromFile(String fileName) throws IOException {
		Path path = Paths.get(fileName + ".txt");
		List<String> listed = Files.readAllLines(path, ENCODING);
		String result = "";
		for (String str : listed)
			result += str + "\n";
		return result;
	}

	/*public static ArrayList<String> addLevelsToAdd(ArrayList<String> levelsList) {
		levelsList.add("TestLevel");
		return levelsList;
	}*/


}
