package infrastructure;
import org.jbox2d.common.Vec2;

public interface FXJBox {
	public void setVisible(Boolean bool);
	public void setLayoutX(float x);
	public void setLayoutY(float y);
	public Vec2 getPosition();
	public Vec2 getPPosition();
	public float getWidth();
	public float getHeight();
	public double getPWidth();
	public double getPHeight();
	public void update();
}
