package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ShipObject;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    ShipObject shipObject;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, GameSettings.SHIP_HEIGHT / 2,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
//            myGdxGame.touch = myGdxGame.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
//            shipObject.move(myGdxGame.touch);
            // Получаем координаты касания в пикселях
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Преобразуем в мировые координаты через камеру
            shipObject.move(myGdxGame.getCamera().unproject(touchPos));
        }
    }

    private void draw() {
        myGdxGame.getCamera().update();
        myGdxGame.getBatch().setProjectionMatrix(myGdxGame.getCamera().combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.getBatch().begin();
        shipObject.draw(myGdxGame.getBatch());
        myGdxGame.getBatch().end();

    }
    @Override
    public void render(float delta) {
        this.handleInput();
        this.shipObject.update(); // обновить координаты для рисования
        this.draw();
    }

//    public GameScreen(MyGdxGame myGdxGame) {
//        this.myGdxGame = myGdxGame;
//    }

}