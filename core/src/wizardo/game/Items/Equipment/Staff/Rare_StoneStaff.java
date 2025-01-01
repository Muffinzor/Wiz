package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.Equipment.EquipmentUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.lang.annotation.ElementType;

import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Wizardo.player;

public class Rare_StoneStaff extends Staff{

    public Rare_StoneStaff() {
        setupSprites();

        name = "Stone Staff";

        title = "Rare Staff";
        quality = ItemUtils.EquipQuality.RARE;

    }

    public void setupSprites() {
        SpellUtils.Spell_Element random = SpellUtils.getRandomClassicElement();
        switch(random) {
            case FROST -> {
                sprite = new Sprite(new Texture("Items/Staff/Bluestone.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/Bluestone_Over.png"));
                masteries.add(SpellUtils.getRandomMastery(FROST, 2, true));
                quantity_masteries.add(1);
                gearStats.add(ItemUtils.GearStat.FROSTDMG);
                quantity_gearStats.add(10);
            }
            case FIRE -> {
                sprite = new Sprite(new Texture("Items/Staff/Redstone.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/Redstone_Over.png"));
                masteries.add(SpellUtils.getRandomMastery(FIRE, 2, true));
                quantity_masteries.add(1);
                gearStats.add(ItemUtils.GearStat.FIREDMG);
                quantity_gearStats.add(10);
            }
            case LIGHTNING -> {
                sprite = new Sprite(new Texture("Items/Staff/Yellowstone.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/Yellowstone_Over.png"));
                masteries.add(SpellUtils.getRandomMastery(LIGHTNING, 2, true));
                quantity_masteries.add(1);
                gearStats.add(ItemUtils.GearStat.LITEDMG);
                quantity_gearStats.add(10);
            }
            case ARCANE -> {
                sprite = new Sprite(new Texture("Items/Staff/Purplestone.png"));
                spriteOver = new Sprite(new Texture("Items/Staff/Purplestone_Over.png"));
                masteries.add(SpellUtils.getRandomMastery(ARCANE, 2, true));
                quantity_masteries.add(1);
                gearStats.add(ItemUtils.GearStat.ARCANEDMG);
                quantity_gearStats.add(10);
            }
        }

    }

    public String getFlavorText() {
        return "A peculiar stone encrusted on a stick";
    }
}
