package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Rare_Hat extends Hat {

    public Rare_Hat() {
        sprite = new Sprite(new Texture("Items/Hat/rare1.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/rare1_over.png"));
        displayScale = 1.3f;

        name = "Pointy Hat";
        title = "Rare Hat";
        quality = ItemUtils.EquipQuality.RARE;

        getRareHatStats(this, 2);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalHatStats(this, 1);
        }

    }

    public String getFlavorText() {
        return "Just a pointy hat";
    }

}
