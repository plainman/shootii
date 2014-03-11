package de.bjoernschneider.shootii.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MainMenuScreen implements Screen {
	
	private Stage stage;
	private BitmapFont black;
	private BitmapFont white;
	private TextureAtlas atlas;
	private Skin skin;
	private SpriteBatch batch;
	private TextButton button;
	private Label label;
	private ScreenManagerInterface screenManager;
		
	public MainMenuScreen(ScreenManagerInterface screenManager) {
		this.screenManager = screenManager;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		batch.begin();
		stage.draw();
		batch.end();	
	}

	@Override
	public void resize(int width, int height) {
		if (stage==null)
			stage=new Stage(width, height, true);
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
		
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
		
		button = new TextButton("Play!", style);
		button.setWidth(Gdx.graphics.getWidth()/2);
		button.setHeight(Gdx.graphics.getHeight()/8);
		button.setX(Gdx.graphics.getWidth()/2-button.getWidth()/2);
		button.setY(Gdx.graphics.getHeight()/2-button.getHeight()/2);
//		Gdx.app.log(Shootii.LOG, "W="+Gdx.graphics.getWidth()+" H="+Gdx.graphics.getHeight()+" Bx="+button.getX()+" By="+button.getY()+" Bw="+button.getWidth()+" Bh="+button.getHeight());
		
		button.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log(Shootii.LOG, "down");
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log(Shootii.LOG, "up");
				screenManager.stopMainMenu();
			}
		});
		
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		label = new Label("Shootii", ls);
		label.setX(0);
		label.setY(Gdx.graphics.getHeight()/2+100);
		label.setWidth(width);
		label.setAlignment(Align.center);
		
		stage.addActor(button);
		stage.addActor(label);
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
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
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		stage.dispose();
	}

}
