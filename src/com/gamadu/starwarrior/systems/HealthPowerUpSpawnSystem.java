package com.gamadu.starwarrior.systems;

import java.util.Random;

import org.newdawn.slick.GameContainer;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gamadu.starwarrior.EntityFactory;

public class HealthPowerUpSpawnSystem extends IntervalEntitySystem {

	private GameContainer container;
	private Random r;

	public HealthPowerUpSpawnSystem(int interval, GameContainer container) {
		super(Aspect.getEmpty(), interval);
		this.container = container;
	}

	@Override
	public void initialize() {
		r = new Random();
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		EntityFactory.createHealthPowerUp(world, r.nextInt(container.getWidth()), -50, 0, 0.05f).addToWorld();
	}
	
}
