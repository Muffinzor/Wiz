package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Unique.QuetzOverload.QuetzOverload;

import static wizardo.game.Wizardo.player;


public class Legendary_LightningRobes extends Robes{

    public float accumulatedDmg;

    public Legendary_LightningRobes() {
        sprite = new Sprite(new Texture("Items/Robes/LightningRobes.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/LightningRobes_Over.png"));
        displayScale = 0.9f;

        name = "Quetz' Pride";
        title = "Legendary Robes";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.LITEDMG);
        quantity_gearStats.add(MathUtils.random(15, 20));

    }

    public String getDescription() {
        return String.format("""
            Releases a lightning overload
            everytime you lose 50 shield""");
    }

    public String getFlavorText() {
        return "\" From the scales of\nthe Feathered Serpent\"";
    }

    public void update(float delta) {
        if(accumulatedDmg >= 50) {
            accumulatedDmg = 0;
            QuetzOverload overload = new QuetzOverload();
            player.spellManager.add(overload);
        }
    }
}
