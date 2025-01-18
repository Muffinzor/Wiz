package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Epic_DragonbreathAmulet extends Amulet {

    public Epic_DragonbreathAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/DragonAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/DragonAmmy_Over.png"));
        displayScale = 0.9f;

        name = "The Betrayer's Phylactery";
        title = "Epic Amulet";
        quality = ItemUtils.EquipQuality.EPIC;

        masteries.add(SpellUtils.Spell_Name.FLAMEJET);
        quantity_masteries.add(1);
        masteries.add(SpellUtils.Spell_Name.OVERHEAT);
        quantity_masteries.add(1);


    }

    public String getDescription() {

        return String.format("""
            Dragonbreath's angle is doubled""");
    }

    public String getFlavorText() {
        return "\" Better scaleless than lifeless.\"";
    }
}
