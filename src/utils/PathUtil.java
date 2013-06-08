package utils;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

public class PathUtil {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static Vec2 PtoLocalPoint(Vec2 world, Vec2 topLeft) {
		return new Vec2(world.x - topLeft.x, world.y - topLeft.y);
	}
	public static Vec2 toLocalPoint(Vec2 world, Vec2 bottomLeft){
		return new Vec2(world.x - bottomLeft.x, world.y - bottomLeft.y);
	}

	public static Vec2 toWorldPoint(Vec2 local, Vec2 topLeft) {
		return new Vec2(local.x + topLeft.x, local.y + topLeft.y);
	}

	public static Vec2 topLeft(Vec2[] world) {
		return new Vec2(minX(world), minY(world));
	}

	public static Vec2 bottomLeft(Vec2[] world) {
		return new Vec2(minX(world), minY(world));
	}

	public static float posX(String url) throws IOException {
		Vec2[] worldPoints = readInPoints(url);
		return shapeCenter(worldPoints).x;
	}

	public static float posY(String url) throws IOException {
		Vec2[] worldPoints = readInPoints(url);
		return posY(worldPoints);
	}

	public static float width(String url) throws IOException{
		Vec2[] worldPoints = readInPoints(url);
		return width(worldPoints);
	}

	public static float height(String url) throws IOException {
		Vec2[] worldPoints = readInPoints(url);
		return height(worldPoints);
	}
	public static float posX(Vec2[] worldPoints) {
		return shapeCenter(worldPoints).x;
	}

	public static float posY(Vec2[] worldPoints) {
		return shapeCenter(worldPoints).y;
	}

	public static float width(Vec2[] worldPoints){
		return maxX(worldPoints) - minX(worldPoints);
	}

	public static float height(Vec2[] worldPoints) {
		return maxY(worldPoints) - minY(worldPoints);
	}


	public static float minX(Vec2[] world) {
		float leftX = Float.MAX_VALUE;
		for (int a = 0; a < world.length; a++) {
			if (world[a].x < leftX)
				leftX = world[a].x;
		}
		return leftX;
	}

	public static float minY(Vec2[] world) {
		float leftY = Float.MAX_VALUE;
		for (int a = 0; a < world.length; a++) {
			if (world[a].y < leftY)
				leftY = world[a].y;
		}
		return leftY;
	}

	public static float maxX(Vec2[] world) {
		float maxX = 0;
		for (int a = 0; a < world.length; a++) {
			if (world[a].x > maxX)
				maxX = world[a].x;
		}
		return maxX;
	}

	public static float maxY(Vec2[] world) {
		float maxY = 0;
		for (int a = 0; a < world.length; a++) {
			if (world[a].y > maxY)
				maxY = world[a].y;
		}
		return maxY;
	}

	public static Vec2[] readInPPoints(String url) throws IOException {
		Path path = Paths.get(url);
		List<String> listed = Files.readAllLines(path, ENCODING);
		String line10 = listed.get(9);
		line10 = line10.substring(13, line10.length() - 4);
		String[] coordPairs = line10.split("[ ]");
		for (int a = 0; a < coordPairs.length; a++) {
			coordPairs[a] = coordPairs[a].substring(0,
					coordPairs[a].length() - 1);
		}
		Vec2[] points = new Vec2[coordPairs.length];
		for (int a = 0; a < coordPairs.length; a++) {
			String[] tupleSplit = coordPairs[a].split("[,]");
			float x = Float.parseFloat(tupleSplit[0]);
			float y = Float.parseFloat(tupleSplit[1]);
			points[a] = new Vec2(x, y);
		}
		return points;
	}
	
	public static Vec2[] parseVec2(String str){
		String[] coordPairs = str.split("[ ]");
		Vec2[] points = new Vec2[coordPairs.length];
		for (int a = 0; a < coordPairs.length; a++) {
			String[] tupleSplit = coordPairs[a].split("[,]");
			float x = Float.parseFloat(tupleSplit[0]);
			float y = Float.parseFloat(tupleSplit[1]);
			points[a] = new Vec2(x, y);
		}
		return points;
	}

