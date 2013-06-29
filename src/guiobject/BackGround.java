package guiobject;

import infrastructure.App;
import infrastructure.FXJBox;
import infrastructure.GameWorld;

import java.io.Serializable;

import org.jbox2d.common.Vec2;
import utils.Util;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BackGround implements FXJBox, Serializable {
	private ImageView view;
	private Image image;

	public BackGround(ImageView view) {
		this.view = new ImageView();
		view.setImage(image);
		// view.setFitWidth(100);
		view.setPreserveRatio(true);
		// view.setCache(true);
		view.setViewport(new Rectangle2D(0, getPHeight() - Util.HEIGHT, 600,
				600));
		/*
		 * view.setLayoutX(0); view.setLayoutY(-getPHeight() + Util.HEIGHT);
		 */
	}

	public synchronized void setViewport(Rectangle2D a) {
		view.setViewport(a);
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

	@Override
	public synchronized void update() {
		setViewport(new Rectangle2D(-Camera.camera.getOffsetX(),
				GameWorld.world.getCurrentMap().getPHeight() - Util.HEIGHT
						- Camera.camera.getOffsetY(), 600, 600));
	}
}
