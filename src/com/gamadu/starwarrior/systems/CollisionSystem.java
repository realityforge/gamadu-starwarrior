package com.gamadu.starwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.managers.GroupManager;
import com.artemis.utils.ImmutableBag;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.Transform;

public class CollisionSystem extends EntitySystem {
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Health> healthMapper;
	private ImmutableBag<Entity> bullets;
	private ImmutableBag<Entity> ships;

	public CollisionSystem() {
		super(Aspect.getAspectFor(Transform.class));
	}

	@Override
	public void initialize() {
		transformMapper = world.getMapper(Transform.class);
		healthMapper = world.getMapper(Health.class);

		bullets = world.getManager(GroupManager.class).getEntities("BULLETS");
		ships = world.getManager(GroupManager.class).getEntities("SHIPS");
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		if(bullets != null && ships != null) {
			shipLoop: for(int a = 0; ships.size() > a; a++) {
				Entity ship = ships.get(a);
				for(int b = 0; bullets.size() > b; b++) {
					Entity bullet = bullets.get(b);
					
					if(collisionExists(bullet, ship)) {
						Transform tb = transformMapper.get(bullet);
						EntityFactory.createBulletExplosion(world, tb.getX(), tb.getY()).addToWorld();
						world.deleteEntity(bullet);
						
						Health health = healthMapper.get(ship);
						health.addDamage(4);
	
						
						if(!health.isAlive()) {
							Transform ts = transformMapper.get(ship);
	
							EntityFactory.createShipExplosion(world, ts.getX(), ts.getY()).addToWorld();
	
							world.deleteEntity(ship);
							continue shipLoop;
						}
					}
				}
			}
		}
	}

	private boolean collisionExists(Entity e1, Entity e2) {
		Transform t1 = transformMapper.get(e1);
		Transform t2 = transformMapper.get(e2);
		return t1.getDistanceTo(t2) < 15;
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
