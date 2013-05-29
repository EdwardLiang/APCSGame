package Game;

import java.util.Scanner;

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
	final static String delim = ";";

	public static String inputToParsed() {
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
		return result;
	}
	public static GameWorld toWorld(String parsed)
	{
		return null;
	}
}
