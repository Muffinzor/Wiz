package wizardo.game.Items.Equipment.Book;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Unique.VogonAura;


import static wizardo.game.Wizardo.player;

public class Epic_VogonBook extends Book {

    VogonAura aura;

    public Epic_VogonBook() {
        sprite = new Sprite(new Texture("Items/Spellbook/VogonBook.png"));
        spriteOver = new Sprite(new Texture("Items/Spellbook/VogonBook_Over.png"));
        displayScale = 0.8f;

        name = "Vogon Poems";
        title = "Epic Book";
        quality = ItemUtils.EquipQuality.EPIC;
    }

    public String getDescription() {
        int bonus = player.inventory.gold/100;
        return String.format("""
            Reading from this makes monsters near
            you take 30%% increased damage""", bonus);
    }

    public String getFlavorText() {
        return "\" Widely regarded as third worst\nin the universe.\"";
    }

    @Override
    public void equip() {
        super.equip();
        aura = new VogonAura();
        player.screen.spellManager.add(aura);
    }

    public void unequip() {
        super.unequip();
        player.screen.spellManager.remove(aura);
    }
}
