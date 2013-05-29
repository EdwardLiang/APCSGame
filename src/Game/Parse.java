package Game;

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

	public static GameWorld toWorld(String raw)
	{
		String[] parsed = raw.split("[;]");
		ArrayList<Entity> elements = new ArrayList<Entity>();
		int numToAdd = Integer.parseInt(parsed[3]);
		int index = 4;
		Entity e;
		for(int i = 0; i < numToAdd; i++){
			switch(Integer.parseInt(parsed[index])){
			case 1:
				e = new BouncyBall(Float.parseFloat(parsed[index+1]),Float.parseFloat(parsed[index+2]),Integer.parseInt(parsed[index+3]),Utility.parseColor(parsed[index+4]));
				index+=5;
				break;
			case 2:
				e = new Wall((Float.parseFloat(parsed[index+1])),Float.parseFloat(parsed[index+2]),(Float.parseFloat(parsed[index+3])),Float.parseFloat(parsed[index+4]));
				index+=5;
				break;
			case 3: 
				e = new Projectile((Float.parseFloat(parsed[index+1])),Float.parseFloat(parsed[index+2]),(Float.parseFloat(parsed[index+3])),Float.parseFloat(parsed[index+4]),Float.parseFloat(parsed[index+5]));
				((Body)(e.node.getUserData())).setLinearVelocity(new Vec2(50.0f, 0.0f));
				index+=6;
				break;
			case 4:
				e = new Creature(Float.parseFloat(parsed[index+1]),Float.parseFloat(parsed[index+2]));
				index+=3;
				break;
			default:
				throw new IllegalArgumentException();	
					
					
			}
			elements.add(e);
		}
		
		GameWorld game = new GameWorld(parsed[2],parsed[0],elements,Float.parseFloat(parsed[1]));
		return game;
				
	}
}
