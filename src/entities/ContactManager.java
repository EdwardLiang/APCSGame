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
	private ArrayList<Class<?>> deadly = new ArrayList<Class<?>>();

	public ContactManager() {
		super();
		deadly.add(DeadlyDynamicPathEntity.class);
		deadly.add(DeadlyStaticPathEntity.class);
		deadly.add(Projectile.class);
		deadly.add(DeadlyBouncyBall.class);
	}

	@Override
	public void beginContact(Contact contact) {
		if (contact.getFixtureA().getBody().getUserData().equals(Player.class)) {
			for (Class<?> a : deadly) {
				if (contact.getFixtureB().getBody().getUserData().equals(a)) {
					if (((Player) GameWorld.world.getPlayer()).getStatus() != Player.Status.DEAD) {
						((Player) GameWorld.world.getPlayer()).kill();
						((Player) GameWorld.world.getPlayer()).getBody()
								.setLinearVelocity(new Vec2(0, 0));
					}
				}
			}
		} else if (contact.getFixtureB().getBody().getUserData()
				.equals(Player.class)) {
			for (Class<?> a : deadly) {
				if (contact.getFixtureA().getBody().getUserData().equals(a)) {
					if (((Player) GameWorld.world.getPlayer()).getStatus() != Player.Status.DEAD)
						if (((Player) GameWorld.world.getPlayer()).getStatus() != Player.Status.DEAD) {
							((Player) GameWorld.world.getPlayer()).kill();
							((Player) GameWorld.world.getPlayer()).getBody()
									.setLinearVelocity(new Vec2(0, 0));

						}
				}
			}
		}
		if (contact.getFixtureA().getBody().getUserData().equals(Player.class)
				&& contact.getFixtureB().getBody().getUserData()
						.equals(Door.class)) {
			GameWorld.world.isAtDoor(true);
		} else if (contact.getFixtureB().getBody().getUserData()
				.equals(Player.class)
				&& contact.getFixtureA().getBody().getUserData()
						.equals(Door.class)) {
			GameWorld.world.isAtDoor(true);
		}
	}

	@Override
	public void endContact(Contact contact) {
		if (contact.getFixtureA().getBody().getUserData().equals(Player.class)
				&& contact.getFixtureB().getBody().getUserData()
						.equals(Door.class)) {
			GameWorld.world.isAtDoor(false);
		} else if (contact.getFixtureB().getBody().getUserData()
				.equals(Player.class)
				&& contact.getFixtureA().getBody().getUserData()
						.equals(Door.class)) {
			GameWorld.world.isAtDoor(false);
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
