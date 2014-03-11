package de.bjoernschneider.shootii.view;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;


public class InputHandler implements InputProcessor {
	
	private InputControllerInterface inputController;
	private WorldRenderer renderer;
	private int lastX, lastY;
	
	public InputHandler(WorldRenderer renderer, InputControllerInterface inputController) {
		this.renderer = renderer;
		this.inputController=inputController;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)  inputController.leftPressed();
		if (keycode == Keys.RIGHT) inputController.rightPressed();
		if (keycode == Keys.UP)    inputController.upPressed();
		if (keycode == Keys.DOWN)  inputController.downPressed();
		if (keycode == Keys.X)     inputController.firePressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)  inputController.leftReleased();
		if (keycode == Keys.RIGHT) inputController.rightReleased();
		if (keycode == Keys.UP)    inputController.upReleased();
		if (keycode == Keys.DOWN)  inputController.downReleased();
		if (keycode == Keys.X)     inputController.fireReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		boolean processed=false;
        if (isScreenXYinViewportRectangle(screenX, screenY, renderer.getFireButtonRectangle())) {
        	inputController.firePressed();
        	processed=true;
        } else {
    		lastX=screenX;
    		lastY=screenY;
        }
        return processed;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		boolean processed=false;
        if (isScreenXYinViewportRectangle(screenX, screenY, renderer.getFireButtonRectangle())) {
        	inputController.fireReleased();
        	processed=true;
        }
        return processed;
	}
	
	private boolean isScreenXYinViewportRectangle(int x, int y, Rectangle rect) {
		int height=renderer.getHeight();
        if (x > rect.x && x < rect.x+rect.width && height-y > rect.y && height-y < rect.y+rect.height) return true;
        return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		boolean processed=false;
		if (!isScreenXYinViewportRectangle(screenX, screenY, renderer.getFireButtonRectangle())) {
			inputController.move(lastX, lastY, screenX, screenY);
			lastX=screenX;
			lastY=screenY;
			processed=true;
		} else {
			inputController.fireReleased();
		}
		return processed;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
