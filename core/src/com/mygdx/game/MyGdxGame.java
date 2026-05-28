package com.mygdx.game;

import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.screens.GameScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private GameScreen game;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT );
		this.game = new GameScreen(this);
		setScreen(game);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		this.batch.begin();

		this.batch.end();
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
        this.game.dispose();
	}
}
