package com.gamadu.starwarrior.components;

import com.artemis.Component;

public class Velocity extends Component {
	private float vx;
	private float vy;

	public Velocity() {
	}

	public Velocity(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}
	
	


}
