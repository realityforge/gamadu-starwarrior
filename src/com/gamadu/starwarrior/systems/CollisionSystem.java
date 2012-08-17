package com.gamadu.starwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.Manager;
import com.artemis.managers.GroupManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.Transform;

public class CollisionSystem extends EntitySystem {
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Health> healthMapper;
	private ImmutableBag<Entity> bullets;
	private ImmutableBag<Entity> ships;
	private Bag<CollisionGroup> collisionGroups;

	public CollisionSystem() {
		super(Aspect.getAspectFor(Transform.class));
	}

	@Override
	public void initialize() {
		transformMapper = world.getMapper(Transform.class);
		healthMapper = world.getMapper(Health.class);
		
		collisionGroups = new Bag<CollisionGroup>();
		
		collisionGroups.add(new CollisionGroup("PLAYER_BULLETS", "ENEMY_SHIPS", new CollisionHandler() {
			@Override
			public void handleCollision(Entity bullet, Entity ship) {
				Transform tb = transformMapper.get(bullet);
				EntityFactory.createBulletExplosion(world, tb.getX(), tb.getY()).addToWorld();
				world.deleteEntity(bullet);
				
				Health health = healthMapper.get(ship);
				health.addDamage(5);

				
				if(!health.isAlive()) {
					Transform ts = transformMapper.get(ship);

					EntityFactory.createShipExplosion(world, ts.getX(), ts.getY()).addToWorld();

					world.deleteEntity(ship);
				}
			}
		}));

		collisionGroups.add(new CollisionGroup("ENEMY_BULLETS", "PLAYER_SHIP", new CollisionHandler() {
			@Override
			public void handleCollision(Entity bullet, Entity ship) {
				Transform tb = transformMapper.get(bullet);
				EntityFactory.createBulletExplosion(world, tb.getX(), tb.getY()).addToWorld();
				world.deleteEntity(bullet);
				
				Health health = healthMapper.get(ship);
				health.addDamage(1);

				
				if(!health.isAlive()) {
					Transform ts = transformMapper.get(ship);

					EntityFactory.createShipExplosion(world, ts.getX(), ts.getY()).addToWorld();

					world.deleteEntity(ship);
				}
			}
		}));
		
		collisionGroups.add(new CollisionGroup("ENEMY_SHIPS", "PLAYER_SHIP", new CollisionHandler() {
			@Override
			public void handleCollision(Entity enemy, Entity player) {
				Transform tb = transformMapper.get(enemy);
				EntityFactory.createShipExplosion(world, tb.getX(), tb.getY()).addToWorld();
				world.deleteEntity(enemy);
				
				Health health = healthMapper.get(player);
				health.addDamage(2);

				
				if(!health.isAlive()) {
					Transform ts = transformMapper.get(player);

					EntityFactory.createShipExplosion(world, ts.getX(), ts.getY()).addToWorld();

					world.deleteEntity(player);
				}
			}
		}));
		
		collisionGroups.add(new CollisionGroup("HEALTH_POWERUPS", "PLAYER_SHIP", new CollisionHandler() {
			@Override
			public void handleCollision(Entity powerup, Entity ship) {
				Health health = healthMapper.get(ship);
				health.addHealth(5);
				powerup.deleteFromWorld();
			}
		}));
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		for(int i = 0; collisionGroups.size() > i; i++) {
			collisionGroups.get(i).checkForCollisions();
		}
	}

	private boolean collisionExists(Entity e1, Entity e2) {
		Transform t1 = transformMapper.get(e1);
		Transform t2 = transformMapper.get(e2);
		return t1.getDistanceTo(t2) < 10;
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	
	private class CollisionGroup {
		private ImmutableBag<Entity> groupEntitiesA;
		private ImmutableBag<Entity> groupEntitiesB;
		private CollisionHandler handler;

		public CollisionGroup(String group1, String group2, CollisionHandler handler) {
			groupEntitiesA = world.getManager(GroupManager.class).getEntities(group1);
			groupEntitiesB = world.getManager(GroupManager.class).getEntities(group2);
			this.handler = handler;
		}

		public void checkForCollisions() {
			for(int a = 0; groupEntitiesA.size() > a; a++) {
				for(int b = 0; groupEntitiesB.size() > b; b++) {
					Entity entityA = groupEntitiesA.get(a);
					Entity entityB = groupEntitiesB.get(b);
					if(collisionExists(entityA, entityB)) {
						handler.handleCollision(entityA, entityB);
					}
				}
			}
			
		}
	}
	
	private interface CollisionHandler {
		void handleCollision(Entity a, Entity b);
	}

}
