package com.gamadu.starwarrior.systems;

import java.util.Random;

import org.newdawn.slick.GameContainer;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntitySystem;
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
		super(Aspect.getAspectFor(Transform.class, Weapon.class, Enemy.class), interval);
		this.container = container;
	}

	@Override
	public void initialize() {
		weaponMapper = world.getMapper(Weapon.class);
		transformMapper = world.getMapper(Transform.class);
		
		r = new Random();
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity e = EntityFactory.createEnemyShip(world);
		
		e.getComponent(Transform.class).setLocation(r.nextInt(container.getWidth()), r.nextInt(400)+50);
		e.getComponent(Velocity.class).setVx(0.05f);
		
		e.addToWorld();
	}
	
}
