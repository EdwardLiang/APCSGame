package inputManagers;

import infrastructure.App;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import entities.Player;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class KeyManager {
	public Set<KeyCode> buffer = EnumSet.noneOf(KeyCode.class);
	protected Player p =(Player) App.game.getPlayer();
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.add(t.getCode());
			t.consume();
			if(t.getCode() == KeyCode.R)
				try {
					App.game.getCurrentMap().reset();
					System.out.println("R Hit");
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (t.getCode() == KeyCode.P) {
				App.game.getCurrentMap().toggleTime();
			}
			if(t.getCode() == KeyCode.N){
				if(App.game.getIsAtDoor()){
					int index = App.game.getMaps().indexOf(App.game.getCurrentMap());
					if(index + 1 < App.game.getMaps().size())
						App.game.changeMap(App.game.getMaps().get(index+1));
				}
			}
		}
	};
	public EventHandler<KeyEvent> keyRelease = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.remove(t.getCode());
			t.consume();
		}
	};

	public Runnable keyThread = new Runnable() {
		@Override
		public void run() {

			// TODO Auto-generated method stub
		}

	};
}
