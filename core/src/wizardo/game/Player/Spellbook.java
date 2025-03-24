package wizardo.game.Player;

import wizardo.game.Spells.Spell;

import java.util.ArrayList;

import static wizardo.game.Spells.SpellBank.Frost_Spells.frost_spells;

public class Spellbook {

    public ArrayList<Spell> equippedSpells;
    public ArrayList<Spell> knownSpells;
    public Spell defensive_spell;
    public Spell utility_spell;

    public int frostbolt_lvl = 1;
    public int icespear_lvl = 0;
    public int frozenorb_lvl = 0;

    public int fireball_lvl = 0;
    public int flamejet_lvl = 0;
    public int overheat_lvl = 0;

    public int chainlightning_lvl = 0;
    public int chargedbolt_lvl = 0;
    public int thunderstorm_lvl = 0;

    public int arcanemissile_lvl = 0;
    public int energybeam_lvl = 0;
    public int rift_lvl = 0;

    // FLAMEJET
    public int flamejet_bonus_dmg;
    public int flamejet_bonus_flames;

    // FIREBALL
    public int fireball_bonus_dmg;
    public int fireball_bonus_radius;
    public int fireball_bonus_knockback;

    // OVERHEAT
    public int overheat_bonus_dmg;
    public int overheat_bonus_cdreduction;
    public int overheat_bonus_fullhpdmg;

    // CHARGEDBOLTS
    public int chargedbolts_bonus_dmg;
    public int chargedbolts_bonus_proj;
    public int chargedbolts_bonus_duration;

    // CHAIN LIGHTNING
    public int chainlightning_bonus_dmg;
    public int chainlightning_bonus_jump;
    public int chainlightning_bonus_crit;

    // THUNDERSTORM
    public int thunderstorm_bonus_dmg;
    public int thunderstorm_bonus_duration;
    public int thunderstorm_bonus_splash;

    // ARCANE MISSILES
    public int arcane_missile_bonus_dmg;
    public int arcane_missile_bonus_proj;
    public int arcane_missile_bonus_rotation;

    // ENERGY BEAM
    public int energybeam_bonus_dmg;
    public int energybeam_bonus_width;
    public int energybeam_bonus_firsthits;

    // RIFTS
    public int rifts_bonus_dmg;
    public int rifts_bonus_spread;
    public int rifts_bonus_quantity;

    // FROSTBOLTS
    public int frostbolts_bonus_dmg;
    public int frostbolts_bonus_proj;
    public int frostbolts_bonus_splash;

    // ICESPEAR
    public int icespear_bonus_dmg;
    public int icespear_bonus_shard;
    public int icespear_bonus_split_chance;

    // FROZENORB
    public int frozenorb_bonus_dmg;
    public int frozenorb_bonus_radius;
    public int frozenorb_bonus_proj_quantity;

    // --------- HYBRID SPELLS --------- //

    // JUDGEMENT
    public int judgement_bonus_cdreduction;  // increment of 0.4s removed per
    public int judgement_bonus_dmg;

    // BLIZZARD
    public int blizzard_bonus_dmg;
    public int blizzard_bonus_duration; // increment of 0.5s per
    public int blizzard_bonus_splash_chance;

    // CELESTIAL STRIKE
    public int celestialstrike_bonus_dmg;
    public int celestialstrike_bonus_cdreduction;
    public int celestialstrike_bonus_freezechance;

    // DRAGONBREATH
    public int dragonbreath_bonus_dmg;
    public int dragonbreath_bonus_burndmg;
    public int dragonbreath_bonus_burnduration; // increment of 1s

    // ENERGY RAIN
    public int energyrain_bonus_dmg;
    public int energyrain_bonus_proj;
    public int energyrain_bonus_radius;

    // LIGHTNING HANDS
    public int lightninghands_bonus_dmg;
    public int lightninghands_bonus_branch;
    public int lightninghands_reducedcd;

    // FORKED LIGHTNING
    public int forkedlightning_bonus_dmg;

    // FROST NOVA
    public int frostnova_bonus_dmg;

    // LASERS
    public int lasers_bonus_dmg;

    // METEORS
    public int meteors_bonus_dmg;

    // ORBIT
    public int orbit_bonus_dmg;

    // REPULSION FIELD
    public int repulsion_bonus_dmg;



    // ELEMENTS
    public int frost_bonus_cdreduction;
    public int frost_bonus_multicast;
    public int fire_bonus_cdreduction;
    public int fire_bonus_multicast;
    public int lightning_bonus_cdreduction;
    public int lightning_bonus_multicast;
    public int arcane_bonus_cdreduction;
    public int arcane_bonus_multicast;

    // Elemental Dmg
    public float arcaneBonusDmg;
    public float frostBonusDmg;
    public float fireBonusDmg;
    public float lightningBonusDmg;
    public float allBonusDmg;

    // LevelUp Mastery Modifiers
    public int chargedboltBonus;
    public int frostboltBonus;
    public int arcanemissileBonus;
    
    // Family Dmg
    public int explosivesBonusDmg;
    public int flashBonusDmg;
    public int energyBonusDmg;
    public int gravityBonusDmg;
    public int sharpBonusDmg;
    public int conductiveBonusDmg;
    public float divineBonusDmg;

    // Wide Modifiers
    public int empyreanFrequencyBonus;
    public int pushbackBonus;
    public int iceRadiusBonus;
    public int projSpeedBonus;

    public float castSpeed;
    public float multicast;
    
    // Specific Modifiers
    public int repulsionRadiusBonus;
    public int orbitingIceRadius;
    public float orbitSpeed;
    public int riftPullBonus;
    public int flamejetBonus;
    public int energybeamBonus;
    public int chainlightningBonus;
    public int icespearBonus;
    public int fireballBonus;

    public Spellbook() {
        equippedSpells = new ArrayList<>();
        knownSpells = new ArrayList<>();
        equippedSpells.add(frost_spells[0]);
    }

}
