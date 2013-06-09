package guiobject;



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
		this.height = 1100;
		this.text = new Text(string);
		text.setTextAlignment(TextAlignment.LEFT);
		text.setTextOrigin(VPos.BOTTOM);

		this.rectangle = new Rectangle(width, height);
		rectangle.setLayoutX(100);
		rectangle.setLayoutY(100);
		rectangle.setFill(Color.TRANSPARENT);
		text.setFont(Font.font("Georgia", 20));
		text.setFill(Color.WHITESMOKE);

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
