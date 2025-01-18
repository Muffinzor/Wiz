package wizardo.game.Utils.Contacts;

import com.badlogic.gdx.physics.box2d.*;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.TriggerObject;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
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

        boolean f1isDecor = (f1.getFilterData().categoryBits & DECOR) != 0;
        boolean f2isDecor = (f2.getFilterData().categoryBits & DECOR) != 0;


        /* PAWN + TRIGGER */
        if(f1isPawn && f2isTrigger || f2isPawn && f1isTrigger) {
            TriggerObject object;
            if(f2isTrigger) {
                object = (TriggerObject) f2.getBody().getUserData();
                player.nearbyTriggerObject = object;
            } else {
                object = (TriggerObject) f1.getBody().getUserData();
                player.nearbyTriggerObject = object;
            }
            object.handleCollision();
        } else

        /* MONSTER + PAWN */
        if(f1isPawn && f2isMonster || f1isMonster && f2isPawn) {
            if(f1isMonster) {
                Monster monster = (Monster) f1.getBody().getUserData();
                monster.handleCollision(player);
            } else {
                Monster monster = (Monster) f2.getBody().getUserData();
                monster.handleCollision(player);
            }
        }

        /* MONSTER + MONSTER for push */
        if(f1isMonster && f2isMonster) {
            Monster monster1 = (Monster) f1.getBody().getUserData();
            Monster monster2 = (Monster) f2.getBody().getUserData();
            monster1.handleCollision(monster2);
            monster2.handleCollision(monster1);
        } else

        /* MONSTER + SPELL */
        if(f1isMonster && f2isSpell || f2isMonster && f1isSpell) {
            if(f1isMonster) {
                Spell_Monster_Contact(f2, f1);
            } else {
                Spell_Monster_Contact(f1, f2);
            }
        } else

        if(f1isMonster && f2isDecor || f2isMonster && f1isDecor) {
            EnvironmentObject object;
            if(f1isMonster) {
                object = (EnvironmentObject) f2.getBody().getUserData();
            } else {
                object = (EnvironmentObject) f1.getBody().getUserData();
            }
            object.collided = true;
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
        } else

        /* SPELL + DECOR */
        if(f1isSpell && f2isDecor || f2isSpell && f1isDecor) {
            if(f1isSpell) {
                Spell_Decor_Contact(f1, f2);
            } else {
                Spell_Decor_Contact(f2, f1);
            }
        } else

        /* MONSTER SPELL + PLAYER */
        if(f1isMonsterSpell && f2isPawn || f2isMonsterSpell && f1isPawn) {
            if(f1isMonsterSpell) {
                MonsterSpell spell = (MonsterSpell) f1.getBody().getUserData();
                spell.handleCollision(player.pawn.body);
            } else {
                MonsterSpell spell = (MonsterSpell) f2.getBody().getUserData();
                spell.handleCollision(player.pawn.body);
            }
        } else

        /* MONSTER SPELL + OBSTACLE */
        if(f1isMonsterSpell && f2isObstacle || f2isMonsterSpell && f1isObstacle) {
            if(f1isMonsterSpell) {
                MonsterSpell spell = (MonsterSpell) f1.getBody().getUserData();
                spell.handleCollision(f2);
            } else {
                MonsterSpell spell = (MonsterSpell) f2.getBody().getUserData();
                spell.handleCollision(f1);
            }
        } else

        /* MONSTER SPELL + PLAYER SPELL */
        if(f1isMonsterSpell && f2isSpell || f1isSpell && f2isMonsterSpell) {

            if(f1isMonsterSpell) {
                MonsterSpell spell = (MonsterSpell) f1.getBody().getUserData();
                Spell player_spell = (Spell) f2.getBody().getUserData();
                player_spell.handleCollision(spell);
            } else {
                MonsterSpell spell = (MonsterSpell) f2.getBody().getUserData();
                Spell player_spell = (Spell) f1.getBody().getUserData();
                player_spell.handleCollision(spell);
            }

        }
    }

    public void Spell_Monster_Contact(Fixture spellFix, Fixture monsterFix) {
        Spell spell = (Spell) spellFix.getBody().getUserData();
        Monster monster = (Monster) monsterFix.getBody().getUserData();
        spell.handleCollision(monster);
    }
    public void Spell_Decor_Contact(Fixture spellFix, Fixture decorFix) {
        Spell spell = (Spell) spellFix.getBody().getUserData();
        spell.handleCollision(decorFix);

        EnvironmentObject decor = (EnvironmentObject) decorFix.getBody().getUserData();
        decor.collided = true;
    }


    @Override
    public void endContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        boolean f1isTrigger = (f1.getFilterData().categoryBits & TRIGGER) != 0;
        boolean f2isTrigger = (f2.getFilterData().categoryBits & TRIGGER) != 0;

        boolean f1isPawn = (f1.getFilterData().categoryBits & PAWN) != 0;
        boolean f2isPawn = (f2.getFilterData().categoryBits & PAWN) != 0;

        boolean f1isSpell = (f1.getFilterData().categoryBits & SPELL) != 0;
        boolean f2isSpell = (f2.getFilterData().categoryBits & SPELL) != 0;

        boolean f1isMonsterSpell = (f1.getFilterData().categoryBits & MONSTER_PROJECTILE) != 0;
        boolean f2isMonsterSpell = (f2.getFilterData().categoryBits & MONSTER_PROJECTILE) != 0;

        /* SPELL + MONSTERSPELL */
        if(f1isSpell && f2isMonsterSpell || f2isSpell && f1isMonsterSpell) {
            if(f1isSpell) {
                Spell spell = (Spell) f1.getBody().getUserData();
                MonsterSpell proj = (MonsterSpell) f2.getBody().getUserData();
                spell.exitCollision(proj);
            } else {
                Spell spell = (Spell) f2.getBody().getUserData();
                MonsterSpell proj = (MonsterSpell) f1.getBody().getUserData();
                spell.exitCollision(proj);
            }
        }

        /* PAWN + TRIGGER */
        if(f1isPawn && f2isTrigger || f2isPawn && f1isTrigger) {
            TriggerObject object;
            if(f2isTrigger) {
                object = (TriggerObject) f2.getBody().getUserData();
                if(player.nearbyTriggerObject != null && player.nearbyTriggerObject.equals(object)) {
                    player.nearbyTriggerObject = null;
                }
            } else {
                object = (TriggerObject) f1.getBody().getUserData();
                if(player.nearbyTriggerObject != null && player.nearbyTriggerObject.equals(object)) {
                    player.nearbyTriggerObject = null;
                }
            }
            object.handleCollision();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
