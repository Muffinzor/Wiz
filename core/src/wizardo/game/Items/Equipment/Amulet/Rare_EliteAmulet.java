package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.player;

public class Rare_EliteAmulet extends Amulet {

    public Rare_EliteAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/GiantAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/GiantAmmy_Over.png"));
        displayScale = 0.9f;

        title = "Rare Amulet";
        quality = ItemUtils.EquipQuality.RARE;


        getRareAmuletStats(this, 1);
        if(Math.random() >= 1 - (player.stats.luck/100f)) {
            getNormalAmuletStats(this, 1);
        }
    }

    public String getDescription() {
        return "Deal 20%% more damage\nto large monsters";
    }

    public String getFlavorText() {
        return "for Big Game Wizards";
    }



}
