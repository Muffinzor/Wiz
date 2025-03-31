package wizardo.game.Spells.Hybrid.ForkedLightning;


import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Equipment.Hat.Epic_ForkedLightningHat;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.GameSettings.autoAim_On;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRELITE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class ForkedLightning_Spell extends Spell {

    float quantity;
    int hits_done;
    float interval;

    boolean fromOtherSpell;
    float targetRadius = 2.6f;

    public boolean chargedbolts;
    public boolean fireball;

    public ForkedLightning_Spell() {
        string_name = "Forked Lightning";
        levelup_enum = LevelUpEnums.LevelUps.FORKEDLIGHTNING;
        dmg = 28;

        raycasted = true;
        aimReach = 3.2f;
        cooldown = 1.6f;

        anim_element = FIRELITE;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            setup();
        }
        stateTime += delta;

        if(stateTime >= interval * hits_done) {
            find_target_center();
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(targetPosition, targetRadius, false);
            inRange.removeIf(monster -> monster.body.equals(originBody));
            inRange.removeIf(monster -> !SpellUtils.hasLineOfSight(monster.body.getPosition(), originBody.getPosition()));
            Collections.shuffle(inRange);

            if(!inRange.isEmpty()) {
                Collections.shuffle(inRange);
                ForkedLightning_Hit hit = new ForkedLightning_Hit(inRange.getFirst());
                hit.originBody = originBody;
                hit.fireball = fireball;
                hit.chargedbolts = chargedbolts;
                hit.setElements(this);
                screen.spellManager.add(hit);
            }

            hits_done++;
        }

        if(hits_done >= quantity) {
            screen.spellManager.remove(this);
        }

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return this.dmg + player.spellbook.chainlightning_lvl * 14;
    }

    public void setup() {
        if(originBody == null) {
            originBody = player.pawn.body;
        } else {
            fromOtherSpell = true;
        }
        set_hit_count();
        aimReach *= (1 + player.spellbook.forkedlightning_bonus_range/100f);
        targetRadius *= (1 + player.spellbook.forkedlightning_bonus_range/100f);
    }

    public void set_hit_count() {
        quantity = 8 + player.spellbook.flamejet_lvl * 2;
        quantity *= (1 + player.spellbook.forkedlightning_bonus_quantity/100f);
        interval = getCooldown()/quantity;
    }

    public void find_target_center() {
        if(!controllerActive && !fromOtherSpell) {
            if(autoAim_On) {
                targetPosition = player.pawn.getPosition();
                targetRadius = aimReach * 2;
            } else {
                targetPosition = null;
                targetPosition = SpellUtils.getTargetVector(originBody.getPosition(), getTargetPosition(), aimReach);
            }
        } else if (!fromOtherSpell) {
            if(autoAim_On) {
                targetPosition = player.pawn.getPosition();
                targetRadius = aimReach * 2;
            } else {
                targetPosition = null;
                targetPosition = getTargetPosition();
            }
        }

        if(fromOtherSpell) {
            targetPosition = originBody.getPosition();
            targetRadius = aimReach * 2;
        }
    }

    @Override
    public float getCooldown() {
        float trueCD = cooldown;
        if(player.inventory.equippedHat instanceof Epic_ForkedLightningHat) {
            trueCD = cooldown * 0.8f;
        }
        return trueCD;
    }
}
