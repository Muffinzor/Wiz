package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class Arcane_Spells {

    public static Spell[] arcanespells = new Spell[7];

    public static void createArcane_Spells() {

        ArcaneMissile_Spell arcane0 = new ArcaneMissile_Spell();
        arcane0.anim_element = ARCANE;
        arcane0.spellParts.add(MISSILES);
        arcanespells[0] = arcane0;

        EnergyBeam_Spell arcane1 = new EnergyBeam_Spell();
        arcane1.anim_element = ARCANE;
        arcane1.spellParts.add(BEAM);
        arcanespells[1] = arcane1;

        Rifts_Spell arcane2 = new Rifts_Spell();
        arcane2.anim_element = ARCANE;
        arcane2.spellParts.add(RIFTS);
        arcanespells[2] = arcane2;

        Laser_Spell arcane3 = new Laser_Spell();
        arcane3.anim_element = ARCANE;
        arcane3.spellParts.add(MISSILES);
        arcane3.spellParts.add(BEAM);
        arcanespells[3] = arcane3;

        ArcaneMissile_Spell arcane4 = new ArcaneMissile_Spell();
        arcane4.rift = true;
        arcane4.spellParts.add(MISSILES);
        arcane4.spellParts.add(RIFTS);
        arcane4.anim_element = ARCANE;
        arcanespells[4] = arcane4;

        EnergyRain_Spell arcane5 = new EnergyRain_Spell();
        arcane5.anim_element = ARCANE;
        arcane5.spellParts.add(RIFTS);
        arcane5.spellParts.add(BEAM);
        arcanespells[5] = arcane5;

        Laser_Spell arcane6 = new Laser_Spell();
        arcane6.anim_element = ARCANE;
        arcane6.rifts = true;
        arcane6.spellParts.add(RIFTS);
        arcane6.spellParts.add(MISSILES);
        arcane6.spellParts.add(BEAM);
        arcanespells[6] = arcane6;

    }
}
