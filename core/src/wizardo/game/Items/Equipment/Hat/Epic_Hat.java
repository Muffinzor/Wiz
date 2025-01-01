package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;

import static wizardo.game.Wizardo.player;

public class Epic_Hat extends Hat {

    public Epic_Hat() {

        setSprite();
        title = "Epic Hat";
        quality = ItemUtils.EquipQuality.EPIC;

        getEpicHatStats(this, 3);

        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getRareHatStats(this, 1);
        }

    }

    public void setSprite() {
        reversedSprite = MathUtils.randomBoolean();
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> {
                sprite = new Sprite(new Texture("Items/Hat/epic1.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/epic1_over.png"));
                displayScale = 1.3f;
            }
            case 2 -> {
                sprite = new Sprite(new Texture("Items/Hat/RedHat.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/RedHat_Over.png"));
            }
            case 3 -> {
                sprite = new Sprite(new Texture("Items/Hat/EpicHat1.png"));
                spriteOver = new Sprite(new Texture("Items/Hat/EpicHat1_Over.png"));
            }
        }
    }

    public String getFlavorText() {
        return "Looks very wizardy";
    }
}
