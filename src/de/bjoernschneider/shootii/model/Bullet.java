package de.bjoernschneider.shootii.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MoveableEntity {
	
	private static final float WIDTH=0.1f;
	private static final float HEIGHT=0.4f;
	private static final float SPEED=10f;

	public Bullet() {
		super();
	}

	public void init(float worldX, float worldY, float rotation) {
		super.init(new Vector2(worldX, worldY), WIDTH, HEIGHT, SPEED, rotation);
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public void update(Ship ship) {
		Vector2 v2=new Vector2(1f,0); v2.rotate(rotation);
		position.add(v2.cpy().scl(Gdx.graphics.getDeltaTime() * speed));
		super.update(ship);
	}

	
	
	

}
