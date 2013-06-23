package utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;

public class Parse {
	public final static String delim = ";";
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static void writeToFile(String code, String fileName)
			throws IOException {
		Path path = Paths.get(fileName);
		ArrayList<String> list = new ArrayList<String>();
		list.add(code);
		Files.write(path, list, ENCODING);
	}

	public static String[] fragment(String str) {
		return str.split("[;]");
	}

	

	

	/*
	 * public static void writeToFile(String fileName, GameMap m) throws
	 * IOException { try { FileOutputStream fileOut = new
	 * FileOutputStream(fileName); ObjectOutputStream out = new
	 * ObjectOutputStream(fileOut); out.writeObject(m); out.close();
	 * fileOut.close(); } catch (IOException i) {
	 * System.out.println("Error writing GameMap to file.");
	 * i.printStackTrace(); } }
	 * 
	 * public static GameMap readFromFile(String fileName) throws IOException,
	 * ClassNotFoundException { GameMap m = null; try { FileInputStream fileIn =
	 * new FileInputStream(fileName); ObjectInputStream in = new
	 * ObjectInputStream(fileIn); m = (GameMap) in.readObject(); in.close();
	 * fileIn.close(); } catch (IOException i) { i.printStackTrace(); return
	 * null; } catch (ClassNotFoundException c) {
	 * System.out.println("Employee class not found"); c.printStackTrace();
	 * return null; } return m; }
	 */

}
