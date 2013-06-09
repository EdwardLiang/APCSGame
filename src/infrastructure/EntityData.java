package infrastructure;

import entities.Entity;
import entities.Player;

public class EntityData {
	private float locX;
	private float locY;
	public EntityData(Entity e){
		locX = e.getBody().getPosition().x;
		locY = e.getBody().getPosition().y;
	}
	public float getX(){
		return locX;
	}
	public float getY(){
		return locY;
	}
}
