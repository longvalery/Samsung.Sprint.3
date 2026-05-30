package com.mygdx.game.components;

import static com.mygdx.game.GameSettings.TRASH_BIT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject{

    private static final int paddingHorizontal  = 30;
    private int livesLeft;


    public TrashObject (int width, int height, String texturePath , World world ) {
        super(texturePath,
                width / 2 + paddingHorizontal + (new Random().nextInt(GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width))
                , GameSettings.SCREEN_HEIGHT + height / 2
                , width, height, world, TRASH_BIT);
               this.body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
               this.livesLeft = 1;
    }

    public boolean isInFrame() {
        return this.getY() + this.height / 2 > 0;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }

    public boolean isAlive() {
        return this.livesLeft > 0;
    }

}
