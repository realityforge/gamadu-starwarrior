package com.gamadu.starwarrior.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gamadu.starwarrior.EntityFactory;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;

public class PlayerShipControlSystem extends IntervalEntitySystem implements KeyListener {
	private GameContainer container;
	private boolean moveRight;
	private boolean moveLeft;
	private boolean shoot;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private boolean disable;
	private boolean enable;
	private Entity player;

	public PlayerShipControlSystem(GameContainer container) {
		super(Aspect.getEmpty(), 50);
		this.container = container;
	}

	@Override
	public void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		player = world.getManager(TagManager.class).getEntity("PLAYER");

		if (player != null && player.isEnabled()) {
			if (moveLeft) {
				velocityMapper.get(player).setVx(-0.2f);
			} else if (moveRight) {
				velocityMapper.get(player).setVx(0.2f);
			} else {
				velocityMapper.get(player).setVx(0);
			}

			if (shoot) {
				Transform transform = transformMapper.get(player);
				EntityFactory.createPlayerMissile(world, transform.getX(), transform.getY() - 20).addToWorld();
			}
		}

		if (disable) {
			player.disable();
			disable = false;
		} else if (enable) {
			player.enable();
			enable = false;
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
		} else if (key == Input.KEY_Q) {
			enable = true;
		} else if (key == Input.KEY_W) {
			disable = true;
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
