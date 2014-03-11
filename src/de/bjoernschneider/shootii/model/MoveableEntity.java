package de.bjoernschneider.shootii.model;

import com.badlogic.gdx.math.Vector2;

public abstract class MoveableEntity extends Entity {
	protected Vector2 velocity;
	protected float speed;
	protected float rotation;
	
	public MoveableEntity() {
		super();
		velocity = new Vector2();
	}

	@Override
	public void reset() {
		super.reset();
		speed=0;
		rotation=0;
		velocity.set(0, 0);
	}

	public void init(Vector2 position, float width, float height, float speed, float rotation) {
		super.init(position, width, height);
		this.speed = speed;
		this.rotation = rotation;
	}

	public void update(Ship ship) {
		bounds.set(position.x, position.y, width, height);
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
