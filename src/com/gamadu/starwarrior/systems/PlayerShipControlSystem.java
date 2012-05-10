package com.gamadu.starwarrior.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Player;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;

public class PlayerShipControlSystem extends EntityProcessingSystem implements KeyListener {
	private GameContainer container;
	private boolean moveRight;
	private boolean moveLeft;
	private boolean shoot;
	private ComponentMapper<Transform> transformMapper;

	public PlayerShipControlSystem(GameContainer container) {
		super(Transform.class, Player.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e) {
		Transform transform = transformMapper.get(e);

		if (moveLeft) {
			transform.addX(world.getDelta() * -0.3f);
		}
		if (moveRight) {
			transform.addX(world.getDelta() * 0.3f);
		}
		
		if (shoot) {
			Entity missile = EntityFactory.createMissile(world);
			missile.getComponent(Transform.class).setLocation(transform.getX(), transform.getY() - 20);
			missile.getComponent(Velocity.class).setVelocity(-0.5f);
			missile.getComponent(Velocity.class).setAngle(90);
			missile.refresh();

			shoot = false;
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_A) {
			moveLeft = true;
			moveRight = false;
		} else if (key == Input.KEY_D) {
			moveRight = true;
			moveLeft = false;
		} else if (key == Input.KEY_SPACE) {
			shoot = true;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_A) {
			moveLeft = false;
		} else if (key == Input.KEY_D) {
			moveRight = false;
		} else if (key == Input.KEY_SPACE) {
			shoot = false;
		}
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input input) {
	}

}
