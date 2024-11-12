package wizardo.game.Spells.Hybrid.CelestialStrike;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class CelestialStrike_Spell extends Spell {

    public boolean frostbolts;
    public boolean chargedbolts;
    public boolean chain;

    public CelestialStrike_Spell() {

        raycasted = true;
        aimReach = 15;

        name = "Celestial Strike";

        baseDmg = 120;

        cooldown = 3;

        main_element = SpellUtils.Spell_Element.LIGHTNING;
        bonus_element = SpellUtils.Spell_Element.FROST;

    }


    @Override
    public void update(float delta) {

        if(delta > 0) {

            if (!initialized) {
                screen = currentScreen;
                initialized = true;
            }

            CelestialStrike_Hit strike = new CelestialStrike_Hit();
            strike.targetPosition = new Vector2(getTargetPosition());
            strike.screen = screen;
            strike.frostbolts = frostbolts;
            strike.chain = chain;
            strike.chargedbolts = chargedbolts;
            screen.spellManager.toRemove(this);
            screen.spellManager.toAdd(strike);

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
        int dmg = baseDmg;
        dmg += 20 * player.spellbook.thunderstorm_lvl;
        return dmg;
    }
}
