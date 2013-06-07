package infrastructure;

import java.io.Serializable;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import entities.Entity;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BackGround implements FXJBox, Serializable {
	private String path;
	private ImageView view;
	private Image image;

	public BackGround(String path) {
		this.path = path;
		image = new Image(path);
		view = new ImageView(image);
		view.setLayoutX(0);
		view.setLayoutY(-getPHeight() + Util.HEIGHT);
	}
	public String getPath(){
		return path;
	}

	@Override
	public synchronized void setLayoutX(float x) {
		view.setLayoutX(x);
	}

	@Override
	public synchronized void setLayoutY(float y) {
		view.setLayoutY(y);
	}

	@Override
	public synchronized double getPWidth() {
		return image.getWidth();
	}

	@Override
	public synchronized double getPHeight() {
		return image.getHeight();
	}

	@Override
	public synchronized float getWidth() {
		return (float) Util.toWidth(image.getWidth());
	}

	@Override
	public synchronized float getHeight() {
		return (float) Util.toHeight(image.getHeight());
	}
	
	public static BackGround parse(String path){
		return new BackGround(path);
	}

	@Override
	public String toString() {
		return path;
	}

	@Override
	public void setVisible(Boolean bool) {
		if (bool == true) {
			if (App.root.getChildren().contains(view) == true) {
				App.root.getChildren().remove(view);
				App.root.getChildren().add(view);
			} else
				App.root.getChildren().add(view);
		} else if (App.root.getChildren().contains(view) == true)
			App.root.getChildren().remove(view);
	}

	@Override
	public synchronized Vec2 getPosition() {
		return new Vec2((float) Util.toPosX(view.getLayoutX()),
				(float) Util.toPosY(view.getLayoutY()));
	}

	@Override
	public synchronized Vec2 getPPosition() {
		return new Vec2((float) view.getLayoutX(), (float) view.getLayoutY());
	}
}
