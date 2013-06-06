package entities;

import infrastructure.App;
import infrastructure.Util;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class PopupText {
	protected double xPPos;
	protected double yPPos;
	public Node node;
	private String message;
	private int width;
	private int height;
	private Stage stage;
	private Popup window;
	private Boolean isVisible;
	private Rectangle r;

	public PopupText(double xPPos, double yPPos, int width, int height,
			String message, Stage stage) {
		this.xPPos = xPPos;
		this.yPPos = yPPos;
		this.message = message;
		this.width = width;
		this.height = height;
		this.stage = stage;
		isVisible = false;
		window = new Popup();
		window.setX(xPPos);
		window.setY(yPPos);
		r = create();
		window.getContent().addAll(r, new Text(xPPos-width/2,yPPos, message));

	}
	public PopupText(String message, Stage stage)
	{
		this.xPPos = 700;
		this.yPPos = 200;
		this.message = message;
		this.width = 500;
		this.height = 200;
		this.stage = stage;
		isVisible = false;
		window = new Popup();
		window.setX(xPPos);
		window.setY(yPPos);
		r = create();
		window.getContent().addAll(r, new Text(xPPos-width/2,yPPos-width/2, message));
	}

	public void toggle() {
		if (isVisible) {
			isVisible = false;
			window.hide();
		} else {
			isVisible = true;
			window.show(stage);
		}
	}

	private Rectangle create() {
		Rectangle r = new Rectangle(xPPos, yPPos, Color.WHITESMOKE);
		//r.setX(xPPos);
		//r.setY(yPPos);
		r.setWidth(width);
		r.setHeight(height);
		r.setLayoutX(xPPos - width / 2);
		r.setLayoutY(yPPos - height /2);

		return r;
	}
}
