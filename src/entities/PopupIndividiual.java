package entities;

import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupIndividiual extends PopupText {
	public enum Box {
		YES, NO;
	}

	private Box box;

	public PopupIndividiual(String message, Stage stage, boolean haveBox) {
		this.xPPos = 700;
		this.yPPos = 350;
		this.message = message;
		this.width = 500;
		this.height = 200;
		this.stage = stage;
		window = new Popup();
		if (haveBox) {
			r = create();
			this.box = Box.YES;
		} else
			this.box = Box.NO;
		switch (box) {
		case YES:
			window.getContent().addAll(r, new Text(10,20,message));
			break;
		case NO:
			window.getContent().addAll(r);
		} 
	}

}
