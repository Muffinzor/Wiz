package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Epic_Amulet extends Amulet {

    public Epic_Amulet() {
        sprite = new Sprite(new Texture("Items/Amulets/PurpleAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/PurpleAmmy_Over.png"));
        displayScale = 0.85f;

        title = "Epic Amulet";
        quality = ItemUtils.EquipQuality.EPIC;

        getEpicAmuletStats(this, 3);
        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getRareAmuletStats(this, 1);
        }

    }


    public String getFlavorText() {
        return "\" A beautiful trinket\"";
    }

}
