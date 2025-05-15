package wizardo.game.Spells.SpellBank;

import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Hybrid.ForkedLightning.ForkedLightning_Spell;

import wizardo.game.Spells.Hybrid.LightningHands.LightningHands_Spell;
import wizardo.game.Spells.Hybrid.MeteorShower.MeteorShower_Spell;
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
            litefire0.spellParts.add(CHARGEDBOLT);
            litefireSpells[0] = litefire0;

            // 1. Flamejet + Chainlightning

            ForkedLightning_Spell litefire1 = new ForkedLightning_Spell();
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
            litefire3.spellParts.add(CHARGEDBOLT);
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
            litefire5.anim_element = LIGHTNING;
            litefire5.thunderstorm = true;
            litefire5.spellParts.add(FIREBALL);
            litefire5.spellParts.add(THUNDERSTORM);
            litefireSpells[5] = litefire5;

            // 6. Overheat + Chargedbolt

            ChargedBolts_Spell litefire6 = new ChargedBolts_Spell();
            litefire6.anim_element = FIRE;
            litefire6.overheat = true;
            litefire6.spellParts.add(OVERHEAT);
            litefire6.spellParts.add(CHARGEDBOLT);
            litefireSpells[6] = litefire6;

            // 7. Overheat + Chainlighting

            Overheat_Spell litefire7 = new Overheat_Spell();
            litefire7.anim_element = LIGHTNING;
            litefire7.chainlightning = true;
            litefire7.spellParts.add(OVERHEAT);
            litefire7.spellParts.add(CHAIN);
            litefireSpells[7] = litefire7;

            // 8. Overheat + Thunderstorm

            Overheat_Spell litefire8 = new Overheat_Spell();
            litefire8.anim_element = LIGHTNING;
            litefire8.thunderstorm = true;
            litefire8.spellParts.add(OVERHEAT);
            litefire8.spellParts.add(THUNDERSTORM);
            litefireSpells[8] = litefire8;

            // 9. Flamejet + Chargedbolts + Chainlightning

            ForkedLightning_Spell litefire9 = new ForkedLightning_Spell();
            litefire9.chargedbolts = true;
            litefire9.spellParts.add(FLAMEJET);
            litefire9.spellParts.add(CHAIN);
            litefire9.spellParts.add(CHARGEDBOLT);
            litefireSpells[9] = litefire9;

            // 10. Flamejet + Chargedbolts + Thunderstorm

            Thunderstorm_Spell litefire10 = new Thunderstorm_Spell();
            litefire10.nested_spell = litefire0;
            litefire10.anim_element = FIRE;
            litefire10.spellParts.add(FLAMEJET);
            litefire10.spellParts.add(CHARGEDBOLT);
            litefire10.spellParts.add(THUNDERSTORM);
            litefireSpells[10] = litefire10;

            // 11. Flamejet + ChainLighting + Thunderstorm

            Thunderstorm_Spell litefire11 = new Thunderstorm_Spell();
            litefire11.anim_element = FIRE;
            litefire11.forkedlightning = true;
            litefire11.spellParts.add(FLAMEJET);
            litefire11.spellParts.add(CHAIN);
            litefire11.spellParts.add(THUNDERSTORM);
            litefireSpells[11] = litefire11;

            // 12. Fireball + Chargedbolts + Chainlightning

            ChainLightning_Spell litefire12 = new ChainLightning_Spell();
            litefire12.fireball = true;
            litefire12.nested_spell = new ChargedBolts_Spell();
            litefire12.anim_element = LIGHTNING;
            litefire12.spellParts.add(FIREBALL);
            litefire12.spellParts.add(CHARGEDBOLT);
            litefire12.spellParts.add(CHAIN);
            litefireSpells[12] = litefire12;

            // 13. Fireball + Chargedbolts + Thunderstorm

            MeteorShower_Spell litefire13 = new MeteorShower_Spell();
            litefire13.anim_element = LIGHTNING;
            litefire13.nested_spell = new ChargedBolts_Spell();
            litefire13.thunderstorm = true;
            litefire13.spellParts.add(FIREBALL);
            litefire13.spellParts.add(CHARGEDBOLT);
            litefire13.spellParts.add(THUNDERSTORM);
            litefireSpells[13] = litefire13;

            // 14. Fireball + Chainlightning + Thunderstorm

            Fireball_Spell litefire14 = new Fireball_Spell();
            litefire14.chainThunder = true;
            litefire14.anim_element = LIGHTNING;
            litefire14.spellParts.add(FIREBALL);
            litefire14.spellParts.add(CHAIN);
            litefire14.spellParts.add(THUNDERSTORM);
            litefireSpells[14] = litefire14;

            // 15. Overheat + Chargedbolts + Chainlightning

            Overheat_Spell litefire15 = new Overheat_Spell();
            litefire15.anim_element = LIGHTNING;
            litefire15.chainlightning = true;
            litefire15.chargedbolts = true;
            litefire15.spellParts.add(OVERHEAT);
            litefire15.spellParts.add(CHAIN);
            litefire15.spellParts.add(CHARGEDBOLT);
            litefireSpells[15] = litefire15;


            // 16. Overheat + Chargedbolts + Thunderstorm

            Thunderstorm_Spell litefire16 = new Thunderstorm_Spell();
            litefire16.anim_element = LIGHTNING;
            litefire16.nested_spell = new ChargedBolts_Spell();
            litefire16.overheat = true;
            litefire16.spellParts.add(OVERHEAT);
            litefire16.spellParts.add(CHARGEDBOLT);
            litefire16.spellParts.add(THUNDERSTORM);
            litefireSpells[16] = litefire16;

            // 17. Overheat + Chainlightning + Thunderstorm

            Overheat_Spell litefire17 = new Overheat_Spell();
            litefire17.anim_element = LIGHTNING;
            litefire17.thunderstorm = true;
            litefire17.chainlightning = true;
            litefire17.spellParts.add(THUNDERSTORM);
            litefire17.spellParts.add(CHAIN);
            litefire17.spellParts.add(OVERHEAT);
            litefireSpells[17] = litefire17;

            // 18. Flamejet + Fireball + Chargedbolts

            Fireball_Spell firelite18 = new Fireball_Spell();
            firelite18.nested_spell = litefire0;
            firelite18.flamejets = true;
            firelite18.anim_element = FIRE;
            firelite18.spellParts.add(FIREBALL);
            firelite18.spellParts.add(CHARGEDBOLT);
            firelite18.spellParts.add(FLAMEJET);
            litefireSpells[18] = firelite18;

            // 19. Flamejet + Fireball + Chainlightning

            ForkedLightning_Spell litefire19 = new ForkedLightning_Spell();
            litefire19.fireball = true;
            litefire19.spellParts.add(FIREBALL);
            litefire19.spellParts.add(CHAIN);
            litefire19.spellParts.add(FLAMEJET);
            litefireSpells[19] = litefire19;

            // 20. Flamejet + Fireball + Thunderstorm

            Fireball_Spell litefire20 = new Fireball_Spell();
            litefire20.nested_spell = new Flamejet_Spell();
            litefire20.flameThunder = true;
            litefire20.anim_element = FIRE;
            litefire20.spellParts.add(FIREBALL);
            litefire20.spellParts.add(FLAMEJET);
            litefire20.spellParts.add(THUNDERSTORM);
            litefireSpells[20] = litefire20;

            // 21. Flamejet + Overheat + Chargedbolts

            LightningHands_Spell litefire21 = new LightningHands_Spell();
            litefire21.chargedbolts = true;
            litefire21.spellParts.add(FLAMEJET);
            litefire21.spellParts.add(OVERHEAT);
            litefire21.spellParts.add(CHARGEDBOLT);
            litefireSpells[21] = litefire21;

            // 22. Flamejet + Overheat + Chainlightning

            LightningHands_Spell litefire22 = new LightningHands_Spell();
            litefire22.chainlightning = true;
            litefire22.spellParts.add(FLAMEJET);
            litefire22.spellParts.add(OVERHEAT);
            litefire22.spellParts.add(CHAIN);
            litefireSpells[22] = litefire22;

            // 23. FLamejet + Overheat + Thunderstorm

            LightningHands_Spell litefire23 = new LightningHands_Spell();
            litefire23.thunderstorm = true;
            litefire23.spellParts.add(FLAMEJET);
            litefire23.spellParts.add(OVERHEAT);
            litefire23.spellParts.add(THUNDERSTORM);
            litefireSpells[23] = litefire23;

            // 24. Fireball + Overheat + Chargedbolts

            Overheat_Spell litefire24 = new Overheat_Spell();
            litefire24.fireball = true;
            litefire24.nested_spell = new ChargedBolts_Spell();
            litefire24.anim_element = LIGHTNING;
            litefire24.spellParts.add(FIREBALL);
            litefire24.spellParts.add(OVERHEAT);
            litefire24.spellParts.add(CHARGEDBOLT);
            litefireSpells[24] = litefire24;

            // 25. Fireball + Overheat + Chainlightning

            Overheat_Spell litefire25 = new Overheat_Spell();
            litefire25.anim_element = LIGHTNING;
            litefire25.chainlightning = true;
            litefire25.fireball = true;
            litefire25.spellParts.add(FIREBALL);
            litefire25.spellParts.add(OVERHEAT);
            litefire25.spellParts.add(CHAIN);
            litefireSpells[25] = litefire25;

            // 26. Fireball + Overheat + Thunderstorm

            MeteorShower_Spell litefire26 = new MeteorShower_Spell();
            litefire26.anim_element = LIGHTNING;
            litefire26.thunderstorm = true;
            litefire26.overheat = true;
            litefire26.spellParts.add(FIREBALL);
            litefire26.spellParts.add(OVERHEAT);
            litefire26.spellParts.add(THUNDERSTORM);
            litefireSpells[26] = litefire26;

        }
    }
}
