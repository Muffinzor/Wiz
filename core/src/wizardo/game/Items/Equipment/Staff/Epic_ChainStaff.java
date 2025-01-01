package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_ChainStaff extends Staff {

    public Epic_ChainStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Coilstaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Coilstaff_Over.png"));
        displayRotation = 25;

        name = "Coiled Spire";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.CHAIN);
        quantity_masteries.add(2);

    }

    public String getDescription() {
        return String.format("""
            Chain Lightning gains a chance to
            cause splash damage""");
    }

    public String getFlavorText() {
        return "\" All these years I have spent\nin the service of wizards...\"";
    }


}
