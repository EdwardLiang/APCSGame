package entities;



import infrastructure.App;
import javafx.beans.property.StringProperty;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

//public class EdwardPopup {
//	protected Rectangle rect;
//	protected double xPPos, yPPos, width, height;
//	protected String message;
//	protected Stage s;
//	protected Text t;
//	protected boolean isOn;
//
//	public EdwardPopup(Stage s, String message) {
//		this.s = s;
//		this.message = message;
//		this.xPPos = 50;
//		this.yPPos = 150;
//		this.width = 500;
//		this.height = 300;
//		isOn = false;
//		rect = create();
//		t = new Text(xPPos + 10, xPPos, message);
//		t.wrappingWidthProperty().bind(rect.widthProperty().multiply(0.9));
//		t.xProperty().bind(rect.xProperty().add(rect.widthProperty().doubleValue()-(t.boundsInLocalProperty().getValue().getWidth()/2)));
//		t.yProperty().bind(rect.yProperty().add(rect.heightProperty().divide(2)));
//		t.setTextAlignment(TextAlignment.CENTER);
//		t.setTextOrigin(VPos.CENTER);
//	}
//
//	
//
//	public Rectangle create() {
//		Rectangle r = new Rectangle(width, height, Color.WHITESMOKE);
//		r.setWidth(width);
//		r.setHeight(height);
//		r.setLayoutX(xPPos);
//		r.setLayoutY(yPPos);
//
//		return r;
//	}
//
//}
public class EdwardPopup {
	public enum Box {
		YES, NO;
	}
	private Text text;
	private Rectangle rectangle,clip;
	private double xPPos,yPPos,width,height;
	private boolean isOn = false;
	

	public StringProperty textProperty() {
		return text.textProperty();
	}

	public EdwardPopup(String string) {
		this.width = App.scene.getWidth();
		this.height = 300;
		this.text = new Text(string);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setTextOrigin(VPos.CENTER);

		this.rectangle = new Rectangle(width, height);
//		rectangle.setLayoutX(xPPos);
//		rectangle.setLayoutY(yPPos);
		rectangle.setFill(Color.WHITESMOKE);

		this.clip = new Rectangle(width, height);
		text.setClip(clip);
		create();

	}

	public void toggle() {
		if (isOn && App.root.getChildren().contains(text)
				&& App.root.getChildren().contains(rectangle)) {
			App.root.getChildren().remove(rectangle);
			App.root.getChildren().remove(text);
		} else if (!isOn && !App.root.getChildren().contains(text)
				&& !App.root.getChildren().contains(rectangle)) {
			App.root.getChildren().add(rectangle);
			App.root.getChildren().add(text);
		}
		isOn = !isOn;
	}
		
	protected void create() {
		final double w = rectangle.getWidth();
		final double h = rectangle.getHeight();
		clip.setWidth(w);
		clip.setHeight(h);
		clip.setLayoutX(0);
		clip.setLayoutY(-h / 2);

		text.setWrappingWidth(w * 0.9);
		text.setLayoutX(w / 2 - text.getLayoutBounds().getWidth() / 2);
		text.setLayoutY(h / 2);
	}
}
