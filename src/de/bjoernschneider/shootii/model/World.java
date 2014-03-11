package de.bjoernschneider.shootii.model;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


public class World {

	private Ship ship;
	private Array<Bullet> activeBullets;
    private Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
                return new Bullet();
        }
    };
	private Array<Enemy> enemies;
	
	private GameAudioInterface gameAudio;

	private final float WORLD_WIDTH = 48f;
    private final float WORLD_HEIGHT = 26f;
    private final Rectangle WORLD_RECTANGLE = new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    public  final Rectangle getWorldRectangle() { return WORLD_RECTANGLE; }
    private final String LOG = "World";
	
	public World(GameAudioInterface gameAudio) {
		this.gameAudio=gameAudio;
		ship = new Ship();
		ship.init(WORLD_WIDTH/2,1);
		enemies = new Array<Enemy>();
		Enemy e = new Enemy(); 
		e.init(WORLD_WIDTH,WORLD_HEIGHT); 
		enemies.add(e);
		e = new Enemy(); 
		e.init(WORLD_WIDTH,WORLD_HEIGHT*0.75f); 
		enemies.add(e);
		activeBullets = new Array<Bullet>();
	}

	public Ship getShip() {
		return ship;
	}
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	public void addBullet() {
        Bullet b = bulletPool.obtain();
		Vector2 v2 = ship.getNosePosition();
		b.init(v2.x,v2.y, ship.getRotation());
        activeBullets.add(b);
		gameAudio.shoot();
	}
	public Array<Bullet> getBullets() {
		return activeBullets;
	}

	
	public void update() {
		// update ship
		ship.update(ship);
		// test collision between ship and enemies		
		Iterator<Enemy> eIter = enemies.iterator();
		while(eIter.hasNext()) {
			Enemy e = eIter.next(); 
			if (e.isAlive()) { // enemy is healthy
				e.update(ship);
				if (ship.getBounds().overlaps(e.getBounds())&&e.isAlive())
					Gdx.app.log(LOG, "Ship hit!");
			} else { // enemy is exploding or out of world
				eIter.remove();
			}
		}
		// test collision between bullets and enemies
		Iterator<Bullet> bIter = activeBullets.iterator();
		while(bIter.hasNext()) {
			Bullet b = bIter.next();
			b.update(ship);
			Rectangle rb=b.getBounds();
			if (rb.overlaps(WORLD_RECTANGLE)) { // bullet is still in the world
				Iterator<Enemy> ebIter = enemies.iterator();
				while(ebIter.hasNext()) {
					Enemy e = ebIter.next(); 
					if (b.getBounds().overlaps(e.getBounds())&&e.isAlive()) { // enemy hit first time
						e.setHit();
						bIter.remove();
						bulletPool.free(b);
						gameAudio.explode();
					}
				}
			} else { // bullet has left world
				bIter.remove();
				bulletPool.free(b);
			}
		}
	}
	
	public void dispose() {
	}
	
}
