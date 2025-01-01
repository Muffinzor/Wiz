package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_ChargedboltStaff extends Staff {

    public Epic_ChargedboltStaff() {
        sprite = new Sprite(new Texture("Items/Staff/BoltStaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/BoltStaff_Over.png"));
        displayRotation = 25;

        name = "Omnistaff";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.CHARGEDBOLTS);
        quantity_masteries.add(2);

    }

    public String getDescription() {
        return String.format("""
            Chargedbolts is cast all around you
            and shoots a vastly larger quantity
            of bolts""");
    }

    public String getFlavorText() {
        return "\" Experienced wizards should have\nno blind spots.\"";
    }


}
