package com.gamadu.starwarrior.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.TrigLUT;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;

public class MovementSystem extends EntityProcessingSystem {
	private GameContainer container;
	private Graphics g;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Transform> transformMapper;

	public MovementSystem(GameContainer container) {
		super(Aspect.getAspectFor(Transform.class, Velocity.class));
		this.container = container;
	}

	@Override
	public void initialize() {
		velocityMapper = world.getMapper(Velocity.class);
		transformMapper = world.getMapper(Transform.class);
	}

	@Override
	protected void process(Entity e) {
		Velocity velocity = velocityMapper.get(e);
		float v = velocity.getVelocity();

		Transform transform = transformMapper.get(e);

		float r = velocity.getAngleAsRadians();

		float xn = transform.getX() + (TrigLUT.cos(r) * v * world.getDelta());
		float yn = transform.getY() + (TrigLUT.sin(r) * v * world.getDelta());

		transform.setLocation(xn, yn);
	}

}
