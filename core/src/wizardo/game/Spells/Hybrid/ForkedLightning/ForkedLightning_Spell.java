package wizardo.game.Spells.Hybrid.ForkedLightning;


import wizardo.game.Items.Equipment.Hat.Epic_ForkedLightningHat;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.GameSettings.autoAim_On;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRELITE;
import static wizardo.game.Wizardo.player;

public class ForkedLightning_Spell extends Spell {

    int hits;

    boolean fromOtherSpell;
    float targetRadius = 2.8f;

    public boolean chargedbolts;
    public boolean fireball;

    public ForkedLightning_Spell() {

        string_name = "Forked Lightning";
        levelup_enum = LevelUpEnums.LevelUps.FORKEDLIGHTNING;
        dmg = 26;

        raycasted = true;
        aimReach = 3.5f;

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
                ForkedLightning_Hit hit = new ForkedLightning_Hit(inRange.get(i));
                hit.originBody = originBody;
                hit.fireball = fireball;
                hit.chargedbolts = chargedbolts;
                hit.setElements(this);
                screen.spellManager.add(hit);
            }
        }


        screen.spellManager.remove(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return (player.spellbook.chainlightning_lvl + player.spellbook.flamejet_lvl)/2;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg + player.spellbook.chainlightning_lvl * 4;
        dmg = (int) (dmg * (1 + player.spellbook.conductiveBonusDmg/100f));
        return dmg;
    }

    public void setup() {
        if(originBody == null) {
            originBody = player.pawn.body;
        } else {
            fromOtherSpell = true;
        }

        if(!controllerActive && !fromOtherSpell) {

            if(autoAim_On) {
                targetPosition = player.pawn.getPosition();
                targetRadius = 7;
            } else {
                targetPosition = SpellUtils.getTargetVector(originBody.getPosition(), getTargetPosition(), 4);
            }

        } else {

            if(autoAim_On) {
                targetPosition = player.pawn.getPosition();
                targetRadius = 7;
            } else {
                targetPosition = getTargetPosition();
            }

        }

        if(fromOtherSpell) {
            targetPosition = originBody.getPosition();
            targetRadius = 5;
        }

        hits = Math.max(player.spellbook.flamejet_lvl/2, 1);
    }

    @Override
    public float getCooldown() {
        float trueCD = cooldown;
        if(player.inventory.equippedHat instanceof Epic_ForkedLightningHat) {
            trueCD = 0.07f;
        }
        return trueCD;
    }
}
