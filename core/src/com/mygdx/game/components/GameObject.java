package com.mygdx.game.components;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class GameObject {
    private float x;
    private float y;
    int width;
    int height;
    World world;
    Texture texture;
    Body body;

    GameObject(String texturePath, int x, int y, int width, int height, World world) {
        this.width = width;
        this.height = height;

        this.texture = new Texture(texturePath);
        this.body = createBody(x, y, world);
        this.x = x;
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.getX() - (this.width / 2 )
                , this.getY() - (this.width / 2), this.width, this.height);
    }

    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef(); // def - defenition (определение) это объект, который содержит все данные, необходимые для посторения тела

        def.type = BodyDef.BodyType.DynamicBody; // тип тела, который имеет массу и может быть подвинут под действием сил
        def.fixedRotation = true; // запрещаем телу вращаться вокруг своей оси
        Body body = world.createBody(def); // создаём в мире world объект по описанному нами определению
        body.setSleepingAllowed(false); // Не спать

        CircleShape circleShape = new CircleShape(); // задаём коллайдер в форме круга
        circleShape.setRadius(Math.max(width, height) * SCALE / 2f); // определяем радиус круга коллайдера

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape; // устанавливаем коллайдер
        fixtureDef.density = 1.0f; // устанавливаем плотность тела
        fixtureDef.friction = 0.1f; // устанвливаем коэффициент трения

        body.createFixture(fixtureDef); // создаём fixture по описанному нами определению
        circleShape.dispose(); // так как коллайдер уже скопирован в fixutre, то circleShape может быть отчищена, чтобы не забивать оперативную память.

        body.setTransform(x * SCALE, y * SCALE, 0); // устанавливаем позицию тела по координатным осям и угол поворота
        return body;
    }

    public void update() {
        // Синхронизация пиксельных координат с физическим телом
        this.x = body.getPosition().x / SCALE;
        this.y = body.getPosition().y / SCALE;
    }
/*
    public void move(Vector2 targetPosition) {
        Vector2 currentPosition = body.getPosition();
        Vector2 direction = new Vector2(targetPosition).sub(currentPosition);

        float maxSpeed = 5.0f;

        if (direction.len() > 0.1f) {
            direction.nor().scl(maxSpeed);
            body.setLinearVelocity(direction);
        } else {
            body.setLinearVelocity(Vector2.Zero);
        }
    }
    public void dispose() {
        this.texture.dispose();
    }
*/

}
