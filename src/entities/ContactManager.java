package entities;

import infrastructure.App;

import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactManager implements ContactListener {
	private ArrayList<Class<?>> deadly = new ArrayList<Class<?>>();

	public ContactManager() {
		super();
		deadly.add(DeadlyDynamicPathEntity.class);
		deadly.add(DeadlyStaticPathEntity.class);
		deadly.add(Projectile.class);
		deadly.add(DeadlyBouncyBall.class);
	}

	public void beginContact(Contact contact) {
		if (contact.getFixtureA().getBody().getUserData().equals(Player.class)) {
			for (Class<?> a : deadly) {
				if (contact.getFixtureB().getBody().getUserData().equals(a)) {
					if (((Player) App.game.getPlayer()).getStatus() != Player.Status.DEAD){
						((Player) App.game.getPlayer()).kill();
					}
				}
			}
		} else if (contact.getFixtureB().getBody().getUserData()
				.equals(Player.class)) {
			for (Class<?> a : deadly) {
				if (contact.getFixtureA().getBody().getUserData().equals(a)) {
					if (((Player) App.game.getPlayer()).getStatus() != Player.Status.DEAD)
						if (((Player) App.game.getPlayer()).getStatus() != Player.Status.DEAD){
							((Player) App.game.getPlayer()).kill();
						}
				}
			}
		}

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
}
