package infrastructure;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

public class PathParser {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	private static double[][] pPath(String url) throws IOException {
		Path path = Paths.get(url);
		List<String> listed = Files.readAllLines(path, ENCODING);
		String line10 = listed.get(9);
		line10 = line10.substring(13, line10.length() - 4);
		System.out.println(line10);
		String[] coordPairs = line10.split("[ ]");
		double[][] doublePairs = new double[coordPairs.length - 2][2];
		for (int a = 0; a < coordPairs.length; a++) {
			coordPairs[a] = coordPairs[a].substring(0,
					coordPairs[a].length() - 1);
		}
		for (int a = 1; a < coordPairs.length - 1; a++) {
			String[] tupleSplit = coordPairs[a].split("[,]");
			doublePairs[a - 1][0] = Double.parseDouble(tupleSplit[0]);
			doublePairs[a - 1][1] = Double.parseDouble(tupleSplit[1]);
		}
		return doublePairs;
	}

	private static double[][] localDArrayPath(String url) throws IOException {
		double[][] rawPath = pPath(url);
		double leftX = rawPath[0][0];
		double leftY = rawPath[0][1];
		for (int a = 0; a < rawPath.length; a++) {
			rawPath[a][0] = rawPath[a][0] - leftX;
			rawPath[a][1] = rawPath[a][1] - leftY;
		}
		return rawPath;
	}

	public static double[] localPPath(String url) throws IOException {
		double[][] rawPath = localDArrayPath(url);
		int index = 0;
		double[] localPath = new double[rawPath.length * 2];
		for (int a = 0; a < rawPath.length; a++) {
			localPath[index] = rawPath[a][0];
			localPath[index + 1] = rawPath[a][1];
			index += 2;
		}
		return localPath;
	}

	public static Shape localPath(String url) throws IOException {
		double[][] pPath = localDArrayPath(url);
		PolygonShape polygon = new PolygonShape();
		Vec2[] verts = new Vec2[pPath.length];
		for (int a = 0; a < pPath.length; a++) {
			verts[a] = new Vec2((float) Util.toWidth(pPath[a][0]),
					Util.toHeight((float) pPath[a][1]));
		}
		polygon.set(verts, verts.length);
		return polygon;
	}
}
