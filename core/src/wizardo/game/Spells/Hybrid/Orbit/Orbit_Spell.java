package wizardo.game.Spells.Hybrid.Orbit;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Orbit_Spell extends Spell {

    public int orbs = 3;
    public float orbitRadius = 5;

    public float speed = 2.5f;

    public boolean frostbolt;

    public Orbit_Spell() {
        name = "Orbit";
        anim_element = FROST;
        main_element = FROST;
        bonus_element = ARCANE;

        baseDmg = 20;
        cooldown = 12;
    }

    @Override
    public void update(float delta) {

        createOrbs();
        screen.spellManager.toRemove(this);
    }

    public void createOrbs() {
        int randomStart = MathUtils.random(360);
        for (int i = 0; i < orbs; i++) {

            float angle = i * 2 * MathUtils.PI / orbs;
            angle+= randomStart;
            float x = player.pawn.getPosition().x + orbitRadius * MathUtils.cos(angle);
            float y = player.pawn.getPosition().y + orbitRadius * MathUtils.sin(angle);
            Vector2 startPos = new Vector2(x, y);
            Orbit_Projectile orb = new Orbit_Projectile(startPos, angle);
            orb.setElements(this);
            orb.frostbolt = frostbolt;
            orb.orbitRadius = orbitRadius;
            screen.spellManager.toAdd(orb);

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
        return baseDmg + 10 * player.spellbook.frozenorb_lvl;
    }
}
