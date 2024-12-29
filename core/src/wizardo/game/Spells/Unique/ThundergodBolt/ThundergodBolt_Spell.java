package wizardo.game.Spells.Unique.ThundergodBolt;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class ThundergodBolt_Spell extends Spell {

    public ThundergodBolt_Spell() {
        speed = 30f;
        main_element = SpellUtils.Spell_Element.LIGHTNING;
        anim_element = SpellUtils.Spell_Element.LIGHTNING;
    }


    @Override
    public void update(float delta) {

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
        int dmg = 40;
        dmg += 10 * player.spellbook.chainlightning_lvl;
        dmg += 10 * player.spellbook.thunderstorm_lvl;
        dmg += 10 * player.spellbook.chargedbolt_lvl;
        return dmg;
    }
}
