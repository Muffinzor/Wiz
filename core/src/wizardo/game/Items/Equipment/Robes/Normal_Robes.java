package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Normal_Robes extends Robes {

    public Normal_Robes() {
        if(MathUtils.randomBoolean()) {
            sprite = new Sprite(new Texture("Items/Robes/Rags1.png"));
            spriteOver = new Sprite(new Texture("Items/Robes/Rags1_Over.png"));
        } else {
            sprite = new Sprite(new Texture("Items/Robes/Rags2.png"));
            spriteOver = new Sprite(new Texture("Items/Robes/Rags2_Over.png"));
        }

        title = "Common Robes";
        quality = ItemUtils.EquipQuality.NORMAL;

        getNormalRobesStats(this, 1);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalRobesStats(this, 1);
        }

    }

    public String getFlavorText() {
        return "Barely more than rags";
    }

}
