package de.bjoernschneider.shootii.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import de.bjoernschneider.shootii.model.GameAudioInterface;

public class GameAudio implements GameAudioInterface {
	
	private static Music song = Gdx.audio.newMusic(Gdx.files.internal("data/determination.mp3"));
	private static Sound shoot = Gdx.audio.newSound(Gdx.files.internal("data/shoot.wav"));
	private static Sound explosion = Gdx.audio.newSound(Gdx.files.internal("data/boom.wav"));

	@Override
	public void playMusic(boolean looping) {
		song.setLooping(looping);
		song.play();
	}
	@Override
	public void stopMusic() {
		song.stop();
	}
	@Override
	public void shoot() {
		shoot.play();
	}
	@Override
	public void explode() {
		explosion.play();
	}
	public void dispose() {
		song.dispose();
		shoot.dispose();
		explosion.dispose();
	}
}
