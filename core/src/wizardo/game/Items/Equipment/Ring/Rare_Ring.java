package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Rare_Ring extends Ring {

    public Rare_Ring() {
        sprite = new Sprite(new Texture("Items/Rings/Greenstones.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/Greenstones_Over.png"));
        displayScale = 0.7f;

        title = "Rare Ring";
        quality = ItemUtils.EquipQuality.RARE;

        getRareRingStats(this, 2);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalRingStats(this, 1);
        }

    }

    public String getFlavorText() {
        return "Looks more rare than it is";
    }

}
