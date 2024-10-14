package wizardo.game.Utils.Contacts;

import com.badlogic.gdx.physics.box2d.*;
import wizardo.game.Maps.TriggerObject;

import static wizardo.game.Utils.Contacts.Masks.*;

public class WizContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        boolean f1isPawn = (f1.getFilterData().categoryBits & PAWN) != 0;
        boolean f2isPawn = (f2.getFilterData().categoryBits & PAWN) != 0;

        boolean f1isTrigger = (f1.getFilterData().categoryBits & TRIGGER) != 0;
        boolean f2isTrigger = (f2.getFilterData().categoryBits & TRIGGER) != 0;

        if(f1isPawn && f2isTrigger || f2isPawn && f1isTrigger) {
            TriggerObject object;
            if(f2isTrigger) {
                object = (TriggerObject) f2.getBody().getUserData();
            } else {
                object = (TriggerObject) f1.getBody().getUserData();
            }
            object.handleCollision();
        }
    }



    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
