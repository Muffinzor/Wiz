package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_NagelRing extends Ring {

    public Epic_NagelRing() {
        sprite = new Sprite(new Texture("Items/Rings/NagelRing.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/NagelRing_Over.png"));
        displayScale = 0.75f;

        name = "Nagel's Band";
        title = "Epic Ring";
        quality = ItemUtils.EquipQuality.EPIC;

        gearStats.add(ItemUtils.GearStat.LUCK);
        quantity_gearStats.add(15);
        gearStats.add(ItemUtils.GearStat.DEFENSE);
        quantity_gearStats.add(2);
        getEpicRingStats(this, 1);
    }

    public String getDescription() {
          return  "Potions potency is doubled";
    }

    public String getFlavorText() {
        return "For yearning wizards";
    }

}
