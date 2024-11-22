package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class Fire_Spells {

    public static Spell[] firespells = new Spell[7];

    public static void createFire_Spells() {

        // 0. Flamejet
        Flamejet_Spell fire0 = new Flamejet_Spell();
        fire0.anim_element = FIRE;
        fire0.spellParts.add(FLAMEJET);
        firespells[0] = fire0;

        // 1. Fireball
        Fireball_Spell fire1 = new Fireball_Spell();
        fire1.anim_element = FIRE;
        fire1.spellParts.add(FIREBALL);
        firespells[1] = fire1;

        // 2. Overheat
        Overheat_Spell fire2 = new Overheat_Spell();
        fire2.anim_element = FIRE;
        fire2.spellParts.add(OVERHEAT);
        firespells[2] = fire2;

        // 3. Fireball + Flamejet
        Fireball_Spell fire3 = new Fireball_Spell();
        fire3.anim_element = FIRE;
        fire3.nested_spell = firespells[0];
        fire3.spellParts.add(FIREBALL);
        fire3.spellParts.add(FLAMEJET);
        firespells[3] = fire3;

        // 4. Overheat + Fireball
        Overheat_Spell fire4 = new Overheat_Spell();
        fire4.fireball = true;
        fire4.anim_element = FIRE;
        fire4.spellParts.add(FIREBALL);
        fire4.spellParts.add(OVERHEAT);
        firespells[4] = fire4;

        // 5. Overheat + Flamejet
        DragonBreath_Spell fire5 = new DragonBreath_Spell();
        fire5.anim_element = FIRE;
        fire5.spellParts.add(FLAMEJET);
        fire5.spellParts.add(OVERHEAT);
        firespells[5] = fire5;

        // 6. Overheat + Fireball + Flamejet
        DragonBreath_Spell fire6 = new DragonBreath_Spell();
        fire6.anim_element = FIRE;
        fire6.fireball = true;
        fire6.spellParts.add(FLAMEJET);
        fire6.spellParts.add(FIREBALL);
        fire6.spellParts.add(OVERHEAT);
        firespells[6] = fire6;



    }
}
