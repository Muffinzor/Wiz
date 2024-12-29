package wizardo.game.Spells.Fire.Flamejet;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.GameSettings.dmg_text_on;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Wizardo.player;

public class Flamejet_Spell extends Spell {

    public boolean frostbolts;
    public boolean icespear;
    public boolean arcaneMissile;
    public boolean rift;

    public float interval;
    public int quantity = 1;
    int flamesCast;

    public Flamejet_Spell() {

        name = "Flamejet";

        speed = 20;
        cooldown = 0.6f;
        baseDmg = 25;

        main_element = FIRE;

        lightAlpha = 0.88f;

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }

        if(originBody == null) {
            originBody = player.pawn.body;
        }

        if(arcaneMissile && targetPosition == null) {
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 7, true);
            if(inRange.isEmpty()) {
                screen.spellManager.toRemove(this);
                return;
            } else {
                arcaneTargeting(inRange);
            }
        }

        if(stateTime >= interval * flamesCast) {
            Flamejet_Projectile flame = new Flamejet_Projectile();
            flame.spawnPosition = new Vector2(getSpawnPosition());
            flame.targetPosition = new Vector2(getTargetPosition());
            flame.setFlame(this);
            flame.setElements(this);
            screen.spellManager.toAdd(flame);
            flamesCast++;
        }

        stateTime += delta;

        if(flamesCast >= quantity) {
            screen.spellManager.toRemove(this);
        }

    }

    public void setup() {
        if(targetPosition == null) {
            quantity = 2 + Math.min((player.spellbook.flamejet_lvl-1)/2, 5);
        }
        interval = cooldown / quantity;
    }

    public void setFlame(Flamejet_Spell thisFlame) {
        quantity = thisFlame.quantity;
        frostbolts = thisFlame.frostbolts;
        icespear = thisFlame.icespear;
        rift = thisFlame.rift;
        lightAlpha = thisFlame.lightAlpha;
        originBody = thisFlame.originBody;
    }

    public void arcaneTargeting(ArrayList<Monster> inRange) {
        Monster target = null;
        if(!inRange.isEmpty()) {
            float dst = Float.MAX_VALUE;
            for(Monster monster : inRange) {
                float monsterDst = monster.body.getPosition().dst(player.pawn.getPosition());
                if(monsterDst < dst) {
                    dst = monsterDst;
                    target = monster;
                }
            }
        }

        if(target != null) {
            targetPosition = new Vector2(target.body.getPosition());
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.flamejet_lvl;

    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 4 * getLvl();
        dmg = (int) (dmg * (1 + player.spellbook.flashBonusDmg/100f));
        return dmg;
    }

    public void dealDmg(Monster monster) {
        float dmg = getDmg();
        dmg += 4 * monster.flamejetStacks;
        dmg = getScaledDmg(dmg);
        float randomFactor = MathUtils.random(1 - dmgVariance, 1 + dmgVariance);
        dmg *= randomFactor;
        monster.hp -= dmg;

        if(dmg_text_on) {
            dmgText( (int)dmg, monster);
        }
    }

}
