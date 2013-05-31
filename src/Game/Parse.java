package Game;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Parse {
	final static String delim = ";";
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	public static void writeToFile(String code, String fileName)
			throws IOException {
		Path path = Paths.get(fileName + ".txt");
		ArrayList<String> list = new ArrayList<String>();
		list.add(code);
		Files.write(path, list, ENCODING);
	}
	
	public static String[] fragment(String str){
		return str.split("[;]");
	}

	public static String readFromFile(String fileName) throws IOException {
		Path path = Paths.get(fileName + ".txt");
		List<String> listed = Files.readAllLines(path, ENCODING);
		String result = "";
		for (String str : listed)
			result += str + "\n";
		return result;
	}

}
