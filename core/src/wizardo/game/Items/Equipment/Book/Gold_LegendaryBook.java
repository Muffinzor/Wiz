package wizardo.game.Items.Equipment.Book;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class Gold_LegendaryBook extends Book {

    public Gold_LegendaryBook() {
        sprite = new Sprite(new Texture("Items/Spellbook/Goldbook.png"));
        spriteOver = new Sprite(new Texture("Items/Spellbook/Goldbook_Over.png"));
        displayScale = 0.8f;

        name = "The Dragon's Ledger";
        title = "Legendary Book";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(null);
    }

    public String getDescription() {
        int bonus = player.inventory.gold/100;
        return String.format("""
            You gain +1%% to all damage for
            every 100 gold that you hold""", bonus);
    }

    public String getFlavorText() {
        return "\" Don't be shy,\nstep into the light.\"";
    }


    public void equip() {
        super.equip();
    }

    public int getStatValue(int index) {
        return player.inventory.gold/100;
    }

    @Override
    public void unequip() {
        player.inventory.equippedBook = null;
    }
}
