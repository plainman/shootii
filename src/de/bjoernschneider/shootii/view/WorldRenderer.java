package de.bjoernschneider.shootii.view;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import de.bjoernschneider.shootii.model.Bullet;
import de.bjoernschneider.shootii.model.Enemy;
import de.bjoernschneider.shootii.model.Ship;
import de.bjoernschneider.shootii.model.World;

public class WorldRenderer {
	
	private static final float CAMERA_WIDTH = 48f;		// for now camera shows whole world, not more, not less
    private static final float CAMERA_HEIGHT = 26f;

	private World world;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private Texture shipTexture, followerTexture, bulletTexture, fireButtonTexture;
	private ShapeRenderer sr;
	private ParticleEffect exhaust;
	ParticleEffectPool explosionEffectPool;
	Array<PooledEffect> explosions;
	private Rectangle fireButtonRectangle;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	public void setSize (int w, int h) {
		width = w;
		height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		// fire button size is 20% of smaller screen curb and in the lower right
		int fireButtonDia=(width>height?height:width)*2/10;
		fireButtonRectangle.set(width-fireButtonDia, 0, fireButtonDia, fireButtonDia);
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public WorldRenderer(World world) {
		this.world = world;
		
		cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		cam.position.set(CAMERA_WIDTH/2f, CAMERA_HEIGHT/2f, 0);
		cam.update();
		
		batch = new SpriteBatch();
		
		shipTexture = new Texture("data/playerShip2_blue.png");
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		followerTexture = new Texture("data/follower.png");
		followerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bulletTexture = new Texture("data/bullet.png");
		bulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		fireButtonTexture = new Texture("data/firebutton.png");
		fireButtonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		exhaust = new ParticleEffect();
		exhaust.load(Gdx.files.internal("data/exhaust.p"), Gdx.files.internal("data"));
		
		ParticleEffect explode = new ParticleEffect();
		explode.load(Gdx.files.internal("data/explode.p"), Gdx.files.internal("data"));
		explosionEffectPool = new ParticleEffectPool(explode, 1, 10);
		explosions = new Array<PooledEffect>();
		
		fireButtonRectangle = new Rectangle();
		
		exhaust.start();
		
		sr = new ShapeRenderer();
		sr.setColor(Color.CYAN);
	}
	
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		drawShip();
		drawEnemies();
		drawBullets();
		drawScreen();
		batch.end();
		if (debug) drawDebug();
	}
	
	private void drawScreen() {
		batch.draw(fireButtonTexture, fireButtonRectangle.x, fireButtonRectangle.y, fireButtonRectangle.width, fireButtonRectangle.height);
	}
	
	private void drawShip() {
		Ship ship=world.getShip();
		float angle=ship.getRotation();
		Vector2 v2 = ship.getTailPosition();
		exhaust.setPosition(v2.x*ppuX, v2.y*ppuY);
		ParticleEmitter pe = exhaust.getEmitters().get(0);
		pe.getAngle().setLow(angle+180); // opposite direction in which ship moves
		pe.getAngle().setHighMin(angle+180-45); // 45° opening angle to each direction
		pe.getAngle().setHighMax(angle+180+45);
		exhaust.draw(batch, Gdx.graphics.getDeltaTime());

		batch.draw(
				shipTexture,  											// texture
				ship.getPosition().x*ppuX, ship.getPosition().y*ppuY, 	// position in pixel (world units * Pixel per Unit)
				ship.getWidth()/2*ppuX, ship.getHeight()/2*ppuY,		// origin for rotation and scaling relative to position in pixel (middle of ship)
				ship.getWidth()*ppuX, ship.getHeight()*ppuY, 			// size in pixel
				1f, 1f, 												// scaling
				ship.getRotation()-90, 									// rotation (-90, because of ship-image is flying upwards, -0 would be ship flying to the right)
				0, 0,													// coordinates within image (left lower corner) 													
				shipTexture.getWidth(), shipTexture.getHeight(), 		// size to take from image (whole image)
				false, false											// neither flipping of image in X nor in Y direction 
				);	
	}

	private void drawBullets() {
		Array<Bullet> bullets=world.getBullets();
		Iterator<Bullet> bIter = bullets.iterator();
		while(bIter.hasNext()) {
			Bullet b = bIter.next();
			batch.draw(bulletTexture, b.getPosition().x*ppuX, b.getPosition().y*ppuY, b.getWidth()/2*ppuX, b.getHeight()/2*ppuY, b.getWidth()*ppuX, b.getHeight()*ppuY, 1f, 1f, b.getRotation()-90, 0, 0, bulletTexture.getWidth(), bulletTexture.getHeight(), false, false);
		}	
	}

	private void drawEnemies() {
		// enemies
		Array<Enemy> enemies=world.getEnemies();
		Iterator<Enemy> eIter = enemies.iterator();
		while(eIter.hasNext()) {
			Enemy e = eIter.next();
			if (e.isAlive()) { 
				float x=e.getPosition().x*ppuX;
				float y=e.getPosition().y*ppuY;
				if (e.isHit()) { // if enemy is hit, set alive to false and start explosion effect
					e.setAlive(false);
					PooledEffect explode = explosionEffectPool.obtain();
					explode.setPosition(x, y);
					explosions.add(explode);
				} else { // enemy is healthy, draw it
					batch.draw(followerTexture, x, y, e.getWidth()/2*ppuX, e.getHeight()/2*ppuY, e.getWidth()*ppuX, e.getHeight()*ppuY, 1f, 1f, e.getRotation()-90, 0, 0, followerTexture.getWidth(), followerTexture.getHeight(), false, false);					
				}
			}
		}	
		// explosions
		Iterator<PooledEffect> peIter = explosions.iterator();
		while(peIter.hasNext()) {
			PooledEffect e = peIter.next();
			e.draw(batch, Gdx.graphics.getDeltaTime());
			if (e.isComplete()) {
				e.free();
				peIter.remove();
			}
		}
	}


	private void drawDebug() {
		sr.begin(ShapeType.Line);
		// Bullets
		Array<Bullet> bullets=world.getBullets();
		Iterator<Bullet> bIter = bullets.iterator();
		while(bIter.hasNext()) {
			Bullet b = bIter.next();
			sr.rect(b.getPosition().x*ppuX, b.getPosition().y*ppuY, b.getWidth()*ppuX, b.getHeight()*ppuY);
		}
		// Enemies
		Array<Enemy> enemies=world.getEnemies();
		Iterator<Enemy> eIter = enemies.iterator();
		while(eIter.hasNext()) {
			Enemy e = eIter.next();
			sr.rect(e.getPosition().x*ppuX, e.getPosition().y*ppuY, e.getWidth()*ppuX, e.getHeight()*ppuY);
		}	
		// Ship
		Ship ship=world.getShip();
		sr.rect(ship.getPosition().x*ppuX, ship.getPosition().y*ppuY, ship.getWidth()*ppuX, ship.getHeight()*ppuY); //, ship.getWidth()/2*ppuX, ship.getHeight()/2*ppuY, ship.getRotation()-90);
		
		sr.end();
	}
	
	public Rectangle getFireButtonRectangle() {
		return fireButtonRectangle;
	}

	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
		followerTexture.dispose();
		bulletTexture.dispose();
		sr.dispose();
	}

}
