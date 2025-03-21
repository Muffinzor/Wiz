package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_MissileStaff extends Staff{

    public Epic_MissileStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Fingerstaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Fingerstaff_Over.png"));
        displayRotation = 25;

        name = "The Pursuer";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.MISSILES);
        quantity_masteries.add(1);
    }

    public String getDescription() {
        return String.format("""
            Arcane Missiles no longer lose
            lifetime when hitting monsters""");
    }

    public String getFlavorText() {
        return "\" They say he hunts those\nwho bear the darksign.\"";
    }

}
