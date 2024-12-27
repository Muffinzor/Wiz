package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class Rare_Hat extends Hat {

    public Rare_Hat() {
        sprite = new Sprite(new Texture("Items/Hat/Greenhat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/Greenhat_Over.png"));

        name = "Pointy Hat";
        title = "Rare Hat";
        quality = ItemUtils.EquipQuality.RARE;

        masteries.add(SpellUtils.getRandomMastery(2, true));
        quantity_masteries.add(1);
        gearStats.add(getRareStat());
        quantity_gearStats.add(MathUtils.random(5, 10));

    }

    public String getFlavorText() {
        return "Just a pointy hat";

    }

}
