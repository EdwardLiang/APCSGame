package entities;

public class Floor extends Wall {

	public Floor(float posX, float posY, float width, float height) {
		super(posX, posY, width, height);
	}

	@Override
	public void setVisible(Boolean bool) {
		return;
	}

	public static Floor parse(String[] frag) {
		return new Floor(Float.parseFloat(frag[1]), Float.parseFloat(frag[2]),
				Float.parseFloat(frag[3]), Float.parseFloat(frag[4]));
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
