package wizardo.game.Spells.Hybrid.DragonBreath;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class DragonBreath_Spell extends Spell {

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean fireball;

    public DragonBreath_Spell() {

        name = "Dragon's Breath";

        baseDmg = 120;

        cooldown = 4f;

        main_element = SpellUtils.Spell_Element.FIRE;

    }

    @Override
    public void update(float delta) {

        DragonBreath_Projectile dragonbreath = new DragonBreath_Projectile();
        dragonbreath.spawnPosition = new Vector2(getSpawnPosition());
        dragonbreath.targetPosition = new Vector2(getTargetPosition());
        dragonbreath.frostbolts = frostbolts;
        dragonbreath.frozenorb = frozenorb;
        dragonbreath.setElements(this);
        screen.spellManager.toAdd(dragonbreath);
        screen.spellManager.toRemove(this);

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
        dmg += 15 * player.spellbook.flamejet_lvl;
        dmg += 15 * player.spellbook.overheat_lvl;
        return dmg;
    }

}
