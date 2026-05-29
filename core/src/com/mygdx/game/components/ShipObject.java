package com.mygdx.game.components;



import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

import java.awt.SystemTray;

public class ShipObject extends GameObject{
    public ShipObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, world);
        body.setLinearDamping(10);
    }



    private void putInFrame() {
        if (getY() > (GameSettings.SCREEN_HEIGHT / 2f - height / 2f)) {
            setY(GameSettings.SCREEN_HEIGHT / 2 - height / 2);
        }
        if (getY() <= (height / 2f)) {
            setY(height / 2);
        }
        if (getX() < (-width / 2f)) {
            setX(GameSettings.SCREEN_WIDTH);
        }
        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }

    // В ShipObject.move
    public void move(Vector3 touchScreen) {
        // Преобразуем экранные координаты касания (пиксели) в метры Box2D
        float touchWorldX = touchScreen.x * GameSettings.SCALE;
        float touchWorldY = touchScreen.y * GameSettings.SCALE;
        Vector2 targetPos = new Vector2(touchWorldX, touchWorldY);

        Vector2 currentPos = body.getPosition();
        Vector2 toTarget = targetPos.sub(currentPos);
        float distance = toTarget.len();

        if (distance < 0.1f) {
            body.setLinearVelocity(0, 0);
            return;
        }

        // Просто задаём скорость в направлении цели
        float speed = 5.0f; // м/с
        Vector2 desiredVel = toTarget.nor().scl(speed);
        body.setLinearVelocity(desiredVel);
    }

 /*

    public void move(Vector3 vector3) {
        Vector2 currentPosition = body.getPosition();
        Vector2 targetPosition = new Vector2(
                (vector3.x - getX()) * GameSettings.SHIP_FORCE_RATIO,
                (vector3.y - getY()) * GameSettings.SHIP_FORCE_RATIO
        );

        Vector2 desiredVelocity = new Vector2(targetPosition).sub(currentPosition).scl(2.0f); // коэффициент

        // Вычисляем силу, необходимую для достижения желаемой скорости
        Vector2 velocityChange = new Vector2(desiredVelocity).sub(body.getLinearVelocity());
        Vector2 force = velocityChange.scl(body.getMass());

        // Ограничиваем силу, чтобы избежать нестабильности
        float maxForce = 100.0f;
        if (force.len() > maxForce) { force.nor().scl(maxForce); }
       //
        body.setAwake(true);
        body.setFixedRotation(true);
        //
        body.applyForceToCenter(force, true);
    }
*/
}
