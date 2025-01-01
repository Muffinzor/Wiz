package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Normal_Hat extends Hat{

    public Normal_Hat() {
        setSprite();

        title = "Common Hat";
        quality = ItemUtils.EquipQuality.NORMAL;

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalHatStats(this, 2);
        } else {
            getNormalHatStats(this, 1);
        }

    }

    @Override
    public String getFlavorText() {
        return "Just a pointy hat";
    }

    public void setSprite() {
        reversedSprite = MathUtils.randomBoolean();
        int random = MathUtils.random(1, 2);
        switch (random) {
            case 1 -> {
                sprite = new Sprite(new Texture("Items/Hat/NormalHat1.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/NormalHat1_Over.png"));
                displayScale = 1.3f;
            }
            case 2 -> {
                sprite = new Sprite(new Texture("Items/Hat/Greenhat.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/GreenHat_Over.png"));
            }
        }
    }
}
