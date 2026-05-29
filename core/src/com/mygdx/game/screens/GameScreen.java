package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ShipObject;
import com.mygdx.game.components.TrashObject;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    ShipObject shipObject;
    GameSession session;
    ArrayList<TrashObject> trashArray;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.session = new GameSession();
        trashArray = new ArrayList<>();

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
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.getBatch());
        myGdxGame.getBatch().end();
    }

    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {
            if (!trashArray.get(i).isInFrame()) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }

    @Override
    public void render(float delta) {
        this.handleInput();
        this.shipObject.update(); // обновить координаты для рисования
        if (this.session.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(
                    GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                    GameResources.TRASH_IMG_PATH,
                    myGdxGame.world
            );
            trashArray.add(trashObject);
        }
        this.updateTrash();
        this.draw();
    }

    @Override
    public void show() {
        super.show();
        this.session.startGame();
    }

    //    public GameScreen(MyGdxGame myGdxGame) {
//        this.myGdxGame = myGdxGame;
//    }

}