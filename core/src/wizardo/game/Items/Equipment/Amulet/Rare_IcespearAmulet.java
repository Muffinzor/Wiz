package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;

public class Rare_IcespearAmulet extends Amulet {

    public Rare_IcespearAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/SpearAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/SpearAmmy_Over.png"));
        displayScale = 0.85f;

        name = "Sharp Medaillon";
        title = "Rare Amulet";
        quality = ItemUtils.EquipQuality.RARE;

    }

    public String getDescription() {
        return "Ice spears last 2 additional hits";
    }

    public String getFlavorText() {
        return "\"A durable spear\nfor a reliable wizard.\"";
    }

}
