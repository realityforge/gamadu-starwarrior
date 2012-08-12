package com.gamadu.starwarrior.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.gamadu.starwarrior.components.Expires;

public class ExpirationSystem extends EntityProcessingSystem {

	private ComponentMapper<Expires> expiresMapper;

	public ExpirationSystem() {
		super(Aspect.getAspectFor(Expires.class));
	}

	@Override
	public void initialize() {
		expiresMapper = world.getMapper(Expires.class);
	}

	@Override
	protected void process(Entity e) {
		Expires expires = expiresMapper.get(e);
		expires.reduceLifeTime(world.getDelta());

		if (expires.isExpired()) {
			world.deleteEntity(e);
		}

	}
}
