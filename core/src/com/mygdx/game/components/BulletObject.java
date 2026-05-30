package com.mygdx.game.components;

import static com.mygdx.game.GameSettings.BULLET_BIT;
import static com.mygdx.game.GameSettings.SCREEN_HEIGHT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class BulletObject extends GameObject{
    boolean wasHit;
    public BulletObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, world, BULLET_BIT);
        this.body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        this.wasHit = false;
    }
    public boolean hasToBeDestroyed() {
        return  wasHit || ((this.getY() + (this.height / 2)) > SCREEN_HEIGHT);
    }

    @Override
    public void hit() {
        wasHit = true;
    }
}
