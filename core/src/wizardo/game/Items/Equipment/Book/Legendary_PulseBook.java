package wizardo.game.Items.Equipment.Book;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.Unique.StaticOrb.StaticOrb;
import wizardo.game.Spells.Unique.StaticOrb.StaticPulse;
import static wizardo.game.Wizardo.player;

public class Legendary_PulseBook extends Book {

    public int orbsAlive;
    float cooldown;

    public Legendary_PulseBook() {
        sprite = new Sprite(new Texture("Items/Spellbook/PulseBook.png"));
        spriteOver = new Sprite(new Texture("Items/Spellbook/PulseBook_Over.png"));
        displayScale = 0.8f;

        name = "The Savant's Binder";
        title = "Legendary Book";
        quality = ItemUtils.EquipQuality.LEGENDARY;

    }

    @Override
    public void update(float delta) {
        cooldown -= delta;
    }

    public String getDescription() {

        return String.format("""
            All spells can create a Pulsar Orb
            when hitting monsters""");
    }

    public String getFlavorText() {
        return "\" Remember to look up at the stars,\nnot down at your feet.\"";
    }

    public void createPulsar(Monster monster, Spell spell) {
        if(orbsAlive < 6 && cooldown <= 0 && !(spell instanceof StaticPulse)) {
            orbsAlive++;
            cooldown = 0.33f;
            StaticOrb orb = new StaticOrb(monster.body.getPosition(), this);
            orb.anim_element = spell.anim_element;
            player.screen.spellManager.add(orb);
        }
    }
}
