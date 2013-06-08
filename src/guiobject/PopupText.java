package guiobject;

import utils.Util;
import infrastructure.App;
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
	protected String message;
	protected int width;
	protected int height;
	protected Stage stage;
	protected Popup window;
	protected Boolean isVisible;
	protected Rectangle r;

	
	public PopupText(String message, Stage stage)
	{
		this.xPPos = 700;
		this.yPPos = 350;
		this.message = message;
		this.width = 500;
		this.height = 200;
		this.stage = stage;
		isVisible = false;
		window = new Popup();
		r = create();
		window.getContent().addAll(r, new Text(10,20,message));
		window.setX(xPPos);
		window.setY(yPPos);
	}
	public PopupText()
	{
		this.xPPos = 0;
		this.yPPos = 0;
		this.message = null;
		this.width = 0;
		this.height = 0;
		this.stage = null;
		isVisible = false;
		window = new Popup();
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

	protected Rectangle create() {
		Rectangle r = new Rectangle(width, height, Color.WHITESMOKE);
		r.setWidth(width);
		r.setHeight(height);
		r.setLayoutX(0);
		r.setLayoutY(0);

		return r;
	}
}
