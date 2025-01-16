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
        setupSprites();
        displayScale = 0.85f;

        title = "Epic Amulet";
        quality = ItemUtils.EquipQuality.EPIC;

        getEpicAmuletStats(this, 3);
        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getRareAmuletStats(this, 1);
        }

    }

    public void setupSprites() {
        int random = MathUtils.random(1,3);
        switch (random) {
            case 1 -> {
                sprite = new Sprite(new Texture("Items/Amulets/EpicAmmy1.png"));
                spriteOver = new Sprite(new Texture("Items/Amulets/EpicAmmy1_Over.png"));
            }
            case 2 -> {
                sprite = new Sprite(new Texture("Items/Amulets/EpicAmmy2.png"));
                spriteOver = new Sprite(new Texture("Items/Amulets/EpicAmmy2_Over.png"));
            }
            case 3 -> {
                sprite = new Sprite(new Texture("Items/Amulets/EpicAmmy3.png"));
                spriteOver = new Sprite(new Texture("Items/Amulets/EpicAmmy3_Over.png"));
            }
        }
    }


    public String getFlavorText() {
        return "\" A beautiful trinket\"";
    }

}
