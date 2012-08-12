package com.gamadu.starwarrior.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.gamadu.starwarrior.components.Transform;

public class EnemyShip extends Spatial {

	private Transform transform;
	private Polygon ship;

	public EnemyShip(World world, Entity owner) {
		super(world, owner);
	}

	@Override
	public void initalize() {
		ComponentMapper<Transform> transformMapper = world.getMapper(Transform.class);
		transform = transformMapper.get(owner);

		ship = new Polygon();
		ship.addPoint(-10, -10);
		ship.addPoint(10, -10);
		ship.addPoint(0, 10);
		ship.setClosed(true);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.setAntiAlias(true);
		ship.setLocation(transform.getX(), transform.getY());
		g.fill(ship);
	}

}
