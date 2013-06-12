package infrastructure;

import entities.DynamicPathEntity;
import entities.Entity;

public class DynamicEntityData extends EntityData{
	private double rotation;
	public DynamicEntityData(DynamicPathEntity e) {
		super(e);
		rotation = e.node.getRotate();
		// TODO Auto-generated constructor stub
	}
	public double getPreviousRotation(){
		return rotation;
	}
}
