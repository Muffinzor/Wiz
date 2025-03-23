package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_IcespearStaff extends Staff{

    public Epic_IcespearStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Icespear.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Icespear_Over.png"));
        displayRotation = 25;

        name = "Boomstaff";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.ICESPEAR);
        quantity_masteries.add(1);
    }

    public String getDescription() {
        return String.format("""
            Ice Spears are immediately broken into
            shards and shards can split immediately
            
            +20%% Ice Spear speed
            +1 Shard""");
    }

    public String getFlavorText() {
        return " \"WizMart's top of the line.\"";
    }

}
