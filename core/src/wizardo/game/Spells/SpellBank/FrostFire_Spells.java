package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Projectile;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Spell;
import wizardo.game.Spells.Hybrid.FrostNova.FrostNova_Spell;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class FrostFire_Spells {

    public static Spell[] frostfireSpells = new Spell[27];

    public static void createFrostFire_Spells() {

        // 0. FlameJet + FrostBolts

        Flamejet_Spell frostfire0 = new Flamejet_Spell();
        frostfire0.frostbolts = true;
        frostfire0.anim_element = FROST;
        frostfire0.spellParts.add(FROSTBOLT);
        frostfire0.spellParts.add(FLAMEJET);
        frostfireSpells[0] = frostfire0;

        // 1. Flamejet + Icespear

        Icespear_Spell frostfire1 = new Icespear_Spell();
        frostfire1.flamejet = true;
        frostfire1.anim_element = FIRE;
        frostfire1.spellParts.add(ICESPEAR);
        frostfire1.spellParts.add(FLAMEJET);
        frostfireSpells[1] = frostfire1;

        // 2. Flamejet + FrozenOrb

        Frozenorb_Spell frostfire2 = new Frozenorb_Spell();
        frostfire2.nested_spell = new Flamejet_Spell();
        frostfire2.anim_element = FIRE;
        frostfire2.spellParts.add(FLAMEJET);
        frostfire2.spellParts.add(FROZENORB);
        frostfireSpells[2] = frostfire2;

        // 3. Fireball + Frostbolts

        Fireball_Spell frostfire3 = new Fireball_Spell();
        frostfire3.frostbolts = true;
        frostfire3.anim_element = FROST;
        frostfire3.spellParts.add(FIREBALL);
        frostfire3.spellParts.add(FROSTBOLT);
        frostfireSpells[3] = frostfire3;

        // 4. Fireball + Icespear

        Icespear_Spell frostfire4 = new Icespear_Spell();
        frostfire4.fireball = true;
        frostfire4.anim_element = FROST;
        frostfire4.spellParts.add(FIREBALL);
        frostfire4.spellParts.add(ICESPEAR);
        frostfireSpells[4] = frostfire4;

        // 5. Fireball + Frozenorb

        Fireball_Spell frostfire5 = new Fireball_Spell();
        frostfire5.frozenorb = true;
        frostfire5.anim_element = FROST;
        frostfire5.spellParts.add(FIREBALL);
        frostfire5.spellParts.add(FROZENORB);
        frostfireSpells[5] = frostfire5;

        // 6. Overheat + Frostbolts

        Overheat_Spell frostfire6 = new Overheat_Spell();
        frostfire6.frostbolts = true;
        frostfire6.anim_element = FIRE;
        frostfire6.spellParts.add(OVERHEAT);
        frostfire6.spellParts.add(FROSTBOLT);
        frostfireSpells[6] = frostfire6;

        // 7. Overheat + Icespear

        Overheat_Spell frostfire7 = new Overheat_Spell();
        frostfire7.nested_spell = new Icespear_Spell();
        frostfire7.anim_element = FIRE;
        frostfire7.spellParts.add(OVERHEAT);
        frostfire7.spellParts.add(ICESPEAR);
        frostfireSpells[7] = frostfire7;

        // 8. Overheat + FrozenOrb

        FrostNova_Spell frostfire8 = new FrostNova_Spell();
        frostfire8.spellParts.add(OVERHEAT);
        frostfire8.spellParts.add(FROZENORB);
        frostfireSpells[8] = frostfire8;

        // 9. Fireball + Flamejet + Frostbolts

        Fireball_Spell frostfire9 = new Fireball_Spell();
        Flamejet_Spell proj9 = new Flamejet_Spell();
        proj9.frostbolts = true;
        frostfire9.nested_spell = proj9;
        frostfire9.anim_element = FROST;
        frostfire9.spellParts.add(FIREBALL);
        frostfire9.spellParts.add(FLAMEJET);
        frostfire9.spellParts.add(FROSTBOLT);
        frostfireSpells[9] = frostfire9;

        // 10. Fireball + Flamejet + Icespear

        Icespear_Spell frostfire10 = new Icespear_Spell();
        frostfire10.fireball = true;
        frostfire10.flamejet = true;
        frostfire10.anim_element = FIRE;
        frostfire10.spellParts.add(FIREBALL);
        frostfire10.spellParts.add(FLAMEJET);
        frostfire10.spellParts.add(ICESPEAR);
        frostfireSpells[10] = frostfire10;

        // 11. Fireball + Flamejet + Frozenorb

        Fireball_Spell frostfire11 = new Fireball_Spell();
        frostfire11.anim_element = FIRE;
        Frozenorb_Spell orb11 = new Frozenorb_Spell();
        orb11.nested_spell = new Flamejet_Spell();
        frostfire11.nested_spell = orb11;
        frostfire11.spellParts.add(FIREBALL);
        frostfire11.spellParts.add(FLAMEJET);
        frostfire11.spellParts.add(FROZENORB);
        frostfireSpells[11] = frostfire11;

        // 12. Fireball + Overheat + Frostbolts

        Overheat_Spell frostfire12 = new Overheat_Spell();
        frostfire12.anim_element = FROST;
        frostfire12.fireball = true;
        frostfire12.frostbolts = true;
        frostfire12.spellParts.add(FIREBALL);
        frostfire12.spellParts.add(OVERHEAT);
        frostfire12.spellParts.add(FROSTBOLT);
        frostfireSpells[12] = frostfire12;

        // 13. Fireball + Overheat + Icespear

        Icespear_Spell frostfire13 = new Icespear_Spell();
        frostfire13.anim_element = FIRE;
        frostfire13.overheat = true;
        frostfire13.spellParts.add(FIREBALL);
        frostfire13.spellParts.add(OVERHEAT);
        frostfire13.spellParts.add(ICESPEAR);
        frostfireSpells[13] = frostfire13;

        // 14. Fireball + Overheat + Frozenorb

        Overheat_Spell frostfire14 = new Overheat_Spell();
        frostfire14.fireball = true;
        frostfire14.anim_element = FROST;
        frostfire14.frozenorb = true;
        frostfire14.spellParts.add(FIREBALL);
        frostfire14.spellParts.add(OVERHEAT);
        frostfire14.spellParts.add(FROZENORB);
        frostfireSpells[14] = frostfire14;

        // 15. Flamejet + Overheat + Frostbolts

        DragonBreath_Spell frostfire15 = new DragonBreath_Spell();
        frostfire15.frostbolts = true;
        frostfire15.anim_element = FROST;
        frostfire15.spellParts.add(FLAMEJET);
        frostfire15.spellParts.add(OVERHEAT);
        frostfire15.spellParts.add(FROSTBOLT);
        frostfireSpells[15] = frostfire15;

        // 16. Flamejet + Overheat + Icespear

        // 17. Flamejet + Overheat + Frozenorb

        DragonBreath_Spell frostfire17 = new DragonBreath_Spell();
        frostfire17.frozenorb = true;
        frostfire17.anim_element = FROST;
        frostfireSpells[17] = frostfire17;

        // 18. Flamejet + FrozenOrb + Frostbolts

        Frozenorb_Spell frostfire18 = new Frozenorb_Spell();
        Flamejet_Spell flame18 = new Flamejet_Spell();
        flame18.frostbolts = true;
        frostfire18.nested_spell = flame18;
        frostfire18.anim_element = FROST;
        frostfire18.spellParts.add(FLAMEJET);
        frostfire18.spellParts.add(FROZENORB);
        frostfire18.spellParts.add(FROSTBOLT);
        frostfireSpells[18] = frostfire18;

        // 19. Flamejet + Frozenorb + Icespear

        Frozenorb_Spell frostfire19 = new Frozenorb_Spell();
        Icespear_Spell spear19 = new Icespear_Spell();
        spear19.flamejet = true;
        frostfire19.anim_element = FIRE;
        frostfire19.nested_spell = spear19;
        frostfire19.spellParts.add(FLAMEJET);
        frostfire19.spellParts.add(FROZENORB);
        frostfire19.spellParts.add(ICESPEAR);
        frostfireSpells[19] = frostfire19;

        // 20. Overheat + Frozenorb + Icespear

        Frozenorb_Spell frostfire20 = new Frozenorb_Spell();
        frostfire20.nested_spell = new Icespear_Spell();
        frostfire20.frostnova = true;
        frostfire20.anim_element = FROST;
        frostfireSpells[20] = frostfire20;

        // 21. Overheat + Frozenorb + Frostbolts

        // 20. Overheat + Icespear + Frostbolts

        // 21. FrozenOrb + Icespear + Fireball

        // 22. Frozenorb + Frostbolts + Fireball

        // 23. Icespear + Frostbolts + Fireball


        for (Spell frostfireSpell : frostfireSpells) {

            if(frostfireSpell != null && frostfireSpell.bonus_element == null) {

                if (frostfireSpell.main_element == SpellUtils.Spell_Element.FIRE) {
                    frostfireSpell.bonus_element = FROST;
                } else {
                    frostfireSpell.bonus_element = SpellUtils.Spell_Element.FIRE;
                }

            }
        }
    }
}
