package de.bjoernschneider.shootii.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class Entity implements Poolable {
	protected Vector2 position;
	protected float width;
	protected float height;
	protected Rectangle bounds;
	protected boolean alive;
	
	public Entity() {
		bounds=new Rectangle();
	}
	
	public void init(Vector2 position, float width, float height) {
		this.bounds.set(position.x, position.y, width, height);
		this.position=position;
		this.width=width;
		this.height=height;
		this.alive=true;
	}

	@Override
	public void reset() {
        position.set(0,0);
        bounds.set(0, 0, 0, 0);
        width=0; 
        height=0;
        alive = false;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		this.position.set(bounds.getPosition(position));
		this.width=bounds.width;
		this.height=bounds.height;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
