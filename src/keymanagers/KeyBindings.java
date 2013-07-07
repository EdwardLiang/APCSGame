package keymanagers;

import javafx.scene.input.KeyCode;

public enum KeyBindings {
	JUMP(KeyCode.SPACE), UP(KeyCode.UP), DOWN(KeyCode.DOWN), LEFT(KeyCode.LEFT), RIGHT(
			KeyCode.RIGHT), RESET(KeyCode.R), REVERSE(KeyCode.SHIFT);

	KeyCode value;

	private KeyBindings(KeyCode code) {
		this.value = code;
	}
}
