package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Unique.FreezingBlast.FreezingBlast_Explosion;

public class Legendary_FrostStaff extends Staff {

    public Legendary_FrostStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Heatdeath.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Heatdeath_Over.png"));
        displayRotation = 25;

        name = "Heat Death";
        title = "Legendary Staff";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MASTERY_FROST);
        quantity_gearStats.add(1);
        gearStats.add(ItemUtils.GearStat.FROSTDMG);
        quantity_gearStats.add(MathUtils.random(15,20));

    }

    public String getDescription() {
        return String.format("""
            Killing a frozen monster with frost
            damage can cause them to shatter
            
            You deal 20%% more damage to
            frozen monsters""");
    }

    public String getFlavorText() {
        return "\" True Equilibrium\"";
    }

}
