package guiobject;

import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TimePopup extends EdwardPopup{

	public TimePopup(String string) {
		super(string);
		rectangle.setLayoutX(0);
		rectangle.setLayoutY(0);
		this.height = 300;
		text.setTextAlignment(TextAlignment.CENTER);
		text.setTextOrigin(VPos.CENTER);
		text.setFont(Font.font("Georgia", 60));
	}

}
