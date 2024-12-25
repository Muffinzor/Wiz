package wizardo.game.Spells.Hybrid.Laser;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class Laser_Spell extends Spell {

    public boolean thunderstorm;
    public boolean frostbolt;
    public boolean chargedbolt;
    public boolean rifts;
    public boolean flamejet;
    public boolean overheat;

    int lasers;
    int lasersCast;

    Monster target;
    int targetCounter;
    ArrayList<Monster> inRange;

    public Laser_Spell() {

        name = "Lasers";

        cooldown = 0.8f;
        speed = 15f;

        baseDmg = 10;

        inRange = new ArrayList<>();

    }

    @Override
    public void update(float delta) {

        if(!initialized) {
            setup();
            initialized = true;
        }

        stateTime += delta;

        if(stateTime >= 0.05f * lasersCast) {

            selectTarget();

            if(target != null) {

                Laser_Projectile laser = new Laser_Projectile();
                laser.spawnPosition = new Vector2(originBody.getPosition());
                laser.targetPosition = new Vector2(target.body.getPosition());
                laser.setLaser(this);
                screen.spellManager.toAdd(laser);

                lasersCast ++;
            }

        }

        if(lasersCast >= lasers) {
            screen.spellManager.toRemove(this);
        }

    }

    public void setup() {

        if(originBody == null) {
            originBody = player.pawn.body;
            lasers = 3 + player.spellbook.arcanemissile_lvl/2;
        } else {
            lasers = 1;
        }

        inRange = SpellUtils.findMonstersInRangeOfVector(originBody.getPosition(), 10, true);
        if(originBody == player.pawn.body) {
            inRange.sort((m1, m2) -> Float.compare(m2.hp, m1.hp));
            if (inRange.size() > lasers) {
                inRange.subList(lasers, inRange.size()).clear();
            }
        }
    }

    public void setLaser(Laser_Spell parent) {
        setElements(parent);
        frostbolt = parent.frostbolt;
        rifts = parent.rifts;
        chargedbolt = parent.chargedbolt;
        thunderstorm = parent.thunderstorm;
        flamejet = parent.flamejet;
    }

    public void selectTarget() {
        if(targetCounter == inRange.size()) {
            targetCounter = 0;
        }

        if(!inRange.isEmpty()) {
            target = inRange.get(targetCounter);
            targetCounter++;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return (player.spellbook.energybeam_lvl + player.spellbook.arcanemissile_lvl)/2;
    }


    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 10 * player.spellbook.energybeam_lvl;
        dmg = (int) (dmg * (1 + player.spellbook.energyBonusDmg/100f));
        return dmg;
    }

}
