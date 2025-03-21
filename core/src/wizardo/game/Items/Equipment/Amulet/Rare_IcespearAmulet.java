package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

public class Rare_IcespearAmulet extends Amulet {

    public Rare_IcespearAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/SpearAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/SpearAmmy_Over.png"));
        displayScale = 0.85f;

        name = "Sharp Medaillon";
        title = "Rare Amulet";
        quality = ItemUtils.EquipQuality.RARE;

        masteries.add(SpellUtils.Spell_Name.ICESPEAR);
        quantity_masteries.add(1);

    }

    public String getDescription() {
        return "Ice spears split into an additional shard";
    }

    public String getFlavorText() {
        return "\"A versatile spear\nfor a reliable wizard.\"";
    }

}
