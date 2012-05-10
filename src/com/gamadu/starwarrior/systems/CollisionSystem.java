package com.gamadu.starwarrior.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;

public class CollisionSystem extends EntitySystem {
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Health> healthMapper;

	public CollisionSystem() {
		super(Transform.class);
	}

	@Override
	public void initialize() {
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world);
		healthMapper = new ComponentMapper<Health>(Health.class, world);
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		ImmutableBag<Entity> bullets = world.getGroupManager().getEntities("BULLETS");
		ImmutableBag<Entity> ships = world.getGroupManager().getEntities("SHIPS");
		
		if(bullets != null && ships != null) {
			shipLoop: for(int a = 0; ships.size() > a; a++) {
				Entity ship = ships.get(a);
				for(int b = 0; bullets.size() > b; b++) {
					Entity bullet = bullets.get(b);
					
					if(collisionExists(bullet, ship)) {
						Transform tb = transformMapper.get(bullet);
						EntityFactory.createBulletExplosion(world, tb.getX(), tb.getY()).refresh();
						world.deleteEntity(bullet);
						
						Health health = healthMapper.get(ship);
						health.addDamage(4);
	
						
						if(!health.isAlive()) {
							Transform ts = transformMapper.get(ship);
	
							EntityFactory.createShipExplosion(world, ts.getX(), ts.getY()).refresh();
	
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
