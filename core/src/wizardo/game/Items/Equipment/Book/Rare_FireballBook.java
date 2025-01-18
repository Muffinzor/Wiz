package wizardo.game.Items.Equipment.Book;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Unique.VogonAura;

import static wizardo.game.Wizardo.player;

public class Rare_FireballBook extends Book{


    public Rare_FireballBook() {
        sprite = new Sprite(new Texture("Items/Spellbook/RadahnBook.png"));
        spriteOver = new Sprite(new Texture("Items/Spellbook/RadahnBook_Over.png"));
        displayScale = 0.8f;

        name = "Star Conquerering Manual";
        title = "Rare Book";
        quality = ItemUtils.EquipQuality.RARE;
    }

    public String getDescription() {
        int bonus = player.inventory.gold/100;
        return String.format("""
            Fireball and Meteors gain 20%% increased
            explosion radius""", bonus);
    }

    public String getFlavorText() {
        return "\" Leonard would disapprove.\"";
    }

}
