package com.thanhht.mariogame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.thanhht.mariogame.MarioGame;
import com.thanhht.mariogame.Sprites.Enemies.Enemy;
import com.thanhht.mariogame.Sprites.Items.Item;
import com.thanhht.mariogame.Sprites.Mario;
import com.thanhht.mariogame.Sprites.Other.FireBall;
import com.thanhht.mariogame.Sprites.TileObjects.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case MarioGame.MARIO_HEAD_BIT | MarioGame.BRICK_BIT:
            case MarioGame.MARIO_HEAD_BIT | MarioGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == MarioGame.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
                break;
            case MarioGame.ENEMY_HEAD_BIT | MarioGame.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
                break;
            case MarioGame.ENEMY_BIT | MarioGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioGame.MARIO_BIT | MarioGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MarioGame.MARIO_BIT)
                    ((Mario) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Mario) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case MarioGame.ENEMY_BIT | MarioGame.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
                break;
            case MarioGame.ITEM_BIT | MarioGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioGame.ITEM_BIT | MarioGame.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
            case MarioGame.FIREBALL_BIT | MarioGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioGame.FIREBALL_BIT)
                    ((FireBall)fixA.getUserData()).setToDestroy();
                else
                    ((FireBall)fixB.getUserData()).setToDestroy();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
