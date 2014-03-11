package de.bjoernschneider.shootii.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Ship extends MoveableEntity {

	private static final float WIDTH=2f;
	private static final float HEIGHT=2f;
	private static final float SPEED=20f;
	private static final float STANDARD_ROTATION=90f; // ship upwards
	
	protected boolean rotationOn = false;

	public Ship() {
		super();
	}
	
	public void init(float worldX, float worldY) {
		super.init(new Vector2(worldX, worldY), WIDTH, HEIGHT, SPEED, STANDARD_ROTATION);
		rotationOn=false;
	}

	@Override
	public void reset() {
		super.reset();
		rotationOn=false;
	}

	@Override
	public void update(Ship ship) {
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime() * speed));
		
		if (rotationOn)
			if (velocity.x!=0||velocity.y!=0) rotation = velocity.angle();
		
		super.update(ship);
	}

	public void setRotationOn(boolean rotationOn) {
		this.rotationOn = rotationOn;
	}
	public Vector2 getTailPosition() {
		return getEndPosition(-1f);	
	}
	public Vector2 getNosePosition() {
		return getEndPosition(1f);
	}
	private Vector2 getEndPosition(float factor) {
		Vector2 v = new Vector2(width/2f, 0);
		v.rotate(rotation);
		return new Vector2(position.x+width/2f+(v.x*factor), position.y+height/2f+(v.y*factor));		
	}
}
