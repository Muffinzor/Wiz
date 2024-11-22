package wizardo.game.Spells.Fire.Flamejet;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Flamejet_Spell extends Spell {

    public boolean frostbolts;
    public boolean icespear;
    public boolean arcaneMissile;

    public Flamejet_Spell() {

        name = "Flamejet";

        speed = 20;
        cooldown = 0.1f;
        baseDmg = 12;

        main_element = FIRE;

    }

    @Override
    public void update(float delta) {
        if(originBody == null) {
            originBody = player.pawn.body;
        }

        if(arcaneMissile) {
            arcaneTargeting();
        }

        if(delta > 0) {
            Flamejet_Projectile flame = new Flamejet_Projectile();
            flame.spawnPosition = new Vector2(getSpawnPosition());
            flame.targetPosition = new Vector2(getTargetPosition());
            flame.setFlame(this);
            flame.setElements(this);
            currentScreen.spellManager.toAdd(flame);
            currentScreen.spellManager.toRemove(this);
        }

    }

    public void setFlame(Flamejet_Spell thisFlame) {
        frostbolts = thisFlame.frostbolts;
        icespear = thisFlame.icespear;
        lightAlpha = thisFlame.lightAlpha;
        originBody = thisFlame.originBody;
    }

    public void arcaneTargeting() {
        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 5, true);
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
        return dmg;
    }
}
