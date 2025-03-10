package wizardo.game.Spells.Hybrid.CelestialStrike;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Hat.Legendary_SentientHat;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class CelestialStrike_Spell extends Spell {

    int projectiles = 1;
    int projectilesCast;

    public boolean frostbolts;
    public boolean chargedbolts;
    public boolean chain;

    public CelestialStrike_Spell() {

        raycasted = true;
        aimReach = 15;

        string_name = "Celestial Strike";

        dmg = 120;
        cooldown = 2.4f;
        autoaimable = true;

        anim_element = SpellUtils.Spell_Element.COLDLITE;

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
        }


        Monster target = Legendary_SentientHat.findTarget();
        if(target != null) {
            targetPosition = new Vector2(target.body.getPosition());
        } else {
            screen.spellManager.remove(this);
        }

        if(targetPosition == null) {
            return;
        }

        stateTime += delta;
        if(stateTime >= projectilesCast * 0.33f) {

            CelestialStrike_Hit strike = new CelestialStrike_Hit();

            if(projectiles > 1) {
                strike.targetPosition = SpellUtils.getClearRandomPosition(targetPosition, 3f);
            } else {
                strike.targetPosition = targetPosition;
            }

            strike.frostbolts = frostbolts;
            strike.chain = chain;
            strike.chargedbolts = chargedbolts;
            screen.spellManager.add(strike);

            projectilesCast++;

        }

        if(projectilesCast >= projectiles) {
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
        int dmg = this.dmg;
        dmg += 20 * player.spellbook.thunderstorm_lvl;
        return dmg;
    }
}
