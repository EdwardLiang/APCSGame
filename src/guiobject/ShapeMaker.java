package guiobject;

import entities.DynamicPathEntity;
import entities.Entity;
import entities.StaticPathEntity;

import infrastructure.App;
import infrastructure.GameWorld;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

public class ShapeMaker {
	public ArrayList<Marker> marks;

	public ShapeMaker() {
		marks = new ArrayList<Marker>();
	}

	public void addMarker(float pPosX, float pPosY) {
		Marker mark = new Marker(pPosX, pPosY);
		marks.add(mark);
		mark.setVisible(true);
	}

	public void clearMarkers() {
		for (Marker a : marks) {
			a.setVisible(false);
		}
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
		en.addToMap(GameWorld.world.getCurrentMap());
		en.setVisible(true);
		clearMarkers();
		return en;
	}

	public Entity generateStaticEntity() {
		StaticPathEntity en = new StaticPathEntity(getLocations());
		en.addToMap(GameWorld.world.getCurrentMap());
		en.setVisible(true);
		clearMarkers();
		return en;
	}
}
