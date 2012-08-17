package com.gamadu.starwarrior.systems;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
import com.gamadu.starwarrior.components.Health;

public class HudRenderSystem extends EntitySystem {
	private GameContainer container;
	private Graphics g;
	private ComponentMapper<Health> healthMapper;
	private Entity player;

	public HudRenderSystem(GameContainer container) {
		super(Aspect.getEmpty());
		this.container = container;
		this.g = container.getGraphics();
	}

	@Override
	public void initialize() {
		healthMapper = world.getMapper(Health.class);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		player = world.getManager(TagManager.class).getEntity("PLAYER");
		if(player != null && player.isEnabled()) {
			Health health = healthMapper.get(player);
			
			g.setColor(Color.darkGray);
			g.fillRect(container.getWidth()/2-100, container.getHeight()-20, 200, 5);
			
			g.setColor(Color.green);
			int h = Math.round(health.getHealthFactor()*200);
			g.fillRect(container.getWidth()/2-(h/2), container.getHeight()-20, h, 5);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
}
