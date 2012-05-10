package com.gamadu.starwarrior.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Enemy;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;
import com.gamadu.starwarrior.components.Weapon;

public class EnemyShooterSystem extends EntityProcessingSystem {

	private ComponentMapper<Weapon> weaponMapper;
	private long now;
	private ComponentMapper<Transform> transformMapper;

	public EnemyShooterSystem() {
		super(Transform.class, Weapon.class, Enemy.class);
	}

	@Override
	public void initialize() {
		weaponMapper = new ComponentMapper<Weapon>(Weapon.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
	}

	@Override
	protected void begin() {
		now = System.currentTimeMillis();
	}

	@Override
	protected void process(Entity e) {
		Weapon weapon = weaponMapper.get(e);

		if (weapon.getShotAt() + 2000 < now) {
			Transform transform = transformMapper.get(e);

			Entity missile = EntityFactory.createMissile(world);
			missile.getComponent(Transform.class).setLocation(transform.getX(), transform.getY() + 20);
			missile.getComponent(Velocity.class).setVelocity(-0.5f);
			missile.getComponent(Velocity.class).setAngle(270);
			missile.refresh();

			weapon.setShotAt(now);
		}

	}
}
