package com.gamadu.starwarrior.systems;

import org.newdawn.slick.GameContainer;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;

public class CheckOutOfBoundsSystem extends EntityProcessingSystem {
	private GameContainer container;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;

	public CheckOutOfBoundsSystem(GameContainer container) {
		super(Aspect.getAspectFor(Transform.class, Velocity.class));

		this.container = container;
	}

	@Override
	public void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
	}

	@Override
	protected void process(Entity e) {
		Transform transform = transformMapper.get(e);
		Velocity velocity = velocityMapper.get(e);

		if (transform.getX() > container.getWidth() || transform.getX() < 0) {
			velocity.setVx(-velocity.getVx());
		}
	}

}
