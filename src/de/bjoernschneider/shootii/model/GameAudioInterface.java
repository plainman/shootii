package de.bjoernschneider.shootii.model;

public interface GameAudioInterface {
	public void playMusic(boolean looping);
	public void stopMusic();
	public void shoot();
	public void explode();
}
