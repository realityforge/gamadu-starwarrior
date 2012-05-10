package com.gamadu.starwarrior.systems;

import java.util.Random;

import org.newdawn.slick.GameContainer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Enemy;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;
import com.gamadu.starwarrior.components.Weapon;

public class EnemySpawnSystem extends IntervalEntitySystem {

	private ComponentMapper<Weapon> weaponMapper;
	private long now;
	private ComponentMapper<Transform> transformMapper;
	private GameContainer container;
	private Random r;

	public EnemySpawnSystem(int interval, GameContainer container) {
		super(interval, Transform.class, Weapon.class, Enemy.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		weaponMapper = new ComponentMapper<Weapon>(Weapon.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		
		r = new Random();
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity e = EntityFactory.createEnemyShip(world);
		
		e.getComponent(Transform.class).setLocation(r.nextInt(container.getWidth()), r.nextInt(400)+50);
		e.getComponent(Velocity.class).setVelocity(0.05f);
		e.getComponent(Velocity.class).setAngle(r.nextBoolean() ? 0 : 180);
		
		e.refresh();
	}
	
}
