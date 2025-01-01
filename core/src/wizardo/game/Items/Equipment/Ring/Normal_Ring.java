package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Normal_Ring extends Ring {

    public Normal_Ring() {
        sprite = new Sprite(new Texture("Items/Rings/Greenstones.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/Greenstones_Over.png"));
        displayScale = 0.7f;

        title = "Common Ring";
        quality = ItemUtils.EquipQuality.NORMAL;

        getNormalRingStats(this, 1);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalRingStats(this, 1);
        }

    }

    public String getFlavorText() {
        return "A worthless pebble";
    }

}
