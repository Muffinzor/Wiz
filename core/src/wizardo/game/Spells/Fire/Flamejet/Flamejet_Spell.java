package wizardo.game.Spells.Fire.Flamejet;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Flamejet_Spell extends Spell {

    public boolean frostbolts;
    public boolean icespear;

    public Flamejet_Spell() {

        name = "Flamejet";

        speed = 20;
        cooldown = 0.075f;
        baseDmg = 12;

        main_element = FIRE;

    }

    @Override
    public void update(float delta) {

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
