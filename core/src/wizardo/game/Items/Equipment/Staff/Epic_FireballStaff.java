package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_FireballStaff extends Staff {

    public Epic_FireballStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Hydrastaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Hydrastaff_Over.png"));
        displayRotation = 25;

        name = "Hydra Head";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.FIREBALL);
        quantity_masteries.add(1);

    }

    public String getDescription() {
        return String.format("""
            Fireballs are split into 3 Firebolts
            Firebolts have -20%% damage and radius
            but retain everything else""");
    }

    public String getFlavorText() {
        return "\" Down the deepest hollow,\non a lake made of ash...\"";
    }


}
