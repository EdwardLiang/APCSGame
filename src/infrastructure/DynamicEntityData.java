package infrastructure;

import entities.DynamicPathEntity;

public class DynamicEntityData extends EntityData{
	private double rotation;
	public DynamicEntityData(DynamicPathEntity e) {
		super(e);
		rotation = e.getNode().getRotate();
		// TODO Auto-generated constructor stub
	}
	public double getPreviousRotation(){
		return rotation;
	}
}
