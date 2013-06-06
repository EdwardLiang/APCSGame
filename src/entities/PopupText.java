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
	private Text text;

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
		text = new Text(xPPos,yPPos, message);
		r = create();
		window.getContent().addAll(text);

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
