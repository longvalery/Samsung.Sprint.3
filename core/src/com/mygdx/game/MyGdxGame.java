package com.mygdx.game;

import static com.mygdx.game.GameSettings.POSITION_ITERATIONS;
import static com.mygdx.game.GameSettings.SCALE;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;
import static com.mygdx.game.GameSettings.SCREEN_WIDTH;
import static com.mygdx.game.GameSettings.STEP_TIME;
import static com.mygdx.game.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.ContactManager;
import com.mygdx.game.screens.GameScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private GameScreen game;
	public World world;
	public Vector3 touch;

	private float accumulator = 0;

	
	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		world.setGravity(new Vector2(0,0));

		batch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT );
		this.game = new GameScreen(this);
		setScreen(game);
		this.world.setContactListener(new ContactManager(this));
	}
	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void stepWorld(float delta) {
		accumulator += delta;

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;
			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
//			System.out.println(String.format("world.step %f", accumulator));
		}

	}

	@Override
	public void render () {


//		ScreenUtils.clear(1, 0, 0, 1);
//		this.batch.begin();
		float delta = Gdx.graphics.getDeltaTime();
		this.stepWorld(delta);
        this.game.render(delta);
//		this.batch.end();
	}


	@Override
	public void dispose () {
		this.batch.dispose();
        this.game.dispose();
	}
}
