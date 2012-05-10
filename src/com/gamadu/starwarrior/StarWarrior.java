package com.gamadu.starwarrior;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.Player;
import com.gamadu.starwarrior.components.SpatialForm;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;
import com.gamadu.starwarrior.systems.CollisionSystem;
import com.gamadu.starwarrior.systems.EnemyShipMovementSystem;
import com.gamadu.starwarrior.systems.EnemyShooterSystem;
import com.gamadu.starwarrior.systems.EnemySpawnSystem;
import com.gamadu.starwarrior.systems.ExpirationSystem;
import com.gamadu.starwarrior.systems.HealthBarRenderSystem;
import com.gamadu.starwarrior.systems.HudRenderSystem;
import com.gamadu.starwarrior.systems.MovementSystem;
import com.gamadu.starwarrior.systems.PlayerShipControlSystem;
import com.gamadu.starwarrior.systems.RenderSystem;

public class StarWarrior extends BasicGame {

	private World world;
	private GameContainer container;
	
	private EntitySystem renderSystem;
	private EntitySystem hudRenderSystem;
	private EntitySystem controlSystem;
	private EntitySystem movementSystem;
	private EntitySystem enemyShooterSystem;
	private EntitySystem enemyShipMovementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem healthBarRenderSystem;
	private EntitySystem enemySpawnSystem;
	private EntitySystem expirationSystem;

	public StarWarrior() {
		super("Star Warrior");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;

		world = new World();

		SystemManager systemManager = world.getSystemManager();
		renderSystem = systemManager.setSystem(new RenderSystem(container));
		hudRenderSystem = systemManager.setSystem(new HudRenderSystem(container));
		controlSystem = systemManager.setSystem(new MovementSystem(container));
		movementSystem = systemManager.setSystem(new PlayerShipControlSystem(container));
		enemyShooterSystem = systemManager.setSystem(new EnemyShipMovementSystem(container));
		enemyShipMovementSystem = systemManager.setSystem(new EnemyShooterSystem());
		collisionSystem = systemManager.setSystem(new CollisionSystem());
		healthBarRenderSystem = systemManager.setSystem(new HealthBarRenderSystem(container));
		enemySpawnSystem = systemManager.setSystem(new EnemySpawnSystem(500, container));
		expirationSystem = systemManager.setSystem(new ExpirationSystem());

		systemManager.initializeAll();

		initPlayerShip();
		initEnemyShips();

	}

	private void initEnemyShips() {
		Random r = new Random();
		for (int i = 0; 10 > i; i++) {
			Entity e = EntityFactory.createEnemyShip(world);
			
			e.getComponent(Transform.class).setLocation(r.nextInt(container.getWidth()), r.nextInt(400)+50);
			e.getComponent(Velocity.class).setVelocity(0.05f);
			e.getComponent(Velocity.class).setAngle(r.nextBoolean() ? 0 : 180);
			
			e.refresh();
		}
	}

	private void initPlayerShip() {
		Entity e = world.createEntity();
		e.setGroup("SHIPS");
		e.addComponent(new Transform(container.getWidth() / 2, container.getHeight() - 40));
		e.addComponent(new SpatialForm("PlayerShip"));
		e.addComponent(new Health(30));
		e.addComponent(new Player());
		
		e.refresh();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.loopStart();
		
		world.setDelta(delta);

		controlSystem.process();
		movementSystem.process();
		enemyShooterSystem.process();
		enemyShipMovementSystem.process();
		collisionSystem.process();
		enemySpawnSystem.process();
		expirationSystem.process();
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
		//container.setMinimumLogicUpdateInterval(1);
		//container.setMaximumLogicUpdateInterval(1);
		//container.setTargetFrameRate(60);
		container.start();
	}
}
