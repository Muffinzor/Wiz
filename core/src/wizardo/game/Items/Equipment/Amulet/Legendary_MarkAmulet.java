package wizardo.game.Items.Equipment.Amulet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Spells.Unique.MarkDetonation;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Legendary_MarkAmulet extends Amulet {

    public Legendary_MarkAmulet() {
        sprite = new Sprite(new Texture("Items/Amulets/FirstHitAmmy.png"));
        spriteOver = new Sprite(new Texture("Items/Amulets/FirstHitAmmy_Over.png"));
        displayScale = 0.85f;

        name = "The Huntress Locket";
        title = "Legendary Amulet";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        masteries.add(SpellUtils.Spell_Name.MISSILES);
        quantity_masteries.add(1);
        masteries.add(SpellUtils.Spell_Name.ICESPEAR);
        quantity_masteries.add(1);
        gearStats.add(ItemUtils.GearStat.PROJSPEED);
        quantity_gearStats.add(15);

    }

    public String getDescription() {
        return String.format("""
            Arcane missiles and Ice spears mark
            the first monster they hit
            
            Marked monsters will trigger an explosion
            if hit by Arcane missiles, Ice spears,
            Energy beam or Lasers""");
    }

    public String getFlavorText() {
        return "\" My aim is true.\"";
    }

    public void markMonster(Monster monster) {
        monster.marked = true;
    }

    /**
     * detonates a Mark on a monster
     * @param monster the target
     */
    public void detonateMonster(Monster monster) {
        Vector2 position = SpellUtils.getClearRandomPosition(monster.body.getPosition(), monster.bodyRadius/PPM);
        MarkDetonation detonation = new MarkDetonation(position);
        player.screen.spellManager.add(detonation);
        monster.marked = false;

    }
}
