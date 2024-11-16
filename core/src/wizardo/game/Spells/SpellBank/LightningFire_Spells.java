package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.ArcaneArtillery.ArcaneArtillery_Spell;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Projectile;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Spells.Hybrid.ForkedLightning.ForkedLightning_Spell;
import wizardo.game.Spells.Hybrid.FrostNova.FrostNova_Spell;

import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Hybrid.MeteorShower.MeteorShower_Spell;
import wizardo.game.Spells.Hybrid.MeteorShower.Meteor_Explosion;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;

public class LightningFire_Spells {

    public static Spell[] litefireSpells = new Spell[27];

    public static void createLightningFire_Spells() {

        for (Spell spell : litefireSpells) {

            // 0. Flamejet + Chargedbolt

            ChargedBolts_Spell litefire0 = new ChargedBolts_Spell();
            litefire0.flamejet = true;
            litefire0.anim_element = FIRE;
            litefire0.spellParts.add(FLAMEJET);
            litefire0.spellParts.add(CHARGEDBOLTS);
            litefireSpells[0] = litefire0;

            // 1. Flamejet + Chainlightning

            ForkedLightning_Spell litefire1 = new ForkedLightning_Spell();
            litefire1.anim_element = FIRE;
            litefire1.spellParts.add(FLAMEJET);
            litefire1.spellParts.add(CHAIN);
            litefireSpells[1] = litefire1;

            // 2. Flamejet + Thunderstorm

            Thunderstorm_Spell litefire2 = new Thunderstorm_Spell();
            litefire2.anim_element = FIRE;
            litefire2.nested_spell = new Flamejet_Spell();
            litefire2.spellParts.add(FLAMEJET);
            litefire2.spellParts.add(THUNDERSTORM);
            litefireSpells[2] = litefire2;

            // 3. Fireball + Chargedbolt

            Fireball_Spell litefire3 = new Fireball_Spell();
            litefire3.anim_element = LIGHTNING;
            litefire3.nested_spell = new ChargedBolts_Spell();
            litefire3.spellParts.add(FIREBALL);
            litefire3.spellParts.add(CHARGEDBOLTS);
            litefireSpells[3] = litefire3;

            // 4. Fireball + ChainLightning

            ChainLightning_Spell litefire4 = new ChainLightning_Spell();
            litefire4.fireball = true;
            litefire4.anim_element = LIGHTNING;
            litefire4.spellParts.add(FIREBALL);
            litefire4.spellParts.add(CHAIN);
            litefireSpells[4] = litefire4;

            // 5. Fireball + Thunderstorm

            MeteorShower_Spell litefire5 = new MeteorShower_Spell();
            litefire5. anim_element = FIRE;
            litefire5.thunderstorm = false;
            litefire5.spellParts.add(FIREBALL);
            litefire5.spellParts.add(THUNDERSTORM);
            litefireSpells[5] = litefire5;

            // 6. Overheat + Chargedbolt

            // 7. Overheat + Chainlighting

            // 8. Overheat + Thunderstorm

            // 9. Flamejet + Chargedbolts + Chainlightning

            ForkedLightning_Spell litefire9 = new ForkedLightning_Spell();
            litefire9.anim_element = FIRE;
            litefire9.chargedbolts = true;
            litefire9.spellParts.add(FLAMEJET);
            litefire9.spellParts.add(CHAIN);
            litefire9.spellParts.add(CHARGEDBOLTS);
            litefireSpells[9] = litefire9;

            // 10. Flamejet + Chargedbolts + Thunderstorm

            // 11. Flamejet + ChainLighting + Thunderstorm

            Thunderstorm_Spell litefire11 = new Thunderstorm_Spell();
            litefire11.anim_element = FIRE;
            litefire11.nested_spell = litefire1;
            litefire11.spellParts.add(FLAMEJET);
            litefire11.spellParts.add(CHAIN);
            litefire11.spellParts.add(THUNDERSTORM);
            litefireSpells[11] = litefire11;


            // 12. Fireball + Chargedbolts + Chainlightning

            // 13. Fireball + Chargedbolts + Thunderstorm

            MeteorShower_Spell litefire13 = new MeteorShower_Spell();
            litefire13.anim_element = LIGHTNING;
            litefire13.nested_spell = new ChargedBolts_Spell();
            litefire13.thunderstorm = true;
            litefire13.spellParts.add(FIREBALL);
            litefire13.spellParts.add(CHARGEDBOLTS);
            litefire13.spellParts.add(THUNDERSTORM);
            litefireSpells[13] = litefire13;

            // 14. Fireball + Chainlightning + Thunderstorm

            if(spell != null && spell.bonus_element == null) {

                if (spell.main_element == SpellUtils.Spell_Element.LIGHTNING) {
                    spell.bonus_element = FIRE;
                } else {
                    spell.bonus_element = SpellUtils.Spell_Element.LIGHTNING;
                }

            }
        }
    }
}
