package wizardo.game.Screens.LevelUp;

import static wizardo.game.Wizardo.player;

public class TextGenerator {

    public static String getPanelText(LevelUpUtils.LevelUps type, LevelUpUtils.LevelUpQuality quality) {
        String s = "";

        switch(type) {
            case CRYSTAL -> s = crystal(quality);
            case ENERGY -> s = energy(quality);
            case EMPYREAN -> s = empyrean(quality);
            case EXPLOSIVES -> s = explosives(quality);
            case FLARE -> s = flare(quality);
            case FORCE -> s = force(quality);
            case GRAVITY -> s = gravity(quality);
            case ICE -> s = ice(quality);
            case LUCK -> s = luck(quality);
            case PROJECTILES -> s = projectilesSpeed(quality);
            case REGEN -> s = regen(quality);
            case SHARP -> s = sharp(quality);
            case SPACE -> s = space(quality);
            case VOLTAGE -> s = voltage(quality);
        }

        return s;
    }

    private static String crystal(LevelUpUtils.LevelUpQuality quality) {

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
    private static String energy(LevelUpUtils.LevelUpQuality quality) {

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
            Energy Beam, Arcane Rain,
            Laser, Judgement
            """, dmg);

        return text;
    }
    private static String empyrean(LevelUpUtils.LevelUpQuality quality) {

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
            Thunderstorm, Arcane Rain
            Blizzard, Meteor Shower
            """, frequency);

        return text;
    }
    private static String explosives(LevelUpUtils.LevelUpQuality quality) {

        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            STRONG REACTIONS
            
            +%d %% Explosive spell damage
            
            Explosive spells :
            Fireball, Frostbolts
            Overheat, Meteor Shower
            """, dmg);

        return text;
    }
    private static String flare(LevelUpUtils.LevelUpQuality quality) {

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
            Flamejet, Forked Lightning,
            Lightning Hands, Dragonbreath
            """, dmg);

        return text;
    }
    private static String force(LevelUpUtils.LevelUpQuality quality) {
        int dmg = 0;
        switch(quality) {
            case EPIC -> dmg = 8;
            case LEGENDARY -> dmg = 12;
        }

        String text = String.format("""
            STRONG FORCE
            
            +%d %% Monster pushback
            
            Force spells :
            Fireball, Overheat,
            Repulsion Field
            """, dmg);

        return text;
    }
    private static String gravity(LevelUpUtils.LevelUpQuality quality) {
        int dmg = 0;
        switch(quality) {
            case NORMAL -> dmg = 10;
            case RARE -> dmg = 15;
            case EPIC -> dmg = 20;
            case LEGENDARY -> dmg = 30;
        }

        String text = String.format("""
            FUNDAMENTAL MAGIC
            
            +%d %% Gravity spell damage
            
            Gravity spells :
            Frozen Orb, Orbiting Ice,
            Rifts, Repulsion Field
            """, dmg);

        return text;
    }
    private static String ice(LevelUpUtils.LevelUpQuality quality) {
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
    private static String luck(LevelUpUtils.LevelUpQuality quality) {
        int value = 0;
        switch(quality) {
            case EPIC -> value = 10;
            case LEGENDARY -> value = 20;
        }

        String text = String.format("""
            STAR ALIGNED
            
            +%d %% Pickup radius
            +%d Luck
            
            Force spells :
            Fireball, Overheat,
            Repulsion Field
            """, value, value/2);

        return text;
    }
    private static String projectilesSpeed(LevelUpUtils.LevelUpQuality quality) {
        int bonus = 0;
        switch(quality) {
            case NORMAL -> bonus = 4;
            case RARE -> bonus = 6;
            case EPIC -> bonus = 8;
            case LEGENDARY -> bonus = 12;
        }

        String text = String.format("""
            APPLIED MASS
            
            +%d %% Projectile speed
            
            Projectile Spells :
            Frostbolts, Ice Spear, Fireball, Arcane Missiles, Chargedbolts, Orbiting Ice
            """, bonus);

        return text;
    }
    private static String regen(LevelUpUtils.LevelUpQuality quality) {

        int regen = 0;
        switch(quality) {
            case NORMAL -> regen = 6;
            case RARE -> regen = 9;
            case EPIC -> regen = 12;
            case LEGENDARY -> regen = 18;
        }

        float currentRate = player.stats.baseRecharge * (1 + player.stats.bonusRechargeRate/100f);

        String text = String.format("""
            WIZARD'S RESOLVE
            
            +%d %% Shield recharge rate
            
            Current:
            %d %% Bonus recharge
            %.1f Shield per second
            """, regen, player.stats.bonusRechargeRate, currentRate);

        return text;

    }
    private static String sharp(LevelUpUtils.LevelUpQuality quality) {

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
    private static String space(LevelUpUtils.LevelUpQuality quality) {

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
            +%d %% Orbiting Ice orbit radius
            +%d %% Rifts pulling strength
            """, value, value, value/2);

        return text;

    }
    private static String voltage(LevelUpUtils.LevelUpQuality quality) {

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
            Thunderstorm, Celestial Strike
            """, dmg);

        return text;

    }

}
