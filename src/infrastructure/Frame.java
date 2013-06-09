package infrastructure;

import java.util.HashMap;

import entities.DynamicPathEntity;
import entities.Entity;
import entities.Player;

public class Frame {
	HashMap<Entity,EntityData> eData;
	public Frame(){
		eData = new HashMap<Entity,EntityData>();
	}
	public void addEntity(Entity e){
		eData.put(e, new EntityData(e));
	}
	public void addPlayer(Player e){
		eData.put(e, new PlayerData(e));
	}
	public void addDEntity(DynamicPathEntity e){
		eData.put(e, new DynamicEntityData(e));
	}
	public HashMap<Entity,EntityData> getData(){
		return eData;
	}
}