	public static Vec2[] readInPoints(String url) throws IOException {
		Vec2[] pixelPath = readInPPoints(url);
		Vec2[] path = new Vec2[pixelPath.length];
		for (int a = 0; a < pixelPath.length; a++) {
			path[a] = new Vec2(Util.toPosX(path[a].x), Util.toPosY(path[a].y));
		}
		return path;
	}

	public static Vec2[] readInPLocal(String url) throws IOException {
		Vec2[] pWorldPoints = readInPPoints(url);
		Vec2[] pLocal = PWorldToPLocal(pWorldPoints);
		return pLocal;
	}

	public static Vec2[] readInLocal(String url) throws IOException {
		Vec2[] pPoints = readInPPoints(url);
		Vec2[] points = Util.toPoints(pPoints);
		Vec2[] local = worldToLocal(points);
		return local;
	}

	public static Vec2[] readInFileToPLocal(String url) throws IOException {
		return PWorldToPLocal(readInPoints(url));
	}
	
	public static Vec2[] PWorldToLocal(Vec2[] pPoints){
		Vec2[] points = Util.toPoints(pPoints);
		Vec2[] local = worldToLocal(points);
		return local;
	}

	public static Vec2[] PWorldToPLocal(Vec2[] world) {
		Vec2[] local = new Vec2[world.length];
		Vec2 topLeft = topLeft(world);
		for (int a = 0; a < world.length; a++) {
			local[a] = toLocalPoint(world[a], topLeft);
		}
		return local;
	}

	public static Vec2[] worldToLocal(Vec2[] world) {
		Vec2[] local = new Vec2[world.length];
		Vec2 bottomLeft = bottomLeft(world);
		for (int a = 0; a < world.length; a++) {
			local[a] = toLocalPoint(world[a], bottomLeft);
		}
		return local;
	}

	public static Vec2[] localToWorld(Vec2[] local, Vec2 topLeft) {
		Vec2[] world = new Vec2[local.length];
		for (int a = 0; a < local.length; a++) {
			world[a] = toWorldPoint(world[a], topLeft);
		}
		return world;
	}

	public static Double[] vecToDouble(Vec2[] array) {
		Double[] doubles = new Double[array.length * 2];
		int index = 0;
		for (int a = 0; a < array.length; a++) {
			doubles[index] = (double) array[a].x;
			doubles[index + 1] = (double) array[a].y;
			index += 2;
		}
		return doubles;
	}

	public static Vec2 shapeCenter(Vec2[] world) {
		float midX = (minX(world) + maxX(world)) / 2;
		float midY = (minY(world) + maxY(world)) / 2;
		return new Vec2(midX, midY);
	}

	public static Vec2[] shapePoints(Vec2[] local) {
		Vec2[] shapePoints = new Vec2[local.length];
		Vec2 lCenter = shapeCenter(local);
		for (int a = 0; a < local.length; a++) {
			shapePoints[a] = new Vec2();
			shapePoints[a].x = local[a].x - lCenter.x;
			shapePoints[a].y = local[a].y - lCenter.y;
		}
		return shapePoints;
	}

	public static Shape makeShape(Vec2[] verts) {
		PolygonShape polygon = new PolygonShape();
		((PolygonShape) polygon).m_centroid.setZero();
		polygon.set(verts, verts.length);
		return polygon;
	}

	public static Shape readToShape(String url) throws IOException {
		Vec2[] vert = readInLocal(url);
		Vec2[] verts = shapePoints(vert);
		return makeShape(verts);
	}

	public static Node makeNode(Vec2[] verts) {
		Polygon polygon = new Polygon();
		Double[] doubles = vecToDouble(verts);
		polygon.getPoints().addAll(doubles);
		polygon.setFill(Color.BLUE);
		return polygon;
	}

	public static Node readToNode(String url) throws IOException {
		Vec2[] verts = readInPLocal(url);
		return makeNode(verts);
	}
	public static String pToString(Vec2[] verts){
		String converted = "";
		for(Vec2 a: verts){
			converted = converted + a.x + "," + a.y + " ";
		}
		converted = converted.substring(0,converted.length()-1);
		return converted;
	}
}
