package de.bjoernschneider.shootii.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.bjoernschneider.shootii.model.Ship;
import de.bjoernschneider.shootii.model.World;
import de.bjoernschneider.shootii.screens.WorldControllerInterface;
import de.bjoernschneider.shootii.view.GameAudio;
import de.bjoernschneider.shootii.view.InputControllerInterface;
import de.bjoernschneider.shootii.view.InputHandler;
import de.bjoernschneider.shootii.view.WorldRenderer;

public class WorldController implements InputControllerInterface, WorldControllerInterface {

	enum Keys {
		LEFT, RIGHT, UP, DOWN, MOVE, FIRE
	}
	
	private World world;
	private WorldRenderer renderer;
	
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.MOVE, false);
		keys.put(Keys.FIRE, false);
	};
	private int moveOldX, moveOldY, moveNewX, moveNewY; // coordinates for ship movement while touch dragging

	public WorldController(GameAudio gameAudio) {
		world = new World(gameAudio);
		renderer=new WorldRenderer(world);
		Gdx.input.setInputProcessor(new InputHandler(renderer, this));
		
	}
	
	public World getWorld() {
		return world;
	}

	public WorldRenderer getRenderer() {
		return renderer;
	}

	@Override
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}
	@Override
	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}
	@Override
	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}
	@Override
	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}
	@Override
	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}
	@Override
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}
	@Override
	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}
	@Override
	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}
	@Override
	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}
	@Override
	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}


	@Override
	public void move(int oldX, int oldY, int newX, int newY) {
		keys.get(keys.put(Keys.MOVE, true));
		moveOldX=oldX;
		moveOldY=oldY;
		moveNewX=newX;
		moveNewY=newY;
	}
	
	@Override
	public void update(float delta) {
		processInput();
		world.update();
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
	}
	
	private void processInput() {
		Ship ship = world.getShip(); 
		float width=world.getWorldRectangle().width;
		float height=world.getWorldRectangle().height;
		if (keys.get(Keys.LEFT)) ship.getVelocity().x=(ship.getPosition().x>0?-1:0);
		if (keys.get(Keys.RIGHT)) ship.getVelocity().x=(ship.getPosition().x+ship.getWidth()<width?1:0);
		if (keys.get(Keys.UP)) ship.getVelocity().y=(ship.getPosition().y+ship.getHeight()<height?1:0);
		if (keys.get(Keys.DOWN)) ship.getVelocity().y=(ship.getPosition().y>0?-1:0);
		// need to check if both or none direction are pressed, then Bob is idle
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) || (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) ship.getVelocity().x = 0;
		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) || (!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) ship.getVelocity().y = 0;
		if ((keys.get(Keys.FIRE))) { 
			world.addBullet(); 
			keys.get(keys.put(Keys.FIRE, false)); // force single shots
		}
		if (keys.get(Keys.MOVE)) {
			 Vector3 oldInWorld = new Vector3(moveOldX, moveOldY, 0);
			 renderer.getCamera().unproject(oldInWorld);
			 Vector3 newInWorld = new Vector3(moveNewX, moveNewY, 0);
			 renderer.getCamera().unproject(newInWorld);
			 Vector3 v = newInWorld.cpy().sub(oldInWorld);
			 float x=ship.getPosition().x+v.x;
			 float y=ship.getPosition().y+v.y;
			 if (x<0f) x=0f;
			 if (x+ship.getWidth()>width) x=width-ship.getWidth();
			 if (y<0f) y=0f;
			 if (y+ship.getHeight()>height) y=height-ship.getHeight();
			 ship.setPosition(new Vector2(x, y));
			 keys.get(keys.put(Keys.MOVE, false));
		}
	}
	
	public void dispose() {
		world.dispose();
		renderer.dispose();
	}





}
