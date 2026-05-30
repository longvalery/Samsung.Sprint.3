package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.BulletObject;
import com.mygdx.game.components.ContactManager;
import com.mygdx.game.components.ShipObject;
import com.mygdx.game.components.TrashObject;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    ShipObject shipObject;
    GameSession session;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    ContactManager contactManager;

    Box2DDebugRenderer debugRenderer;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.session = new GameSession();
        this.trashArray = new ArrayList<>();
        this.bulletArray = new ArrayList<>();
        this.debugRenderer = new Box2DDrawerenderer();

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
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.getBatch());
        myGdxGame.getBatch().end();
    }

    private void updateTrash() {
        for (int i = 0; i < this.trashArray.size(); i++) {
            TrashObject trash = this.trashArray.get(i);
            trash.update();
            if ((! trash.isInFrame()) || (! trash.isAlive())) {
                myGdxGame.world.destroyBody(trash.getBody());
                this.trashArray.remove(i--);
            }
        }
    }

    private void updateBullets(){
        for (int i = 0; i < this.bulletArray.size(); i++) {
            BulletObject bullet = this.bulletArray.get(i);
            bullet.update();
            if (bullet.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bullet.getBody());
                this.bulletArray.remove(i--);
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
        this.updateBullets();
        if (shipObject.needToShoot()) {
            BulletObject laserBullet = new BulletObject(
                    GameResources.BULLET_IMG_PATH,
                    (int)shipObject.getX(), (int)shipObject.getY() + shipObject.getHeight() / 2,
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                    myGdxGame.world
            );
            bulletArray.add(laserBullet);
        }

       this.myGdxGame.world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        this.draw();

        debugRenderer.render(myGdxGame.world, myGdxGame.getCamera().combined);

        if (!shipObject.isAlive()) {
            System.out.println("Game over!");
        }
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