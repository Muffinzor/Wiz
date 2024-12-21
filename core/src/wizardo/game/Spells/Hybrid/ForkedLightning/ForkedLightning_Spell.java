package wizardo.game.Spells.Hybrid.ForkedLightning;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Fire.Fireball.Fireball_Explosion;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRELITE;
import static wizardo.game.Spells.SpellUtils.Spell_Element.LIGHTNING;
import static wizardo.game.Wizardo.player;

public class ForkedLightning_Spell extends Spell {

    int hits;

    boolean fromOtherSpell;
    float targetRadius = 2.5f;

    public boolean chargedbolts;
    public boolean fireball;

    public ForkedLightning_Spell() {

        name = "Forked Lightning";

        raycasted = true;
        aimReach = 4;

        cooldown = 0.1f;

        anim_element = FIRELITE;

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            setup();
        }

        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(targetPosition, targetRadius, false);
        inRange.removeIf(monster -> monster.body.equals(originBody));
        inRange.removeIf(monster -> !SpellUtils.hasLineOfSight(monster.body.getPosition(), originBody.getPosition()));
        Collections.shuffle(inRange);


        for (int i = 0; i < hits; i++) {
            if(inRange.size() >= i+1) {
                ChainLightning_Hit chain = new ChainLightning_Hit(inRange.get(i));
                chain.maxHits = 1;
                chain.forked = true;
                chain.originBody = originBody;
                chain.setElements(this);
                screen.spellManager.toAdd(chain);
                chargedbolts(inRange.get(i));
                fireball(inRange.get(i));
            }
        }


        screen.spellManager.toRemove(this);

    }

    @Override
    public void dispose() {

    }

    public void chargedbolts(Monster monster) {
        if(chargedbolts) {
            float procRate = 0.95f - 0.05f * player.spellbook.chargedbolt_lvl;
            int quantity = 3 + player.spellbook.chargedbolt_lvl / 5;
            if (Math.random() >= procRate) {
                for (int i = 0; i < quantity; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.flamejet = true;
                    bolt.setElements(this);
                    bolt.spawnPosition = new Vector2(monster.body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 2);
                    screen.spellManager.toAdd(bolt);
                }
            }
        }
    }

    public void fireball(Monster monster) {
        if(fireball) {
            float procRate = 0.98f - 0.02f * player.spellbook.fireball_lvl;
            if(Math.random() >= procRate) {
                Fireball_Explosion explosion = new Fireball_Explosion();
                explosion.targetPosition = new Vector2(monster.body.getPosition());
                explosion.setElements(this);
                explosion.anim_element = LIGHTNING;
                explosion.firelite = true;
                screen.spellManager.toAdd(explosion);
            }
        }
    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return 0;
    }

    public void setup() {
        if(originBody == null) {
            originBody = player.pawn.body;
        } else {
            fromOtherSpell = true;
        }

        if(!controllerActive) {
            targetPosition = SpellUtils.getTargetVector(originBody.getPosition(), getTargetPosition(), 4);
        } else {
            targetPosition = getTargetPosition();
        }

        if(fromOtherSpell) {
            targetPosition = originBody.getPosition();
            targetRadius = 5;
        }

        hits = Math.max(player.spellbook.flamejet_lvl/2, 1);


    }
}
