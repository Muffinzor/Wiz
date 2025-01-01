package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_EnergybeamStaff extends Staff {

    public Epic_EnergybeamStaff() {
        sprite = new Sprite(new Texture("Items/Staff/DarthStaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/DarthStaff_Over.png"));
        displayRotation = 25;

        name = "The Dark Apprentice";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.BEAM);
        quantity_masteries.add(2);

    }

    public String getDescription() {
        return"Energy beam and lasers are also\n shot in the opposite direction";
    }

    public String getFlavorText() {
        return "\" At last we reveal ourselves to the Wizards.\"";
    }


}
