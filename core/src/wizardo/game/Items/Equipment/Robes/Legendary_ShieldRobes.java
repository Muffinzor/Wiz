package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Unique.FreezingMist.FreezingMist_Pulse;

import static wizardo.game.Wizardo.player;

public class Legendary_ShieldRobes extends Robes{

    float quantityRemoved;

    public Legendary_ShieldRobes() {
        sprite = new Sprite(new Texture("Items/Robes/ShieldRobes.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/ShieldRobes_Over.png"));
        displayScale = 0.75f;

        name = "Tal's Regalia";
        title = "Legendary Robes";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.REGEN);
        quantity_gearStats.add(150);
        gearStats.add(ItemUtils.GearStat.CASTSPEED);
        quantity_gearStats.add(5);

    }

    public String getDescription() {
        return String.format("""
            Shield no longer has a cooldown but
            its quantity is diminished by 30%%""");
    }

    public String getFlavorText() {
        return "\" The Sorceress walks fearlessly\ninto the fray\"";
    }

    @Override
    public void equip() {
        super.equip();
        quantityRemoved += player.stats.maxShield * 0.3f;
        player.stats.maxShield = player.stats.maxShield * 0.7f;
        if(player.stats.shield > player.stats.maxShield) {
            player.stats.shield = player.stats.maxShield;
        }
    }

    @Override
    public void unequip() {
        super.unequip();
        giveBackShield();
    }

    public float taxShield(float initialValue) {
        quantityRemoved += initialValue * 0.3f;
        return initialValue * 0.7f;
    }
    public void giveBackShield() {
        player.stats.maxShield += quantityRemoved;
        quantityRemoved = 0;
    }
}
