package wizardo.game.Screens.LevelUp;

import wizardo.game.Player.Levels.LevelUpEnums;

import static wizardo.game.Wizardo.player;

public class TextGenerator {

    public static String getPanelText(LevelUpEnums.LevelUps type, LevelUpEnums.LevelUpQuality quality) {
        String s = "";

        switch(type) {
            case CRYSTAL -> s = crystal(quality);
            case ENERGY -> s = energy(quality);
            case EMPYREAN -> s = empyrean(quality);
            case EXPLOSIVES -> s = explosives(quality);
            case FLARE -> s = flare(quality);
            case FORCE -> s = force(quality);
            case GRAVITY -> s = gravity(quality);
            case LUCK -> s = luck(quality);
            case PROJECTILES -> s = projectilesSpeed(quality);
            case RED -> s = red(quality);
            case REGEN -> s = regen(quality);
            case SHARP -> s = sharp(quality);
            case VOLTAGE -> s = voltage(quality);

            case FROSTBOLT -> s = frostbolt();
            case FLAMEJET -> s = flamejet();
            case CHARGEDBOLT -> s = chargedbolt();
            case MISSILES -> s = missiles();
            case ICESPEAR -> s = icespear();
            case FIREBALL -> s = fireball();
            case CHAIN -> s = chain();
            case BEAM -> s = beam();
        }

        return s;
    }
    private static String flamejet() {
        if(player.spellbook.flamejet_lvl < 5) {
            return "+1 Flamejet Mastery";
        } else {
            int current = player.spellbook.flamejetBonus;
            String text = String.format("""
            FLAMEJET
            
            Applies a stacking debuff that increases damage taken by flamejet by 4
            +2 to maximum stacks
            
            Current maximum: %d
            """, current);
            return text;
        }
    }
    private static String frostbolt() {
        if(player.spellbook.frostbolt_lvl < 5) {
            return "+1 Frostbolts Mastery";
        } else {
            int current = player.spellbook.frostboltBonus;
            String text = String.format("""
            FROSTBOLTS
            
            +8%% chance to freeze
            
            Current chance: %d%%
            """, current);
            return text;
        }
    }
    private static String chargedbolt() {
        if(player.spellbook.chargedbolt_lvl < 5) {
            return "+1 Chargedbolts Mastery";
        } else {
            int current = player.spellbook.chargedboltBonus;
            String text = String.format("""
            CHARGEDBOLTS
            
            Bolts gain a 8%% chance to generate another
            
            Current chance: %d%%
            """, current);
            return text;
        }
    }
    private static String missiles() {
        if(player.spellbook.arcanemissile_lvl < 5) {
            return "+1 Arcane Missiles Mastery";
        } else {
            String text = String.format("""
            ARCANE MISSILES
            
            -8%% duration loss per hit
            """);
            return text;
        }
    }
    private static String fireball() {
        if(player.spellbook.fireball_lvl < 5) {
            return "+1 Fireball Mastery";
        } else {
            String text = String.format("""
            FIREBALL
            
            8%% increased explosion radius
            """);
            return text;
        }
    }
    private static String icespear() {
        if(player.spellbook.icespear_lvl < 5) {
            return "+1 Icespear Mastery";
        } else {
            String text = String.format("""
            ICESPEAR
            
            8%% chance to break into
            an additional shard
            """);
            return text;
        }
    }
    private static String beam() {
        if(player.spellbook.energybeam_lvl < 5) {
            return "+1 Energy Beam Mastery";
        } else {
            int current = player.spellbook.energybeamBonus;
            String text = String.format("""
            ENERGY BEAM
            
            deals 20%% more damage to
            the first monster it hits
            
            Current bonus: %d
            """, current);
            return text;
        }
    }
    private static String chain() {
        if(player.spellbook.chainlightning_lvl < 5) {
            return "+1 Chain Lightning Mastery";
        } else {
            int current = player.spellbook.chainlightningBonus;
            String text = String.format("""
            CHAIN LIGHTNING
            
            +6%% chance to deal double damage
            
            Current chance: %d
            """, current);
            return text;
        }
    }



    private static String crystal(LevelUpEnums.LevelUpQuality quality) {

        int quantity = 0;
        switch(quality) {
            case NORMAL -> quantity = 10;
            case RARE -> quantity = 15;
            case EPIC -> quantity = 20;
            case LEGENDARY -> quantity = 30;
        }

        String text = String.format("""
            CRYSTALIZED DEFENSES
            
            +%d to Maximum shield
            """, quantity);

        return text;
    }
    private static String energy(LevelUpEnums.LevelUpQuality quality) {

        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            AMPLIFIED ENERGY
            
            +%d %% Energy spell damage
            
            Energy spells :
            Energy Beam, Energy Rain,
            Laser
            """, dmg);

        return text;
    }
    private static String empyrean(LevelUpEnums.LevelUpQuality quality) {

        int frequency = 0;
        switch(quality) {
            case NORMAL -> frequency = 4;
            case RARE -> frequency = 6;
            case EPIC -> frequency = 8;
            case LEGENDARY -> frequency = 12;
        }

        String text = String.format("""
            HARSH SKIES
            
            +%d %% Empyrean spell
            projectile frequency
            
            Empyrean spells :
            Thunderstorm, Energy Rain
            Blizzard, Meteor Shower
            """, frequency);

        return text;
    }
    private static String explosives(LevelUpEnums.LevelUpQuality quality) {

        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            ELEMENTAL IMPACT
            
            +%d %% Explosive spell damage
            
            Explosive spells :
            Fireball, Frostbolts
            Overheat, Meteor Shower
            """, dmg);

