package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_FrostRing extends Ring {

    public Epic_FrostRing() {
        sprite = new Sprite(new Texture("Items/Rings/FrostRing.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/FrostRing_Over.png"));
        displayScale = 0.75f;

        name = "Den's Halo";
        title = "Epic Ring";
        quality = ItemUtils.EquipQuality.EPIC;

        gearStats.add(ItemUtils.GearStat.FROSTDMG);
        quantity_gearStats.add(MathUtils.random(10,15));
        masteries.add(SpellUtils.getRandomMastery(SpellUtils.Spell_Element.FROST, 3, true));
        quantity_masteries.add(1);

    }

    public String getDescription() {
        return  "Frost damage has a 20% chance\nto freeze monsters";
    }

    public String getFlavorText() {
        return "Winter is coming";
    }

}
