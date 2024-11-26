package wizardo.game.Utils.Contacts;

import com.badlogic.gdx.physics.box2d.*;
import wizardo.game.Maps.TriggerObject;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterSpells.MonsterSpell;
import wizardo.game.Spells.Spell;

import static wizardo.game.Utils.Contacts.Masks.*;
import static wizardo.game.Wizardo.player;

public class WizContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        boolean f1isPawn = (f1.getFilterData().categoryBits & PAWN) != 0;
        boolean f2isPawn = (f2.getFilterData().categoryBits & PAWN) != 0;

        boolean f1isMonster = (f1.getFilterData().categoryBits & MONSTER) != 0 || (f1.getFilterData().categoryBits & FLYING_MONSTER) != 0;
        boolean f2isMonster = (f2.getFilterData().categoryBits & MONSTER) != 0 || (f2.getFilterData().categoryBits & FLYING_MONSTER) != 0;

        boolean f1isSpell = (f1.getFilterData().categoryBits & SPELL) != 0;
        boolean f2isSpell = (f2.getFilterData().categoryBits & SPELL) != 0;

        boolean f1isMonsterSpell = (f1.getFilterData().categoryBits & MONSTER_PROJECTILE) != 0;
        boolean f2isMonsterSpell = (f2.getFilterData().categoryBits & MONSTER_PROJECTILE) != 0;

        boolean f1isObstacle = (f1.getFilterData().categoryBits & OBSTACLE) != 0;
        boolean f2isObstacle = (f2.getFilterData().categoryBits & OBSTACLE) != 0;

        boolean f1isTrigger = (f1.getFilterData().categoryBits & TRIGGER) != 0;
        boolean f2isTrigger = (f2.getFilterData().categoryBits & TRIGGER) != 0;


        /* PAWN + TRIGGER */
        if(f1isPawn && f2isTrigger || f2isPawn && f1isTrigger) {
            TriggerObject object;
            if(f2isTrigger) {
                object = (TriggerObject) f2.getBody().getUserData();
            } else {
                object = (TriggerObject) f1.getBody().getUserData();
            }
            object.handleCollision();
        } else

        /* MONSTER + SPELL */
        if(f1isMonster && f2isSpell || f2isMonster && f1isSpell) {
            if(f1isMonster) {
                Spell_Monster_Begin(f2, f1);
            } else {
                Spell_Monster_Begin(f1, f2);
            }
        } else

        /* SPELL + OBSTACLE */
        if(f1isSpell && f2isObstacle || f2isSpell && f1isObstacle) {
            if(f1isSpell) {
                Spell spell = (Spell) f1.getBody().getUserData();
                spell.handleCollision(f2);
            } else {
                Spell spell = (Spell) f2.getBody().getUserData();
                spell.handleCollision(f1);
            }
        }

        /* MONSTER SPELL + PLAYER */
        if(f1isMonsterSpell && f2isPawn || f2isMonsterSpell && f1isPawn) {
            if(f1isMonsterSpell) {
                MonsterSpell spell = (MonsterSpell) f1.getBody().getUserData();
                spell.handleCollision(player.pawn.body);
            } else {
                MonsterSpell spell = (MonsterSpell) f2.getBody().getUserData();
                spell.handleCollision(player.pawn.body);
            }
        }

        /* MONSTER SPELL + OBSTACLE */
        if(f1isMonsterSpell && f2isObstacle || f2isMonsterSpell && f1isObstacle) {
            if(f1isMonsterSpell) {
                MonsterSpell spell = (MonsterSpell) f1.getBody().getUserData();
                spell.handleCollision(f2);
            } else {
                MonsterSpell spell = (MonsterSpell) f2.getBody().getUserData();
                spell.handleCollision(f1);
            }
        }
    }

    public void Spell_Monster_Begin(Fixture spellFix, Fixture monsterFix) {
        Spell spell = (Spell) spellFix.getBody().getUserData();
        Monster monster = (Monster) monsterFix.getBody().getUserData();
        spell.handleCollision(monster);
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
