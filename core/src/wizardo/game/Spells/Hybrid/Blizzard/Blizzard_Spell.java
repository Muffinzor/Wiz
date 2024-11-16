package wizardo.game.Spells.Hybrid.Blizzard;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Blizzard_Spell extends Spell {

    public float blizz_radius;

    public float interval;
    public float frequency = 80;
    public float duration = 6f;

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean rift;

    public Vector2 blizzard_center;

    public Blizzard_Spell() {

        name = "Blizzard";

        cooldown = 12;

        speed = 15;
        baseDmg = 24;

        blizz_radius = 20;
        radius = 25;

        interval = 1/frequency;

        main_element = SpellUtils.Spell_Element.FROST;
    }

    @Override
    public void update(float delta) {

        if(!initialized && delta > 0) {
            initialized = true;
            if(rift) {
                blizzard_center = getTargetPosition();
                blizz_radius = 5;
            } else {
                blizzard_center = player.pawn.getPosition();
            }
        }

        stateTime += delta;

        if(stateTime % interval < delta) {

            Vector2 randomTarget = null;
            int attempts = 0;
            while (randomTarget == null && attempts < 10) {
                attempts++;
                Vector2 attempt = SpellUtils.getRandomVectorInRadius(blizzard_center, blizz_radius);
                if (!isPositionOverlappingWithObstacle(attempt)) {
                    randomTarget = attempt;
                }
            }

            if(randomTarget != null) {
                Blizzard_Projectile proj = new Blizzard_Projectile(randomTarget);
                proj.setElements(this);
                proj.screen = screen;
                proj.frostbolts = frostbolts;
                screen.spellManager.toAdd(proj);
            }

        }

        if(stateTime >= duration) {
            screen.spellManager.toRemove(this);
        }

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 1;
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 8 * player.spellbook.icespear_lvl;
        return dmg;
    }


}
