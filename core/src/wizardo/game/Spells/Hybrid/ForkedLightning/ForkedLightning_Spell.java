package wizardo.game.Spells.Hybrid.ForkedLightning;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Wizardo.player;

public class ForkedLightning_Spell extends Spell {

    int hits;

    boolean fromOtherSpell;
    float targetRadius = 2.5f;

    public boolean chargedbolts;

    public ForkedLightning_Spell() {

        raycasted = true;
        aimReach = 4;

        cooldown = 0.1f;

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            setup();
        }

        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(targetPosition, targetRadius, false);
        inRange.removeIf(monster -> !SpellUtils.hasLineOfSight(monster.body.getPosition(), originBody.getPosition()));
        Collections.shuffle(inRange);

        System.out.println(hits);

        for (int i = 0; i < hits; i++) {
            if(inRange.size() >= i+1) {
                ChainLightning_Hit chain = new ChainLightning_Hit(inRange.get(i));
                chain.maxHits = 1;
                chain.forked = true;
                chain.originBody = originBody;
                chain.setElements(this);
                screen.spellManager.toAdd(chain);
                chargedbolts(inRange.get(i));
            }
        }

        screen.spellManager.toRemove(this);

    }

    @Override
    public void dispose() {

    }

    public void chargedbolts(Monster monster) {
        if(chargedbolts) {
            float procRate = 0.925f - 0.025f * player.spellbook.chargedbolt_lvl;
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
