package com.gamadu.starwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Enemy;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;
import com.gamadu.starwarrior.components.Weapon;

public class EnemyDisableEnableSystem extends EntityProcessingSystem {

	private ComponentMapper<Weapon> weaponMapper;
	private long now;
	private ComponentMapper<Transform> transformMapper;

	public EnemyDisableEnableSystem() {
		super(Aspect.getAspectFor(Transform.class, Weapon.class, Enemy.class));
	}

	@Override
	public void initialize() {
		weaponMapper = world.getMapper(Weapon.class);
		transformMapper = world.getMapper(Transform.class);
	}

	@Override
	protected void begin() {
		now = System.currentTimeMillis();
	}

	@Override
	protected void process(Entity e) {
		process(e, weaponMapper.get(e), transformMapper.get(e));
	}

	private void process(Entity e, Weapon weapon, Transform transform) {
		if (weapon.getShotAt() + 2000 < now) {
			EntityFactory.createEnemyMissile(world, transform.getX(), transform.getY() + 20).addToWorld();
			weapon.setShotAt(now);
		}
	}
}
