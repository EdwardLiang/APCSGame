package entities;

public class Floor extends Wall {

	public Floor(float posX, float posY, float width, float height) {
		super(posX, posY, width, height);
	}

	@Override
	public void setVisible(Boolean bool) {
		return;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
