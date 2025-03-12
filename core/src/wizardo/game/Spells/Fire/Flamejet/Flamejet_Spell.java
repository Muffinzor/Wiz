package wizardo.game.Spells.Fire.Flamejet;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.GameSettings.autoAim_On;
import static wizardo.game.GameSettings.dmg_text_on;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Wizardo.player;

public class Flamejet_Spell extends Spell {

    public boolean frostbolts;
    public boolean icespear;
    public boolean arcaneMissile;
    public boolean rift;

    public float interval;
    public float quantity = 1;
    int flamesCast;

    public Flamejet_Spell() {

        string_name = "Flamejet";

        speed = 20;
        cooldown = 1f;
        dmg = 20;

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

        if(autoAim_On || arcaneMissile && targetPosition == null) {
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 7, true);
            if(inRange.isEmpty()) {
                screen.spellManager.remove(this);
                return;
            } else {
                autoTargeting(inRange);
            }
        }

        if(stateTime >= interval * flamesCast) {
            Flamejet_Projectile flame = new Flamejet_Projectile();
            flame.spawnPosition = new Vector2(getSpawnPosition());
            flame.targetPosition = new Vector2(getTargetPosition());
            flame.setFlame(this);
            flame.setElements(this);
            screen.spellManager.add(flame);
            flamesCast++;
        }

        stateTime += delta;

        if(stateTime >= getCooldown()) {
            screen.spellManager.remove(this);
        }

    }

    public void setup() {
        if(targetPosition == null) {
            quantity = 1 + player.spellbook.flamejet_lvl;
        }
        interval = getCooldown() / quantity;
    }

    public void setFlame(Flamejet_Spell thisFlame) {
        quantity = thisFlame.quantity;
        frostbolts = thisFlame.frostbolts;
        icespear = thisFlame.icespear;
        rift = thisFlame.rift;
        lightAlpha = thisFlame.lightAlpha;
        originBody = thisFlame.originBody;
    }

    public void autoTargeting(ArrayList<Monster> inRange) {
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
        int dmg = this.dmg;
        dmg += 8 * getLvl();
        return dmg;
    }

    public void dealDmg(Monster monster) {
        float dmg = getDmg();
        dmg += 4 * monster.flamejetStacks;
        dmg = getScaledDmg(dmg);
        float randomFactor = MathUtils.random(1 - dmgVariance, 1 + dmgVariance);
        dmg *= randomFactor;
        monster.hp -= dmg;

        checkGearProcs(monster);

        if(dmg_text_on) {
            dmgText( (int)dmg, monster);
        }
    }

}
