package com.gamadu.starwarrior.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.gamadu.starwarrior.components.SpatialForm;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.spatials.EnemyShip;
import com.gamadu.starwarrior.spatials.Explosion;
import com.gamadu.starwarrior.spatials.Missile;
import com.gamadu.starwarrior.spatials.PlayerShip;
import com.gamadu.starwarrior.spatials.Spatial;

public class RenderSystem extends EntityProcessingSystem {
	private Graphics graphics;
	private Bag<Spatial> spatials;
	private ComponentMapper<SpatialForm> spatialFormMapper;
	private ComponentMapper<Transform> transformMapper;
	private GameContainer container;

	public RenderSystem(GameContainer container) {
		super(Aspect.getAspectFor(Transform.class, SpatialForm.class));
		this.container = container;
		this.graphics = container.getGraphics();

		spatials = new Bag<Spatial>();
	}

	@Override
	public void initialize() {
		spatialFormMapper = world.getMapper(SpatialForm.class);
		transformMapper = world.getMapper(Transform.class);
	}

	@Override
	protected void process(Entity e) {
		Spatial spatial = spatials.get(e.getId());
		Transform transform = transformMapper.get(e);

		if (transform.getX() >= 0 && transform.getY() >= 0 && transform.getX() < container.getWidth() && transform.getY() < container.getHeight() && spatial != null) {
			spatial.render(graphics);
		}
	}
	
	@Override
	protected void end() {
		graphics.drawString("Active entities: " + world.getEntityManager().getActiveEntityCount(), 10, 25);
		graphics.drawString("Total added to world since start: " + world.getEntityManager().getTotalAdded(), 10, 40);
		graphics.drawString("Total created in world since start: " + world.getEntityManager().getTotalCreated(), 10, 55);
		graphics.drawString("Total deleted from world since start: " + world.getEntityManager().getTotalDeleted(), 10, 70);
	}

	@Override
	protected void added(Entity e) {
		Spatial spatial = createSpatial(e);
		if (spatial != null) {
			spatial.initalize();
			spatials.set(e.getId(), spatial);
		}
	}

	@Override
	protected void removed(Entity e) {
		spatials.set(e.getId(), null);
	}

	private Spatial createSpatial(Entity e) {
		SpatialForm spatialForm = spatialFormMapper.get(e);
		String spatialFormFile = spatialForm.getSpatialFormFile();

		if ("PlayerShip".equalsIgnoreCase(spatialFormFile)) {
			return new PlayerShip(world, e);
		} else if ("Missile".equalsIgnoreCase(spatialFormFile)) {
			return new Missile(world, e);
		} else if ("EnemyShip".equalsIgnoreCase(spatialFormFile)) {
			return new EnemyShip(world, e);
		} else if ("BulletExplosion".equalsIgnoreCase(spatialFormFile)) {
			return new Explosion(world, e, 10);
		} else if ("ShipExplosion".equalsIgnoreCase(spatialFormFile)) {
			return new Explosion(world, e, 30);
		}

		return null;
	}

}
