package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Spells.Unique.ThundergodBolt.ThundergodBolt_Projectile;

import static wizardo.game.Wizardo.player;

public class Legendary_ArcaneStaff extends Staff {

    public Legendary_ArcaneStaff() {
        sprite = new Sprite(new Texture("Items/Staff/HolyStaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/HolyStaff_Over.png"));
        displayRotation = 25;

        name = "Twisting Nether";
        title = "Legendary Staff";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MASTERY_ARCANE);
        quantity_gearStats.add(1);
        gearStats.add(ItemUtils.GearStat.ARCANEDMG);
        quantity_gearStats.add(MathUtils.random(15,20));

    }

    public String getDescription() {
        return String.format("""
            Your arcane damage
            can spawn arcane missiles""");
    }

    public String getFlavorText() {
        return "\" Do not concern yourself,\nmy fate is beyond your feeble grasp.\"";
    }

    public void castArcaneMissile(Monster monster) {
        if(Math.random() >= 0.8f) {
            ArcaneMissile_Spell missile = new ArcaneMissile_Spell();
            missile.spawnPosition = new Vector2(monster.body.getPosition());
            missile.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 2);
            missile.arcaneCasted = true;
            missile.anim_element = SpellUtils.Spell_Element.ARCANE;
            player.screen.spellManager.add(missile);
        }
    }
}



