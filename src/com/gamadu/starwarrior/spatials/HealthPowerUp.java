package com.gamadu.starwarrior.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.gamadu.starwarrior.components.Transform;

public class HealthPowerUp extends Spatial {

	private Transform transform;

	public HealthPowerUp(World world, Entity owner) {
		super(world, owner);
	}

	@Override
	public void initalize() {
		ComponentMapper<Transform> transformMapper = world.getMapper(Transform.class);
		transform = transformMapper.get(owner);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(transform.getX()-10, transform.getY()-10, 20, 20);
		
		g.setColor(Color.red);
		g.fillRect(transform.getX()-8, transform.getY()-2, 16, 4);
		g.fillRect(transform.getX()-2, transform.getY()-8, 4, 16);
	}

}
