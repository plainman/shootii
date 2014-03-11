package de.bjoernschneider.shootii;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import de.bjoernschneider.shootii.controller.WorldController;
import de.bjoernschneider.shootii.screens.WorldScreen;
import de.bjoernschneider.shootii.screens.MainMenuScreen;
import de.bjoernschneider.shootii.screens.ScreenManagerInterface;
import de.bjoernschneider.shootii.screens.SplashScreen;
import de.bjoernschneider.shootii.view.GameAudio;

public class Shootii extends Game implements ScreenManagerInterface {
	
	public static final String VERSION = "0.01";
	public static final String LOG = "Shootii";
	
	private WorldController worldController;
	private GameAudio gameAudio;
//	FPSLogger log;
	
	@Override
	public void create() {	
		Gdx.app.log(Shootii.LOG, "It all begins....");
//		log = new FPSLogger();
		gameAudio = new GameAudio();

//		startSplash();
//		startMainMenu();
		startGame();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		gameAudio.dispose();
		if (worldController!=null) worldController.dispose();
		Gdx.app.log(Shootii.LOG, "....and now it all ends.");
	}

	@Override
	public void render() {
		super.render();
//		log.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public void startSplash() { 
		setScreen(new SplashScreen(this)); 
	}
	@Override
	public void stopSplash() {
		startMainMenu();	
	}
	public void startMainMenu() { 
		gameAudio.playMusic(true);
		setScreen(new MainMenuScreen(this)); 
	}
	@Override
	public void stopMainMenu() {
		gameAudio.stopMusic();
		startGame();
		
	}
	public void startGame() {
		worldController = new WorldController(gameAudio);
		setScreen(new WorldScreen(worldController));
	}
}
