package com.gamadu.starwarrior.systems;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.Player;

public class HudRenderSystem extends EntityProcessingSystem {
	private GameContainer container;
	private Graphics g;
	private ComponentMapper<Health> healthMapper;

	public HudRenderSystem(GameContainer container) {
		super(Health.class, Player.class);
		this.container = container;
		this.g = container.getGraphics();
	}

	@Override
	public void initialize() {
		healthMapper = new ComponentMapper<Health>(Health.class, world);
	}

	@Override
	protected void process(Entity e) {
		Health health = healthMapper.get(e);
		g.setColor(Color.white);
		g.drawString("Health: " + health.getHealthPercentage() + "%", 20, container.getHeight() - 40);
	}

}
