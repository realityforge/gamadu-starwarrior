package com.gamadu.starwarrior;

import com.artemis.Entity;
import com.artemis.World;
import com.gamadu.starwarrior.components.Enemy;
import com.gamadu.starwarrior.components.Expires;
import com.gamadu.starwarrior.components.Health;
import com.gamadu.starwarrior.components.SpatialForm;
import com.gamadu.starwarrior.components.Transform;
import com.gamadu.starwarrior.components.Velocity;
import com.gamadu.starwarrior.components.Weapon;

public class EntityFactory {
	public static Entity createMissile(World world) {
		Entity missile = world.createEntity();
		missile.setGroup("BULLETS");
		
		missile.addComponent(new Transform());
		missile.addComponent(new SpatialForm("Missile"));
		missile.addComponent(new Velocity());
		missile.addComponent(new Expires(2000));

		return missile;
	}
	
	public static Entity createEnemyShip(World world) {
		Entity e = world.createEntity();
		e.setGroup("SHIPS");
		
		e.addComponent(new Transform());
		e.addComponent(new SpatialForm("EnemyShip"));
		e.addComponent(new Health(10));
		e.addComponent(new Weapon());
		e.addComponent(new Enemy());
		e.addComponent(new Velocity());
		
		return e;
	}
	
	public static Entity createBulletExplosion(World world, float x, float y) {
		Entity e = world.createEntity();
		
		e.setGroup("EFFECTS");
		
		e.addComponent(new Transform(x, y));
		e.addComponent(new SpatialForm("BulletExplosion"));
		e.addComponent(new Expires(1000));
		
		return e;
	}
	
	public static Entity createShipExplosion(World world, float x, float y) {
		Entity e = world.createEntity();
		
		e.setGroup("EFFECTS");
		
		e.addComponent(new Transform(x, y));
		e.addComponent(new SpatialForm("ShipExplosion"));
		e.addComponent(new Expires(1000));
		
		return e;
	}

}
