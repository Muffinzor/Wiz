package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class Frost_Spells {

    public static Spell[] frostspells = new Spell[7];

    public static void createFrost_Spells() {

        Frostbolt_Spell frost0 = new Frostbolt_Spell();
        frost0.spellParts.add(FROSTBOLT);
        frost0.anim_element = SpellUtils.Spell_Element.FROST;
        frostspells[0] = frost0;

        Icespear_Spell frost1 = new Icespear_Spell();
        frost1.anim_element = SpellUtils.Spell_Element.FROST;
        frost1.spellParts.add(ICESPEAR);
        frostspells[1] = frost1;

        Frozenorb_Spell frost2 = new Frozenorb_Spell();
        frost2.anim_element = SpellUtils.Spell_Element.FROST;
        frost2.spellParts.add(FROZENORB);
        frostspells[2] = frost2;

        Icespear_Spell frost3 = new Icespear_Spell();
        frost3.anim_element = SpellUtils.Spell_Element.FROST;
        frost3.frostbolts = true;
        frost3.spellParts.add(ICESPEAR);
        frost3.spellParts.add(FROSTBOLT);
        frostspells[3] = frost3;

        Frozenorb_Spell frost4 = new Frozenorb_Spell();
        frost4.anim_element = SpellUtils.Spell_Element.FROST;
        frost4.nested_spell = new Frostbolt_Spell();
        frost4.spellParts.add(FROZENORB);
        frost4.spellParts.add(FROSTBOLT);
        frostspells[4] = frost4;

        Frozenorb_Spell frost5 = new Frozenorb_Spell();
        frost5.anim_element = SpellUtils.Spell_Element.FROST;
        frost5.nested_spell = new Icespear_Spell();
        frost5.spellParts.add(FROZENORB);
        frost5.spellParts.add(ICESPEAR);
        frostspells[5] = frost5;

        Frozenorb_Spell frost6 = new Frozenorb_Spell();
        Icespear_Spell spear6 = new Icespear_Spell();
        spear6.nested_spell = new Frostbolt_Explosion();
        frost6.anim_element = SpellUtils.Spell_Element.FROST;
        frost6.nested_spell = spear6;
        frost6.spellParts.add(FROZENORB);
        frost6.spellParts.add(ICESPEAR);
        frost6.spellParts.add(FROSTBOLT);
        frostspells[6] = frost6;

    }
}
