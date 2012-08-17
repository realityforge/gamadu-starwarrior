package com.gamadu.starwarrior;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.SpatialForm;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;
import com.gamadu.starwarrior.systems.CheckOutOfBoundsSystem;
import com.gamadu.starwarrior.systems.CollisionSystem;
import com.gamadu.starwarrior.systems.EnemyShooterSystem;
import com.gamadu.starwarrior.systems.EnemySpawnSystem;
import com.gamadu.starwarrior.systems.ExpirationSystem;
import com.gamadu.starwarrior.systems.EnemyHealthBarRenderSystem;
import com.gamadu.starwarrior.systems.HealthPowerUpSpawnSystem;
import com.gamadu.starwarrior.systems.HudRenderSystem;
import com.gamadu.starwarrior.systems.MovementSystem;
import com.gamadu.starwarrior.systems.PlayerShipControlSystem;
import com.gamadu.starwarrior.systems.RenderSystem;

public class StarWarrior extends BasicGame {

	private World world;
	private GameContainer container;

	private EntitySystem renderSystem;
	private EntitySystem hudRenderSystem;
	private EntitySystem healthBarRenderSystem;

	public StarWarrior() {
		super("Star Warrior");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;

		world = new World();
		world.setManager(new GroupManager());
		world.setManager(new TagManager());

		world.setSystem(new MovementSystem(container));
		world.setSystem(new PlayerShipControlSystem(container));
		world.setSystem(new CheckOutOfBoundsSystem(container));
		world.setSystem(new EnemyShooterSystem());
		world.setSystem(new CollisionSystem());
		world.setSystem(new EnemySpawnSystem(750, container));
		world.setSystem(new ExpirationSystem());
		world.setSystem(new HealthPowerUpSpawnSystem(10000, container));

		renderSystem = world.setSystem(new RenderSystem(container), true);
		hudRenderSystem = world.setSystem(new HudRenderSystem(container), true);
		healthBarRenderSystem = world.setSystem(new EnemyHealthBarRenderSystem(container), true);

		world.initialize();

		initPlayerShip();
		initEnemyShips();

	}

	private void initEnemyShips() {
		Random r = new Random();
		for (int i = 0; 10 > i; i++) {
			EntityFactory.createEnemyShip(world, r.nextInt(container.getWidth()),-50, 0f, 0.05f).addToWorld();
		}
	}

	private void initPlayerShip() {
		Entity e = world.createEntity();
		e.addComponent(new Transform(container.getWidth() / 2, container.getHeight() - 50));
		e.addComponent(new SpatialForm("PlayerShip"));
		e.addComponent(new Velocity());
		e.addComponent(new Health(30));
		
		world.getManager(GroupManager.class).add(e,"PLAYER_SHIP");
		world.getManager(TagManager.class).register("PLAYER", e);
		
		world.addEntity(e);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.setDelta(delta);
		world.process();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		renderSystem.process();
		healthBarRenderSystem.process();
		hudRenderSystem.process();
	}

	public static void main(String[] args) throws SlickException {
		StarWarrior game = new StarWarrior();
		AppGameContainer container = new AppGameContainer(game);
		container.setDisplayMode(1024, 768, false);
		container.setAlwaysRender(true);
		// container.setMinimumLogicUpdateInterval(1);
		// container.setMaximumLogicUpdateInterval(1);
		// container.setTargetFrameRate(60);
		container.start();
	}
}
