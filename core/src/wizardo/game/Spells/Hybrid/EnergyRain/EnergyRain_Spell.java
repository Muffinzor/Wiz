package wizardo.game.Spells.Hybrid.EnergyRain;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Spells.SpellUtils.Spell_Element.ARCANE;
import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.player;

public class EnergyRain_Spell extends Spell {

    public int projectiles;
    public int projectilesSent = 0;

    public float interval;

    public boolean frostbolt;
    public boolean chargedbolts;

    public boolean riftTargeting;

    public EnergyRain_Spell() {
        name = "Energy Rain";
        cooldown = 12;
        baseDmg = 75;
        interval = 0.2f;
        radius = 16;

        main_element = ARCANE;
    }


    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }
        stateTime += delta;

        if(stateTime >= projectilesSent * interval) {

            if(riftTargeting) {
                riftTargeting();
            } else {
                regularTargeting();
            }

        }

        if(projectilesSent >= projectiles) {
            screen.spellManager.toRemove(this);
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
        return baseDmg + player.spellbook.energybeam_lvl * 25;
    }

    public void setup() {
        projectiles = 6 + player.spellbook.rift_lvl * 2;
        if(riftTargeting) {
            targetPosition = getTargetPosition();
        }
    }

    public Monster findTarget() {
        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), radius, false);
        if(!inRange.isEmpty()) {
            int index = (int) (Math.random() * inRange.size());
            return inRange.get(index);
        }
        return null;
    }

    public Vector2 getRandomizedPosition(Monster target) {
        Vector2 randomTarget = null;
        int attempts = 0;
        while (randomTarget == null && attempts < 10) {
            attempts++;
            Vector2 attempt = SpellUtils.getRandomVectorInRadius(target.body.getPosition(), 3);
            if (!isPositionOverlappingWithObstacle(attempt) || attempts == 10) {
                randomTarget = attempt;
            }
        }
        return randomTarget;
    }

    public void regularTargeting() {
        Monster target = findTarget();
        if(target != null) {
            Vector2 randomPosition = getRandomizedPosition(target);
            EnergyRain_Projectile beam = new EnergyRain_Projectile();
            beam.setElements(this);
            beam.targetPosition = randomPosition;
            beam.frostbolt = frostbolt;
            beam.chargedbolts = chargedbolts;
            screen.spellManager.toAdd(beam);
        }
        projectilesSent++;
    }
    public void riftTargeting() {
        Vector2 randomTarget = SpellUtils.getRandomVectorInRadius(targetPosition, 3.5f);
        EnergyRain_Projectile beam = new EnergyRain_Projectile();
        beam.setElements(this);
        beam.targetPosition = randomTarget;
        beam.frostbolt = frostbolt;
        beam.chargedbolts = chargedbolts;
        screen.spellManager.toAdd(beam);
        projectilesSent++;
    }
}