        return text;
    }
    private static String flare(LevelUpEnums.LevelUpQuality quality) {

        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            SEARING FLASH
            
            +%d %% Flash spell damage
            
            Flash spells :
            Flamejet, Thunderstorm
            Lightning Hands, Dragonbreath
            """, dmg);

        return text;
    }
    private static String force(LevelUpEnums.LevelUpQuality quality) {
        int value = 0;
        switch(quality) {
            case EPIC -> value = 12;
            case LEGENDARY -> value = 20;
        }

        String text = String.format("""
            STRONG FORCE
            
            +%d %% Monster pushback
            
            Force spells :
            Overheat, Frostnova,
            Repulsion Field
            """, value);

        return text;
    }
    private static String gravity(LevelUpEnums.LevelUpQuality quality) {
        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            FUNDAMENTAL MAGIC
            
            +%d %% Fundamental spell damage
            
            Gravity spells :
            Frozen Orb, Orbiting Ice,
            Rifts, Repulsion Field
            """, dmg);

        return text;
    }
    private static String ice(LevelUpEnums.LevelUpQuality quality) {
        int radius = 0;
        switch(quality) {
            case NORMAL -> radius = 4;
            case RARE -> radius = 6;
            case EPIC -> radius = 8;
            case LEGENDARY -> radius = 12;
        }

        String text = String.format("""
            CREEPING FROST
            
            +%d %% Frozen spell radius
            
            Frozen spells :
            Frozen Orb, Orbiting Ice,
            Frost Nova
            """, radius);

        return text;
    }
    private static String luck(LevelUpEnums.LevelUpQuality quality) {
        int value = 0;
        switch(quality) {
            case EPIC -> value = 10;
            case LEGENDARY -> value = 20;
        }

        String text = String.format("""
            STAR ALIGNED
            
            +%d %% Pickup radius
            +%d Luck
            
            Luck increases gold found and loot quality
            """, value, value/2);

        return text;
    }
    private static String projectilesSpeed(LevelUpEnums.LevelUpQuality quality) {
        int bonus = 0;
        switch(quality) {
            case NORMAL -> bonus = 4;
            case RARE -> bonus = 6;
            case EPIC -> bonus = 8;
            case LEGENDARY -> bonus = 12;
        }

        String text = String.format("""
            HIGH VELOCITY
            
            +%d %% Projectile speed
            
            Projectile Spells :
            Frostbolts, Ice Spear, Fireball, Arcane Missiles, Chargedbolts
            """, bonus);

        return text;
    }

    private static String red(LevelUpEnums.LevelUpQuality quality) {

        int value = 0;
        switch(quality) {
            case EPIC -> value = 2;
            case LEGENDARY -> value = 3;
        }

        String text = String.format("""
            THICK ROBES
            
            -%d Damage received
            
            Current damage reduction: %d
            """, value, player.stats.damageReduction);

        return text;

    }
    private static String regen(LevelUpEnums.LevelUpQuality quality) {

        int regen = 0;
        switch(quality) {
            case NORMAL -> regen = 8;
            case RARE -> regen = 12;
            case EPIC -> regen = 16;
            case LEGENDARY -> regen = 24;
        }

        float currentRate = player.stats.baseRecharge * (1 + player.stats.bonusRechargeRate/100f);

        String text = String.format("""
            WIZARD'S RESOLVE
            
            +%d %% Shield recharge rate
            
            Current:
            %d %% Bonus recharge
            %.2f Shield per second
            """, regen, player.stats.bonusRechargeRate, currentRate);

        return text;

    }
    private static String sharp(LevelUpEnums.LevelUpQuality quality) {

        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            PIERCING WORDS
            
            +%d %% Sharp spell damage
            
            Sharp spells :
            Ice Spear, Arcane Missiles,
            Blizzard
            """, dmg);

        return text;

    }
    private static String space(LevelUpEnums.LevelUpQuality quality) {

        int value = 0;
        switch(quality) {
            case NORMAL -> value = 6;
            case RARE -> value = 10;
            case EPIC -> value = 14;
            case LEGENDARY -> value = 20;
        }

        String text = String.format("""
            SPACE FOLDING
            
            +%d %% Repulsion Field radius
            +%d %% Rifts pulling strength
            +%d %% Orbiting Ice orbit speed and radius
            """, value, value, value/2);

        return text;

    }
    private static String voltage(LevelUpEnums.LevelUpQuality quality) {

        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            HIGH VOLTAGE
            
            +%d %% Conductive spell damage
             
            Conductive spells :
            Chargedbolts, Chain Lightning,
            Forked Lightning
            """, dmg);

        return text;
    }

}
