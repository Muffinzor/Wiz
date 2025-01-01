package wizardo.game.Items.Equipment.Hat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellUtils.Spell_Name.*;
import static wizardo.game.Wizardo.player;

public class Legendary_SentientHat extends Hat {

    public Legendary_SentientHat() {
        sprite = new Sprite(new Texture("Items/Hat/SentientHat.png"));
        spriteOver = new Sprite(new Texture("Items/Hat/SentientHat_Over.png"));
        displayScale = 0.8f;

        name = "Sentient Hat";
        title = "Legendary Hat";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.ALLDMG);
        quantity_gearStats.add(10);

    }

    public String getDescription() {

        return String.format("""
            The Sentient Hat takes control of
            your targeted spells and gives them
            20%% reduced cooldown""");
    }

    public String getFlavorText() {
        return "\"I was the greatest wizard\nof them all!\"";
    }

    public static Monster findTarget() {
        ArrayList<Monster> list = null;
        ArrayList<Monster> inShortRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 4, true);
        if(!inShortRange.isEmpty()) {
            list = inShortRange;
        } else {
            ArrayList<Monster> inMediumRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 7, true);
            if(!inMediumRange.isEmpty()) {
                list = inMediumRange;
            } else {
                ArrayList<Monster> inLongRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 10, true);
                if(!inLongRange.isEmpty()) {
                    list = inLongRange;
                } else {
                    ArrayList<Monster> inMaxRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 15, true);
                    if(!inMaxRange.isEmpty()) {
                        list = inMaxRange;
                    }
                }
            }
        }

        if(list != null) {
            Collections.shuffle(list);
            return list.getFirst();
        } else {
            return null;
        }

    }
}
