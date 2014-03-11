package de.bjoernschneider.shootii.screens;

import com.badlogic.gdx.Screen;

public class WorldScreen implements Screen {

	private WorldControllerInterface worldController;
	
	public WorldScreen(WorldControllerInterface worldController) {
		this.worldController=worldController;
	}
	
	@Override
	public void render(float delta) {
		worldController.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		worldController.resize(width, height);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	}

}
