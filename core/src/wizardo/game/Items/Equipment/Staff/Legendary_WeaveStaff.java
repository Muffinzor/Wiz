package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.Unique.ThundergodBolt.ThundergodBolt_Projectile;

import static wizardo.game.Spells.SpellBank.FireArcane_Spells.firearcaneSpells;
import static wizardo.game.Spells.SpellBank.FrostArcane_Spells.frostarcaneSpells;
import static wizardo.game.Spells.SpellBank.FrostFire_Spells.frostfireSpells;
import static wizardo.game.Spells.SpellBank.FrostLightning_Spells.frostliteSpells;
import static wizardo.game.Spells.SpellBank.LightningArcane_Spells.litearcaneSpells;
import static wizardo.game.Spells.SpellBank.LightningFire_Spells.litefireSpells;
import static wizardo.game.Wizardo.player;

public class Legendary_WeaveStaff extends Staff {

    float stateTime;

    public Legendary_WeaveStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Spellweaver.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Spellweaver_Over.png"));
        displayRotation = 25;

        name = "The Spellweaver";
        title = "Legendary Staff";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MASTERY_ALL);
        quantity_gearStats.add(1);
        gearStats.add(ItemUtils.GearStat.ALLDMG);
        quantity_gearStats.add(10);

    }

    public String getDescription() {
        return String.format("""
            The Spellweaver casts powerful spells
            on its own periodically""");
    }

    public String getFlavorText() {
        return "\" He who controls the Weave,\ncontrols the universe.\"";
    }

    public void update(float delta) {
        stateTime += delta;
        if(stateTime >= 8) {
            castRandomPowerfulSpell();
            stateTime = 0;
        }
    }

    public static void castRandomPowerfulSpell() {
        Spell spell = null;
        int random = MathUtils.random(1,12);
        switch (random) {
            case 1 -> spell = litefireSpells[25];           // Overheat + Chain + Fireball
            case 2 -> spell = litearcaneSpells[26];         // Energy Rain + Thunderstorm
            case 3 -> spell = frostliteSpells[23];          // Blizzard + Frostbolt
            case 4 -> spell = frostfireSpells[26];          // FrozenOrb finishing in FrostNova
            case 5 -> spell = frostarcaneSpells[26];        // Frost Judgement
            case 6 -> spell = firearcaneSpells[22];         // Arcane Meteors
            case 7 -> spell = litefireSpells[23];           // LightningHands + thunderstorm
            case 8 -> spell = litearcaneSpells[22];         // Rifts + Chain + Missiles
            case 9 -> spell = frostliteSpells[21];          // Storm + Frostbolts + Chargedbolt
            case 10 -> spell = frostfireSpells[17];         // Frozen Dragonbreath
            case 11 -> spell = frostarcaneSpells[24];       // Frozen orb + rift + missile
            case 12 -> spell = firearcaneSpells[24];        // EnergyRain + Flamejet
        }
        Spell clone = spell.clone();
        player.screen.spellManager.add(clone);
    }

}
