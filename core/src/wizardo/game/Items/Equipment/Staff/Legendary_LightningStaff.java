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

public class Legendary_LightningStaff extends Staff {

    public Legendary_LightningStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Thundergods.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Thundergods_Over.png"));
        displayRotation = 25;

        name = "Thundergod's Ire";
        title = "Legendary Staff";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MASTERY_LIGHTNING);
        quantity_gearStats.add(1);
        gearStats.add(ItemUtils.GearStat.LITEDMG);
        quantity_gearStats.add(MathUtils.random(15,20));

    }

    public String getDescription() {
        return String.format("""
            All your lightning damage has a chance
            to trigger a powerful thunderbolt that
            scales with all your lightning masteries""");
    }

    public String getFlavorText() {
        return "\" For know that noone is truly free,\nexcept Zeus.\"";
    }

    public static void castThunderbolt(Monster monster, Spell spell) {
        float procRate = 0.75f;
        if(spell instanceof ChargedBolts_Spell || spell instanceof Frostbolt_Spell) {
            procRate = 0.85f;
        }
        if(Math.random() >= procRate) {
            ThundergodBolt_Projectile bolt = new ThundergodBolt_Projectile(monster.body.getPosition());
            monster.screen.spellManager.add(bolt);
        }
    }

}
