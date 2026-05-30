package com.mygdx.game.components;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class ContactManager implements ContactListener {
    private MyGdxGame parent;

    public ContactManager(MyGdxGame parent){
        this.parent = parent;
    }


    @Override
    public void beginContact(Contact contact) { // код, выполнияющийся в момент начала контакта
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits;
        int cDef2 = fixB.getFilterData().categoryBits;

        if (cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.BULLET_BIT
            || cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.BULLET_BIT
            || cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.SHIP_BIT
            || cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.SHIP_BIT) {
            ((GameObject) fixA.getUserData()).hit();
            ((GameObject) fixB.getUserData()).hit();
        }
    }

    @Override
    public void endContact(Contact contact) { // код, выполняющийся после завершения контакта

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {  // код, выполняющийся перед вычислением всех контактов

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {  // код, выполняющийся сразу после вычислений контактов

    }
}
