package com.gamadu.starwarrior.systems;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.Player;

public class HudRenderSystem extends EntityProcessingSystem {
	private GameContainer container;
	private Graphics g;
	private ComponentMapper<Health> healthMapper;

	public HudRenderSystem(GameContainer container) {
		super(Aspect.getAspectFor(Health.class, Player.class));
		this.container = container;
		this.g = container.getGraphics();
	}

	@Override
	public void initialize() {
		healthMapper = world.getMapper(Health.class);
	}

	@Override
	protected void process(Entity e) {
		Health health = healthMapper.get(e);
		g.setColor(Color.white);
		g.drawString("Health: " + health.getHealthPercentage() + "%", 20, container.getHeight() - 40);
	}

}
