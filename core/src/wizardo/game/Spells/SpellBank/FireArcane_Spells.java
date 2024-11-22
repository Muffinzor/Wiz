package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class FireArcane_Spells {

    public static Spell[] firearcaneSpells = new Spell[27];

    public static void createFireArcane_Spells() {

        // 0. ArcaneMissile + Flamejet

        Flamejet_Spell firearcane0 = new Flamejet_Spell();
        firearcane0.anim_element = ARCANE;
        firearcane0.arcaneMissile = true;
        firearcane0.spellParts.add(MISSILES);
        firearcane0.spellParts.add(FLAMEJET);
        firearcaneSpells[0] = firearcane0;

        // 1. ArcaneMissile + Fireball

        Fireball_Spell firearcane1 = new Fireball_Spell();
        firearcane1.anim_element = ARCANE;
        firearcane1.nested_spell = new ArcaneMissile_Spell();
        firearcane1.spellParts.add(MISSILES);
        firearcane1.spellParts.add(FIREBALL);
        firearcaneSpells[1] = firearcane1;

        // 2. ArcaneMissile + Overheat

        // 3. EnergyBeam + Flamejet

        // 4. EnergyBeam + Fireball

        // 5. EnergyBeam + Overheat

        // 6. Rifts + Flamejet

        // 7. Rifts + Fireball

        // 8. Rifts + Overheat

        for (Spell firearcane : firearcaneSpells) {

            if(firearcane != null && firearcane.bonus_element == null) {

                if (firearcane.main_element == SpellUtils.Spell_Element.ARCANE) {
                    firearcane.bonus_element = FIRE;
                } else {
                    firearcane.bonus_element = SpellUtils.Spell_Element.ARCANE;
                }

            }
        }

    }
}
