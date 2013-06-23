package entities;

import infrastructure.App;
import infrastructure.GameWorld;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactManager implements ContactListener {
	public ContactManager() {
		super();
	}

	@Override
	public void beginContact(Contact contact) {
		Entity a = (Entity) contact.getFixtureA().getBody().getUserData();
		Entity b = (Entity) contact.getFixtureB().getBody().getUserData();
		if (a instanceof Player) {
			if (b.getIsDeadly()) {
				((Player) a).kill();
			}
			if (b instanceof Door) {
				GameWorld.world.isAtDoor(true);
			}
		} else if (b instanceof Player) {
			if (a.getIsDeadly()) {
				((Player) b).kill();
			}
			if (a instanceof Door) {
				GameWorld.world.isAtDoor(true);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Entity a = (Entity) contact.getFixtureA().getBody().getUserData();
		Entity b = (Entity) contact.getFixtureB().getBody().getUserData();
		if (a instanceof Player) {
			if (b instanceof Door) {
				GameWorld.world.isAtDoor(false);
			}
		} else if (b instanceof Player) {
			if (a instanceof Door) {
				GameWorld.world.isAtDoor(false);
			}
		}
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
