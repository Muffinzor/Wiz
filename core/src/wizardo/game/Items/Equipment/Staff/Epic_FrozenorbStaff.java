package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_FrozenorbStaff extends Staff{

    public Epic_FrozenorbStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Orbstaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Orbstaff_Over.png"));
        displayRotation = 25;

        name = "Death's Fathom";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.FROZENORB);
        quantity_masteries.add(1);
    }

    public String getDescription() {
        return String.format("""
            Frozen orb's cooldown and duration
            are halved, it moves 30%% slower and
            its AoE damage is increased by 50%%""");
    }

    public String getFlavorText() {
        return "\" There is dark magic at work here.\"";
    }

}
