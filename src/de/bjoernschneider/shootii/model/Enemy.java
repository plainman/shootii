package de.bjoernschneider.shootii.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends MoveableEntity {
	
	private static final float WIDTH=2f;
	private static final float HEIGHT=2f;
	private static final float SPEED=3f;
	private static final float ROTATION_SPEED = 100; 
	
	protected boolean hit;
	
	public Enemy() {
		super();
	}

	public void init(float worldX, float worldY) {
		super.init(new Vector2(worldX, worldY), WIDTH, HEIGHT, SPEED, 0);
		hit=false;
	}
	@Override
	public void reset() {
		super.reset();
		hit=false;
	}
	@Override
	public void update(Ship ship) {
		float delta = Gdx.graphics.getDeltaTime();
		
		position.x-=delta*speed;
		position.y-=delta*speed/3;
		//position.lerp(ship.getPosition(), delta * speed);

		rotation += delta * ROTATION_SPEED;
		if (rotation>360) rotation-=360;
		
		super.update(ship);
	}
	
	public boolean isHit() {
		return hit;
	}
	public void setHit() {
		this.hit = true;
	}
}
