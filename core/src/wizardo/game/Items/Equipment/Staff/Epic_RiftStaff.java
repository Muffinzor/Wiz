package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_RiftStaff extends Staff {

    public Epic_RiftStaff() {
        sprite = new Sprite(new Texture("Items/Staff/RiftStaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/RiftStaff_Over.png"));
        displayRotation = 25;

        name = "Personal Vendetta";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.RIFTS);
        quantity_masteries.add(2);
    }

    public String getDescription() {
        return String.format("""
            Rifts will always be cast
            underneath monsters""");
    }

    public String getFlavorText() {
        return "\" Like the Weave itself,\nI do not play with dice.\"";
    }


}
