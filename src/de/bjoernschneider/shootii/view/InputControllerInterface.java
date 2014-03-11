package de.bjoernschneider.shootii.view;


public interface InputControllerInterface {
	public void leftPressed();
	public void rightPressed();
	public void upPressed();
	public void downPressed();
	public void firePressed();
	public void leftReleased();
	public void rightReleased();
	public void upReleased();
	public void downReleased();
	public void fireReleased();
	public void move(int oldX, int oldY, int newX, int newY);
}
