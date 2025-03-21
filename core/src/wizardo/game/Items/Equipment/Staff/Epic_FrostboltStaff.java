package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_FrostboltStaff extends Staff {

    public Epic_FrostboltStaff() {
        sprite = new Sprite(new Texture("Items/Staff/FrostboltStaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/FrostboltStaff_Over.png"));
        displayRotation = 25;

        name = "The Caged Man's Rod";
        title = "Epic Staff";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.FROSTBOLT);
        quantity_masteries.add(1);

    }

    public String getDescription() {
        return String.format("""
            Frostbolts freeze twice as often
            and for longer""");
    }

    public String getFlavorText() {
        return "\" Stay a while, and listen...";
    }


}
