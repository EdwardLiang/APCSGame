package infrastructure;

import entities.DynamicPathEntity;
import entities.Entity;
import entities.Marker;
import entities.StaticPathEntity;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

public class ShapeMaker {
	public ArrayList<Marker> marks;

	public void addMarker(float pPosX, float pPosY) {
		marks.add(new Marker(pPosX, pPosY));
	}

	public void clearMarkers() {
		marks.clear();
	}

	public Vec2[] getLocations() {
		Vec2[] verts = new Vec2[marks.size()];
		for (int a = 0; a < marks.size(); a++) {
			verts[a] = marks.get(a).pCoord();
		}
		return verts;
	}

	public Entity generateDynamicEntity() {
		DynamicPathEntity en = new DynamicPathEntity(getLocations());
		clearMarkers();
		return en;
	}

	public Entity generateStaticEntity() {
		StaticPathEntity en = new StaticPathEntity(getLocations());
		clearMarkers();
		return en;
	}
}
