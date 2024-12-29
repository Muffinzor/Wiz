package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_OculusRing extends Ring {

    public Epic_OculusRing() {
        sprite = new Sprite(new Texture("Items/Rings/Occy.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/Occy_Over.png"));
        displayScale = 0.7f;

        name = "The Oculus Ring";
        title = "Epic Ring";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.getRandomMastery(null, 1, false));
        quantity_masteries.add(MathUtils.random(1,3));
        masteries.add(SpellUtils.getRandomMastery(null,2, false));
        quantity_masteries.add(MathUtils.random(1,3));
        masteries.add(SpellUtils.getRandomMastery(null,3, false));
        quantity_masteries.add(MathUtils.random(1,3));

        gearStats.add(ItemUtils.GearStat.ALLDMG);
        quantity_gearStats.add(5);
    }

    public String getFlavorText() {
        return "A very unusual ring";
    }
}
